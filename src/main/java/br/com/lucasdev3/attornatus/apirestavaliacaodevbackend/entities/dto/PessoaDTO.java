package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Endereco;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Pessoa;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  @NotBlank(message = "nome é obrigatorio")
  private String nome;

  @NotBlank(message = "data de nascimento é obrigatorio")

  private String dataNascimento;

  @NotNull(message = "endereco é obrigatorio")
  @Valid
  private List<Endereco> enderecos;

  public PessoaDTO(@Valid Pessoa pessoa) {
    this.nome = pessoa.getNome();
    this.dataNascimento = pessoa.getDataNascimento();
    this.enderecos = pessoa.getEnderecos();
  }


}
