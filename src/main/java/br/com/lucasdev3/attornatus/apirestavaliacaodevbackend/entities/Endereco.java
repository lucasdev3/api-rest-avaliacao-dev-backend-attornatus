package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
//@Table(name = "enderecos")
@Embeddable
@Getter
@Setter
//@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Endereco implements Serializable {

  private static final long serialVersionUID = 1L;

//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;

//  @Column(name = "logradouro", nullable = false)
  private String logradouro;

//  @Column(name = "cep", nullable = false)
  private String cep;

//  @Column(name = "numero", nullable = false)
  private String numero;

//  @Column(name = "endereco_principal", nullable = false)
  private Boolean enderecoPrincipal = false;

}
