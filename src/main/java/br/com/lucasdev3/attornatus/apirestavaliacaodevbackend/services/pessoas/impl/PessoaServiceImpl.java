package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.impl;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Endereco;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Pessoa;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.PessoaDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.repositories.PessoaRepository;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.interfaces.PessoaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PessoaServiceImpl implements PessoaService {

  @Autowired
  private PessoaRepository pessoaRepository;

  private static final Logger LOGGER = Logger.getLogger(PessoaServiceImpl.class);

  @Override
  public ResponseEntity<Set<PessoaDTO>> buscarTodos() {
    try {
      List<Pessoa> pessoas = pessoaRepository.findAll();
      if (pessoas.isEmpty()) {
        LOGGER.info("Nenhuma pessoa encontrada!");
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(pessoas.stream().map(PessoaDTO::new).collect(Collectors.toSet()));
    } catch (Exception e) {
      LOGGER.error(e);
    }
    return ResponseEntity.internalServerError().build();
  }

  @Override
  public ResponseEntity<Set<PessoaDTO>> buscarTodosPeloNome(String nome) {
    try {
      List<Pessoa> pessoas = pessoaRepository.findAllByNomeContaining(nome);
      if (pessoas.iterator().hasNext()) {
        LOGGER.info("Nenhuma pessoa encontrada pelo nome!");
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(pessoas.stream().map(PessoaDTO::new).collect(Collectors.toSet()));
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
  public ResponseEntity<?> salvar(PessoaDTO pessoaDTO) {
    try {
      validacaoEnderecos(pessoaDTO.getEnderecos());
      Pessoa pessoa = new Pessoa(pessoaDTO);
      pessoaRepository.save(pessoa);
      return ResponseEntity.ok().body("Pessoa cadastrada com sucesso!");
    } catch (Exception e) {
      LOGGER.error("Falha ao cadastrar pessoa!\n" + e.getMessage());
    }
    return ResponseEntity.internalServerError().build();
  }

  @Override
  public ResponseEntity<?> atualizar(PessoaDTO pessoaDTO, Long id) {
    try {
      Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
      if (pessoa == null) {
        LOGGER.error("Falha ao atualizar pessoa. Pessoa não encontrado na base de dados!");
        return ResponseEntity.notFound().build();
      }
      validacaoEnderecos(pessoaDTO.getEnderecos());
      pessoa.update(pessoaDTO);
      pessoaRepository.save(pessoa);
      LOGGER.info("Pessoa atualizada com sucesso!");
      return ResponseEntity.ok().body("pessoa atualizada!");

    } catch (Exception e) {
      LOGGER.error("Falha ao atualizar pessoa.\n" + e.getMessage());
    }
    return ResponseEntity.internalServerError().build();
  }

  @Override
  public ResponseEntity<?> deletar(Long id) {
    try {
      pessoaRepository.deleteById(id);
      LOGGER.info("Pessoa deletada com sucesso!");
      return ResponseEntity.ok().body("pessoa deletada com sucesso!");
    } catch (Exception e) {
      LOGGER.error("Falha ao deletar médico!\n" + e.getMessage());
    }
    return ResponseEntity.internalServerError().build();
  }

  private static void validacaoEnderecos(Set<Endereco> enderecos) {
    int cnt = 0;
    for (Endereco endereco : enderecos) {
      if (endereco.getEnderecoPrincipal()) {
        cnt++;
      }
    }
    if (cnt > 1) {
      ResponseEntity.badRequest().body("Pessoa só pode ter um endereço principal");
    }
  }


}
