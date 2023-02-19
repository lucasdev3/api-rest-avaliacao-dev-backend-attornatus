package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Pessoa;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.EnderecoDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.PessoaDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.repositories.PessoaRepository;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.interfaces.PessoaService;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.utils.ResponseModel;

@Service
public class PessoaServiceImpl implements PessoaService {

  @Autowired
  private final PessoaRepository pessoaRepository;

  public PessoaServiceImpl(PessoaRepository pessoaRepository) {
    this.pessoaRepository = pessoaRepository;
  }

  private static final Logger LOGGER = Logger.getLogger(PessoaServiceImpl.class);

  @Override
  public ResponseEntity<List<PessoaDTO>> buscarTodos() {
    try {
      List<Pessoa> pessoas = pessoaRepository.findAll();
      if (pessoas.isEmpty()) {
        LOGGER.info("Nenhuma pessoa encontrada!");
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(pessoas.stream().map(PessoaDTO::new).collect(Collectors.toList()));
    } catch (Exception e) {
      LOGGER.error(e);
    }
    return ResponseEntity.internalServerError().build();
  }

  @Override
  public ResponseEntity<List<PessoaDTO>> buscarTodosPeloNome(String nome) {
    try {
      List<Pessoa> pessoas = pessoaRepository.findAllByNomeContaining(nome);
      if (pessoas.iterator().hasNext()) {
        LOGGER.info("Nenhuma pessoa encontrada pelo nome!");
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(pessoas.stream().map(PessoaDTO::new).collect(Collectors.toList()));
    } catch (Exception e) {
      LOGGER.error(e);
    }
    return ResponseEntity.internalServerError().build();
  }

  @Override
  public ResponseEntity<PessoaDTO> buscarPeloId(Long id) {
    try {
      Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
      if (pessoa == null) {
        LOGGER.info("Nenhuma pessoa encontrado pelo id!");
        return ResponseEntity.notFound().build();
      }
      PessoaDTO dto = new PessoaDTO(pessoa);
      return ResponseEntity.ok(dto);
    } catch (Exception e) {
      LOGGER.error("Falha ao listar pessoa por id!\n" + e.getMessage());
    }
    return ResponseEntity.internalServerError().build();
  }

  @Override
  public ResponseEntity<List<EnderecoDTO>> buscarEnderecoPeloId(Long id, Boolean enderecoPrincipal) {
    try {
      Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
      if (pessoa == null) {
        LOGGER.info("Nenhuma pessoa encontrado pelo id!");
        return ResponseEntity.notFound().build();
      }
      PessoaDTO dto = new PessoaDTO(pessoa);
      if (enderecoPrincipal) {
        return ResponseEntity.ok(
            dto.getEnderecos().stream().filter(EnderecoDTO::getEnderecoPrincipal).collect(
                Collectors.toList()));
      } else {
        return ResponseEntity.ok(dto.getEnderecos());
      }

    } catch (Exception e) {
      LOGGER.error("Falha ao listar pessoa por id!\n" + e.getMessage());
    }
    return ResponseEntity.internalServerError().build();
  }

  @Override
  public ResponseEntity<?> salvar(@Valid PessoaDTO pessoaDTO) {
    try {
      if (!validacaoEnderecos(pessoaDTO.getEnderecos())) {
        return ResponseEntity.badRequest()
            .body(new ResponseModel(
                "É necessário pelo menos 1 endereço e somente 1 pode o principal!"));
      }
      if (existePessoaCadastrada(pessoaDTO.getNome())) {
        return ResponseEntity.badRequest().body(new ResponseModel("Nome já cadastrado no banco!"));
      }
      Pessoa pessoa = new Pessoa(pessoaDTO);
      pessoaRepository.save(pessoa);
      return ResponseEntity.ok().body(new ResponseModel("Pessoa cadastrada com sucesso!"));
    } catch (Exception e) {
      LOGGER.error("Falha ao cadastrar pessoa!\n" + e.getMessage());
    }
    return ResponseEntity.internalServerError().build();
  }

  @Override
  public ResponseEntity<?> atualizar(@Valid PessoaDTO pessoaDTO, Long id) {
    try {
      if (!validacaoEnderecos(pessoaDTO.getEnderecos())) {
        return ResponseEntity.badRequest()
            .body(new ResponseModel(
                "É necessário pelo menos 1 endereço e somente 1 pode ser o principal!"));
      }
      Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
      Boolean existeNomePessoa = existePessoaCadastrada(pessoaDTO.getNome());
      if (pessoa == null) {
        return ResponseEntity.badRequest().body(new ResponseModel(
            "Falha ao atualizar pessoa. Pessoa não encontrado na base de dados!"));
      }
      if (existeNomePessoa && !pessoaDTO.getNome().equalsIgnoreCase(pessoa.getNome())) {
        LOGGER.error("Nome já existente na base de dados");
        return ResponseEntity.badRequest()
            .body(new ResponseModel("Nome já existente na base de dados"));
      }
      pessoa.update(pessoaDTO);
      pessoaRepository.save(pessoa);
      LOGGER.info("Pessoa atualizada com sucesso!");
      return ResponseEntity.ok().body(new ResponseModel("Pessoa atualizada!"));

    } catch (Exception e) {
      LOGGER.error("Falha ao atualizar pessoa.\n" + e.getMessage());
    }
    return ResponseEntity.internalServerError().build();
  }

  @Override
  public ResponseEntity<?> deletar(Long id) {
    try {
      if (pessoaRepository.existsById(id)) {
        pessoaRepository.deleteById(id);
        LOGGER.info("Pessoa deletada com sucesso!");
        return ResponseEntity.ok().body(new ResponseModel("Pessoa deletada com sucesso!"));
      }
      return ResponseEntity.badRequest()
          .body(new ResponseModel("ID inválido! Usuario não registrado no banco!"));
    } catch (Exception e) {
      LOGGER.error("Falha ao deletar médico!\n" + e.getMessage());
    }
    return ResponseEntity.internalServerError().build();
  }

  /* ESTA REGRA GARANTE QUE NO BANCO SEJA CADASTRADO PELO MENOS UM ENDEREÇO NA LISTA DE ENDEREÇOS
     DA PESSOA E CERTIFICA QUE A PESSOA TENHA UM ENDERECO PRINCIPAL. SE A LISTA SO TIVER UM ENDERECO
     O MESMO SERIA CONSIDERADO COMO PRINCIPAL POR PADRAO */
  private Boolean validacaoEnderecos(List<EnderecoDTO> enderecos) {

    if (enderecos.size() == 0) {
      LOGGER.error("É necessário pelo menos 1 endereço!");
      return false;
    }
    int cnt = 0;
    for (EnderecoDTO endereco : enderecos) {
      if (endereco.getEnderecoPrincipal()) {
        cnt++;
      }
    }
    if (cnt > 1 || cnt == 0) {
      LOGGER.error("Pessoa só pode ter um endereço principal");
      return false;
    }
    return true;
  }

  private Boolean existePessoaCadastrada(String nome) {
    return pessoaRepository.existsByNome(nome);
  }


}
