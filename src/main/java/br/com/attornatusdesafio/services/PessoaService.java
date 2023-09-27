package br.com.attornatusdesafio.services;

import java.util.List;

import br.com.attornatusdesafio.entities.Pessoa;
import br.com.attornatusdesafio.entities.dtos.PessoaDTO;

public interface PessoaService {

	List<Pessoa> findAll();

	Pessoa findById(Long id);

	Pessoa create(PessoaDTO obj);

	Pessoa update(Long id, PessoaDTO obj);

	void delete(Long id);
	
}
