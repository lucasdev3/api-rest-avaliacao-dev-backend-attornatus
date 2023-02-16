package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.interfaces;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.PessoaDTO;
import java.util.Set;
import org.springframework.http.ResponseEntity;

public interface PessoaService {

  ResponseEntity<Set<PessoaDTO>> buscarTodos();

  ResponseEntity<Set<PessoaDTO>> buscarTodosPeloNome(String nome);

  ResponseEntity<PessoaDTO> buscarPeloId(Long id);

  ResponseEntity<?> salvar(PessoaDTO pessoaDTO);

  ResponseEntity<?> atualizar(PessoaDTO pessoaDTO, Long id);

  ResponseEntity<?> deletar(Long id);

}
