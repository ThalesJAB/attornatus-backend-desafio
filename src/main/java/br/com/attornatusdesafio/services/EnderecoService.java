package br.com.attornatusdesafio.services;

import java.util.List;

import br.com.attornatusdesafio.entities.Endereco;

public interface EnderecoService {

	List<Endereco> findAllByPessoa(Long idPessoa);
	
	Endereco findById(Long id);

	Endereco findByEnderecoPrincipal(Long idPessoa);

	Endereco create(Long idPessoa, Endereco endereco);

	Endereco update(Long idPessoa, Long idEndereco, Endereco obj);

	void delete(Long idPessoa, Long idEndereco);

}
