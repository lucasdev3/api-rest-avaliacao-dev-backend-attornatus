package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.interfaces;

import java.util.List;
import org.springframework.http.ResponseEntity;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.EnderecoDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.PessoaDTO;

public interface PessoaService {

  ResponseEntity<List<PessoaDTO>> buscarTodos();

  ResponseEntity<List<PessoaDTO>> buscarTodosPeloNome(String nome);

  ResponseEntity<PessoaDTO> buscarPeloId(Long id);

  ResponseEntity<List<EnderecoDTO>> buscarEnderecoPeloId(Long id, Boolean enderecoPrincipal);

  ResponseEntity<?> salvar(PessoaDTO pessoaDTO);

  ResponseEntity<?> atualizar(PessoaDTO pessoaDTO, Long id);

  ResponseEntity<?> deletar(Long id);

}
