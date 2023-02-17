package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.interfaces;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Endereco;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.PessoaDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface PessoaService {

  ResponseEntity<List<PessoaDTO>> buscarTodos();

  ResponseEntity<List<PessoaDTO>> buscarTodosPeloNome(String nome);

  ResponseEntity<PessoaDTO> buscarPeloId(Long id);

  ResponseEntity<List<Endereco>> buscarEnderecoPeloId(Long id, Boolean enderecoPrincipal);

  ResponseEntity<?> salvar(PessoaDTO pessoaDTO);

  ResponseEntity<?> atualizar(PessoaDTO pessoaDTO, Long id);

  ResponseEntity<?> deletar(Long id);

}
