package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.impl;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Pessoa;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.EnderecoDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.PessoaDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.repositories.PessoaRepository;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.interfaces.PessoaService;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.utils.ResponseModel;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PessoaServiceImpl implements PessoaService {

  @Autowired
  private final PessoaRepository pessoaRepository;

  public PessoaServiceImpl(PessoaRepository pessoaRepository) {
    this.pessoaRepository = pessoaRepository;
  }

  private static final Logger LOGGER = Logger.getLogger(PessoaServiceImpl.class);

  @Override
  public ResponseEntity<ResponseModel> buscarTodos() {
    try {
      List<Pessoa> pessoas = pessoaRepository.findAll();
      if (pessoas.isEmpty()) {
        LOGGER.info("Nenhuma pessoa encontrada!");
        return new ResponseEntity<>(new ResponseModel("Nenhuma pessoa encontrada"),
            HttpStatus.NOT_FOUND);
      }
      List<PessoaDTO> listDTO = pessoas.stream().map(PessoaDTO::new).collect(Collectors.toList());
      return new ResponseEntity<>(new ResponseModel("Lista de Pessoas", listDTO), HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error(e);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<ResponseModel> buscarTodosPeloNome(String nome) {
    try {
      List<Pessoa> pessoas = pessoaRepository.findAllByNomeContaining(nome);
      if (!pessoas.iterator().hasNext()) {
        LOGGER.info("Nenhuma pessoa encontrada pelo nome!");
        return new ResponseEntity<>(new ResponseModel("Nenhuma pessoa encontrada pelo nome!"),
            HttpStatus.NOT_FOUND);
      }
      List<PessoaDTO> listDTO = pessoas.stream().map(PessoaDTO::new).collect(Collectors.toList());
      return new ResponseEntity<>(new ResponseModel("Lista de Pessoas filtrada pelo nome", listDTO),
          HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error(e);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<ResponseModel> buscarPeloId(Long id) {
    try {
      Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
      if (pessoa == null) {
        LOGGER.info("Nenhuma pessoa encontrado pelo id!");
        return new ResponseEntity<>(new ResponseModel("Nenhuma pessoa encontrado pelo id!"),
            HttpStatus.NOT_FOUND);
      }
      PessoaDTO dto = new PessoaDTO(pessoa);
      return new ResponseEntity<>(new ResponseModel("Busca por ID", dto), HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Falha ao listar pessoa por id!\n" + e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<ResponseModel> buscarEnderecoPeloId(Long id,
      Boolean enderecoPrincipal) {
    try {
      Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
      if (pessoa == null) {
        LOGGER.info("Nenhuma pessoa encontrado pelo id!");
        return new ResponseEntity<>(new ResponseModel("Nenhuma pessoa encontrado pelo id!"),
            HttpStatus.NOT_FOUND);
      }
      PessoaDTO dto = new PessoaDTO(pessoa);
      if (enderecoPrincipal) {
        List<EnderecoDTO> dtoList = dto.getEnderecos().stream()
            .filter(EnderecoDTO::getEnderecoPrincipal).collect(
                Collectors.toList());
        return new ResponseEntity<>(new ResponseModel("Busca endereço principal por ID", dtoList),
            HttpStatus.OK);
      } else {
        return new ResponseEntity<>(new ResponseModel("Busca endereçp por ID", dto.getEnderecos()),
            HttpStatus.OK);
      }

    } catch (Exception e) {
      LOGGER.error("Falha ao listar pessoa por id!\n" + e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<ResponseModel> salvar(@Valid PessoaDTO pessoaDTO) {
    try {
      if (!validacaoEnderecos(pessoaDTO.getEnderecos())) {
        return new ResponseEntity<>(
            new ResponseModel("É necessário pelo menos 1 endereço e somente 1 pode o principal!"),
            HttpStatus.BAD_REQUEST);
      }
      if (existePessoaCadastrada(pessoaDTO.getNome())) {
        return new ResponseEntity<>(new ResponseModel("Nome já cadastrado no banco!"),
            HttpStatus.BAD_REQUEST);
      }
      Pessoa pessoa = new Pessoa(pessoaDTO);
      pessoaRepository.save(pessoa);
      return new ResponseEntity<>(new ResponseModel("Pessoa cadastrada com sucesso!"),
          HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Falha ao cadastrar pessoa!\n" + e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<ResponseModel> atualizar(@Valid PessoaDTO pessoaDTO, Long id) {
    try {
      if (!validacaoEnderecos(pessoaDTO.getEnderecos())) {
        return new ResponseEntity<>(
            new ResponseModel(
                "É necessário pelo menos 1 endereço e somente 1 pode ser o principal!"),
            HttpStatus.BAD_REQUEST);
      }
      Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
      Boolean existeNomePessoa = existePessoaCadastrada(pessoaDTO.getNome());
      if (pessoa == null) {
        return new ResponseEntity<>(
            new ResponseModel("Falha ao atualizar pessoa. Pessoa não encontrado na base de dados!"),
            HttpStatus.NOT_FOUND);
      }
      if (existeNomePessoa && !pessoaDTO.getNome().equalsIgnoreCase(pessoa.getNome())) {
        LOGGER.error("Nome já existente na base de dados");
        return new ResponseEntity<>(new ResponseModel("Nome já existente na base de dados"),
            HttpStatus.BAD_REQUEST);
      }
      pessoa.update(pessoaDTO);
      pessoaRepository.save(pessoa);
      LOGGER.info("Pessoa atualizada com sucesso!");
      return new ResponseEntity<>(new ResponseModel("Pessoa atualizada!"), HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Falha ao atualizar pessoa.\n" + e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<ResponseModel> adicionaEnderecoPessoa(Long id, EnderecoDTO dto) {
    try {
      Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
      if (pessoa == null) {
        LOGGER.error("Nenhuma pessoa encontrada!");
        return new ResponseEntity<>(new ResponseModel("Nenhuma pessoa encontrada!"),
            HttpStatus.NOT_FOUND);
      }
      if (pessoa.getEnderecos().contains(dto)) {
        return new ResponseEntity<>(new ResponseModel("Endereço já cadastrado!"),
            HttpStatus.BAD_REQUEST);
      }
      pessoa.getEnderecos().add(dto);
      if (!validacaoEnderecos(pessoa.getEnderecos())) {
        return ResponseEntity.badRequest()
            .body(new ResponseModel(
                "É necessário pelo menos 1 endereço e somente 1 pode ser o principal!"));
      }
      pessoaRepository.save(pessoa);
      return new ResponseEntity<>(new ResponseModel("Endereço atualizado!"), HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error(e);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<ResponseModel> deletar(Long id) {
    try {
      if (pessoaRepository.existsById(id)) {
        pessoaRepository.deleteById(id);
        LOGGER.info("Pessoa deletada com sucesso!");
        return new ResponseEntity<>(new ResponseModel("Pessoa deletada com sucesso!"),
            HttpStatus.OK);
      }
      return new ResponseEntity<>(new ResponseModel("ID inválido! Usuario não registrado no banco!"),
          HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      LOGGER.error("Falha ao deletar médico!\n" + e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
