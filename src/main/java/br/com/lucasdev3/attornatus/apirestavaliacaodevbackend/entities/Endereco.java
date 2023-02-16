package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Endereco implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "logradouro não pode ser nulo")
  private String logradouro;

  @NotBlank(message = "cep não pode ser nulo")
  private String cep;

  @NotBlank(message = "numero não pode ser nulo")
  private String numero;

  private Boolean enderecoPrincipal = false;

}
