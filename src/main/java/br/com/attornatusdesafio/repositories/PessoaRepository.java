package br.com.attornatusdesafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.attornatusdesafio.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
