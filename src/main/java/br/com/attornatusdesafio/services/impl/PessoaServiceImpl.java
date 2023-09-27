package br.com.attornatusdesafio.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.attornatusdesafio.entities.Pessoa;
import br.com.attornatusdesafio.entities.dtos.PessoaDTO;
import br.com.attornatusdesafio.repositories.PessoaRepository;
import br.com.attornatusdesafio.services.PessoaService;
import br.com.attornatusdesafio.services.exceptions.ObjectNotFoundException;

@Service
public class PessoaServiceImpl implements PessoaService {
	
	@Autowired
	private PessoaRepository repository;
	

	@Override
	public List<Pessoa> findAll() {
		return repository.findAll();
	}

	@Override
	public Pessoa findById(Long id) {
		Optional<Pessoa> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Pessoa.class.getName()));
	}

	@Override
	public Pessoa create(PessoaDTO obj) {
		Pessoa novaPessoa = new Pessoa();
		novaPessoa.setNome(obj.getNome());
		novaPessoa.setDataNascimento(obj.getDataNascimento());
		return repository.save(novaPessoa);
	}

	@Override
	public Pessoa update(Long id, PessoaDTO obj) {
		Pessoa entity = findById(id);

		entity.setDataNascimento(obj.getDataNascimento());
		entity.setNome(obj.getNome());

		return repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
		
	}
	
}
