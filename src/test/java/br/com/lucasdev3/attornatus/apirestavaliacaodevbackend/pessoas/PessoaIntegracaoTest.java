package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.pessoas;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Pessoa;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.EnderecoDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.PessoaDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.repositories.PessoaRepository;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.interfaces.PessoaService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class PessoaIntegracaoTest {

  @Autowired
  PessoaService service;

  @Autowired
  PessoaRepository repository;

  @Test
  public void deverar_listar_e_retornar_o_status_ok() throws InterruptedException {

    Thread.sleep(500);

    EnderecoDTO enderecoPessoa = new EnderecoDTO("teste2 logradouro", "12312-230",
        "55", true);
    Pessoa pessoa2 = new Pessoa(
        new PessoaDTO("lucas2", "20-06-1996", List.of(enderecoPessoa)));

    service.salvar(new PessoaDTO(pessoa2));

    HttpStatus statusCode = service.buscarTodos().getStatusCode();
    Assertions.assertEquals(HttpStatus.OK, statusCode);

    HttpStatus statusCode2 = service.buscarPeloId(1L).getStatusCode();
    Assertions.assertEquals(HttpStatus.OK, statusCode2);

    HttpStatus statusCode3 = service.buscarEnderecoPeloId(1L, false).getStatusCode();
    Assertions.assertEquals(HttpStatus.OK, statusCode3);

    HttpStatus statusCode4 = service.buscarEnderecoPeloId(1L, true).getStatusCode();
    Assertions.assertEquals(HttpStatus.OK, statusCode4);

  }

  @Test
  public void deverar_salvar_e_retornar_o_status_bad_request() throws InterruptedException {

    Thread.sleep(500);

    // Validando na lista de endereços que o tenha somente 1 endereço principal.

    EnderecoDTO endereco1Pessoa = new EnderecoDTO("teste logradouro", "12312-230", "55",
        true);
    EnderecoDTO endereco2Pessoa = new EnderecoDTO("teste2 logradouro", "12312-230",
        "55", true);
    Pessoa pessoa = new Pessoa(
        new PessoaDTO("lucas", "20-06-1996", Arrays.asList(endereco1Pessoa, endereco2Pessoa)));

    HttpStatus statusCodeSave = service.salvar(new PessoaDTO(pessoa)).getStatusCode();
    Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCodeSave);
  }

  @Test
  public void deverar_salvar_e_retornar_o_status_ok() throws InterruptedException {

    Thread.sleep(500);

    EnderecoDTO endereco1Pessoa = new EnderecoDTO("teste logradouro", "12312-230", "55",
        false);
    EnderecoDTO endereco2Pessoa = new EnderecoDTO("teste2 logradouro", "12312-230",
        "55", true);
    Pessoa pessoa = new Pessoa(
        new PessoaDTO("lucas3", "20-06-1996", Arrays.asList(endereco1Pessoa, endereco2Pessoa)));

    HttpStatus statusCodeSave = service.salvar(new PessoaDTO(pessoa)).getStatusCode();
    Assertions.assertEquals(HttpStatus.OK, statusCodeSave);
  }

  @Test
  public void deverar_atualizar_e_retornar_o_status_bad_request() throws InterruptedException {

    Thread.sleep(500);

    EnderecoDTO enderecoPessoa = new EnderecoDTO("teste logradouro", "12312-230", "55",
        true);
    Pessoa pessoa = new Pessoa(
        new PessoaDTO("lucas6", "20-06-1996", List.of(enderecoPessoa)));

    HttpStatus statusCodeSave = service.atualizar(new PessoaDTO(pessoa), 1L).getStatusCode();
    Assertions.assertEquals(HttpStatus.NOT_FOUND, statusCodeSave);

  }

  @Test
  public void deverar_atualizar_e_retornar_o_status_ok() throws InterruptedException {

    Thread.sleep(500);

    // Validando na lista de endereços que o tenha somente 1 endereço principal.

    EnderecoDTO enderecoPessoa = new EnderecoDTO("teste logradouro", "12312-230", "55",
        true);
    Pessoa pessoa = new Pessoa(
        new PessoaDTO("lucas7", "20-06-1996", List.of(enderecoPessoa)));

    service.salvar(new PessoaDTO(pessoa));

    Pessoa pessoaBuscada = repository.findById(1L).orElse(null);
    assert pessoaBuscada != null;
    pessoaBuscada.setNome("lucas77");
    HttpStatus statusCodeSave = service.atualizar(new PessoaDTO(pessoaBuscada), 1L).getStatusCode();
    Assertions.assertEquals(HttpStatus.OK, statusCodeSave);
  }


}
