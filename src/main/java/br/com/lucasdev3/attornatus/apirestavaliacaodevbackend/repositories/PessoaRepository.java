package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.repositories;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.entities.Pessoa;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

  ArrayList<Pessoa> findAllByNomeContaining(String nome);

  Boolean existsByNome(String nome);

}
