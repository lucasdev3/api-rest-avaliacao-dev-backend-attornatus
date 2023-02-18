package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.EnderecoDTO;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.dto.PessoaDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoas")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Pessoa implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome", unique = true)
  private String nome;

  @Column(name = "data_nascimento")
  private String dataNascimento;

  @ElementCollection(fetch = FetchType.EAGER)
  @Column(name = "endereco")
  @CollectionTable(name = "pessoa_endereco", joinColumns = @JoinColumn(name = "pessoa_id"))
  @AttributeOverrides({
      @AttributeOverride(name = "logradouro", column = @Column(name = "logradouro")),
      @AttributeOverride(name = "cep", column = @Column(name = "cep")),
      @AttributeOverride(name = "numero", column = @Column(name = "numero")),
      @AttributeOverride(name = "enderecoPrincipal", column = @Column(name = "enderecoPrincipal"))
  })
  private List<EnderecoDTO> enderecos;

  public Pessoa(PessoaDTO dto) {
    this.nome = dto.getNome();
    this.dataNascimento = dto.getDataNascimento();
    this.enderecos = dto.getEnderecos();
    if (dto.getEnderecos().size() == 1) {
      dto.getEnderecos().iterator().next().setEnderecoPrincipal(true);
    }
  }

  public void update(PessoaDTO dto) {
    this.nome = dto.getNome();
    this.dataNascimento = dto.getDataNascimento();
    this.enderecos = dto.getEnderecos();
    if (this.getEnderecos().isEmpty() && dto.getEnderecos().size() == 1) {
      dto.getEnderecos().iterator().next().setEnderecoPrincipal(true);
    }
  }

}
