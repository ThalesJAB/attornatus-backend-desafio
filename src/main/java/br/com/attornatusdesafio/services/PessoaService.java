package br.com.attornatusdesafio.services;

import java.util.List;

import br.com.attornatusdesafio.entities.Pessoa;

public interface PessoaService {

	List<Pessoa> findAll();

	Pessoa findById(Long id);

	Pessoa create(Pessoa obj);

	Pessoa update(Long id, Pessoa obj);

	void delete(Long id);
	
}
