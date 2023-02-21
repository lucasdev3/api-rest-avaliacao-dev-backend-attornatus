package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.interfaces;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.utils.ResponseModel;
import java.util.List;
import org.springframework.http.ResponseEntity;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.EnderecoDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.PessoaDTO;

public interface PessoaService {

  ResponseEntity<ResponseModel> buscarTodos();

  ResponseEntity<ResponseModel> buscarTodosPeloNome(String nome);

  ResponseEntity<ResponseModel> buscarPeloId(Long id);

  ResponseEntity<ResponseModel> buscarEnderecoPeloId(Long id, Boolean enderecoPrincipal);

  ResponseEntity<ResponseModel> salvar(PessoaDTO pessoaDTO);

  ResponseEntity<ResponseModel> atualizar(PessoaDTO pessoaDTO, Long id);

  ResponseEntity<ResponseModel> deletar(Long id);

  ResponseEntity<ResponseModel> adicionaEnderecoPessoa(Long id, EnderecoDTO dto);
}
