package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.repositories;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

  ArrayList<Pessoa> findAllByNomeContaining(String nome);

  Optional<Pessoa> findByNome(String nome);

  Boolean existsByNome(String nome);

}
