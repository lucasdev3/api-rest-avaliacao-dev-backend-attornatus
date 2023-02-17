package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.pessoas;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Endereco;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Pessoa;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.PessoaDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.services.pessoas.interfaces.PessoaService;
import java.util.Arrays;
import javax.validation.Valid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class PessoaIntegracaoTest {

  @Autowired
  PessoaService service;

  @Test
  public void devera_salvar_e_retornar_o_status_esperado_validando_lista_enderecos() {

    // Validando dentro da lista de endereços que o tenha somente 1 endereço principal.

    // CASO DE TESTE 1

    Endereco endereco1Pessoa1 = new Endereco("teste logradouro", "teste cep", "teste numero", true);
    Endereco endereco2Pessoa1 = new Endereco("teste2 logradouro", "teste2 cep", "teste2 numero", true);
    Pessoa pessoa = new Pessoa(new PessoaDTO("lucas", "20-06-1996", Arrays.asList(endereco1Pessoa1, endereco2Pessoa1)));

    var save = service.salvar(new PessoaDTO(pessoa));
    HttpStatus statusCodeSave = save.getStatusCode();
    Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCodeSave);

    // CASO DE TESTE 2

    Endereco endereco1Pessoa2 = new Endereco("teste logradouro", "teste cep", "teste numero", false);
    Endereco endereco2Pessoa2 = new Endereco("teste2 logradouro", "teste2 cep", "teste2 numero", true);
    Pessoa pessoa2 = new Pessoa(new PessoaDTO("lucas", "20-06-1996", Arrays.asList(endereco1Pessoa2, endereco2Pessoa2)));

    var save2 = service.salvar(new PessoaDTO(pessoa2));
    HttpStatus statusCodeSave2 = save2.getStatusCode();
    Assertions.assertEquals(HttpStatus.OK, statusCodeSave2);

  }


}
