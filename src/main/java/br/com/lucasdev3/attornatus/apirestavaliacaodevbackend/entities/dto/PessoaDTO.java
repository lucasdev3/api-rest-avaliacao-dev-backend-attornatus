package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  @NotBlank(message = "nome é obrigatorio")
  private String nome;

  @NotBlank(message = "data de nascimento é obrigatorio")
  @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "data de nascimento não obedece ao padrao dd-MM-yyyy")
  private String dataNascimento;

  @NotNull(message = "endereco é obrigatorio")
  @Valid
  private List<EnderecoDTO> enderecos;

  public PessoaDTO(Pessoa pessoa) {
    this.id = pessoa.getId();
    this.nome = pessoa.getNome();
    this.dataNascimento = pessoa.getDataNascimento();
    this.enderecos = pessoa.getEnderecos();
  }

  public PessoaDTO(String nome, String dataNascimento, List<EnderecoDTO> enderecos) {
    this.nome = nome;
    this.dataNascimento = dataNascimento;
    this.enderecos = enderecos;
  }

}
