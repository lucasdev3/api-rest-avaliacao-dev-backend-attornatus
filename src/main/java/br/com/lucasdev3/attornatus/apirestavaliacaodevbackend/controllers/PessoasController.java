package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.controllers;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Endereco;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.PessoaDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.interfaces.PessoaService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoasController {

  @Autowired
  private final PessoaService pessoaService;

  public PessoasController(PessoaService pessoaService) {
    this.pessoaService = pessoaService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PessoaDTO>> listAll() {
    return pessoaService.buscarTodos();
  }

  @GetMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PessoaDTO>> listAll(@RequestParam String nome) {
    return pessoaService.buscarTodosPeloNome(nome);
  }

  @GetMapping(value = "/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PessoaDTO> getById(@PathVariable long id) {
    return pessoaService.buscarPeloId(id);
  }

  @GetMapping(value = "/buscar-enderecos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Endereco>> getEnderecosById(@PathVariable long id,
      @RequestParam Boolean enderecoPrincipal) {
    return pessoaService.buscarEnderecoPeloId(id, enderecoPrincipal);
  }

  @PostMapping(value = "/salvar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> save(@Valid @RequestBody PessoaDTO pessoaDTO) {
    return pessoaService.salvar(pessoaDTO);
  }

  @PutMapping(value = "/atualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> update(@Valid @RequestBody PessoaDTO pessoaDTO,
      @PathVariable long id) {
    return pessoaService.atualizar(pessoaDTO, id);
  }

  @DeleteMapping(value = "/deletar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable long id) {
    return pessoaService.deletar(id);
  }

}
