package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "logradouro não pode ser nulo")
  private String logradouro;

  @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "cep não obedece ao padrao xxxxx-xxx")
  @NotBlank(message = "cep não pode ser nulo")
  private String cep;

  @NotBlank(message = "numero não pode ser nulo")

  @Pattern(regexp = "^\\d{1,5}$", message = "numero não obedece ao padrao determinado. Deve estar entre 1 e 99999")
  private String numero;

  private Boolean enderecoPrincipal = false;

  public void setEnderecoPrincipal(boolean bool) {
    this.enderecoPrincipal = bool;
  }
}
