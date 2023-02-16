package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Endereco;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Pessoa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnderecoDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  @NotBlank
  private String logradouro;

  @NotBlank
  private String cep;

  @NotBlank
  private String numero;

  @NotNull
  private Boolean enderecoPrincipal;

  public EnderecoDTO(Endereco endereco) {
    this.logradouro = endereco.getLogradouro();
    this.cep = endereco.getCep();
    this.numero = endereco.getNumero();
    this.enderecoPrincipal = endereco.getEnderecoPrincipal();
  }

  public Set<EnderecoDTO> listToDTO(List<Endereco> enderecoIterable,
      ArrayList<EnderecoDTO> arrayList) {
    enderecoIterable.forEach(endereco -> arrayList.add(new EnderecoDTO(endereco)));
    return new HashSet<>(arrayList);
  }

}
