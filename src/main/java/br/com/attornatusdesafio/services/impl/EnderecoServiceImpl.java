package br.com.attornatusdesafio.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.attornatusdesafio.entities.Endereco;
import br.com.attornatusdesafio.entities.Pessoa;
import br.com.attornatusdesafio.entities.enums.TipoEndereco;
import br.com.attornatusdesafio.repositories.EnderecoRepository;
import br.com.attornatusdesafio.services.EnderecoService;
import br.com.attornatusdesafio.services.PessoaService;
import br.com.attornatusdesafio.services.exceptions.EnderecoException;
import br.com.attornatusdesafio.services.exceptions.ObjectNotFoundException;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	private EnderecoRepository repository;

	@Autowired
	private PessoaService pessoaService;

	@Override
	public List<Endereco> findAllByPessoa(Long idPessoa) {
		return repository.findAllByPessoa(idPessoa);
	}

	public Endereco findById(Long id) {
		Optional<Endereco> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Endereco.class.getName()));
	}

	@Override
	public Endereco findByEnderecoPrincipal(Long idPessoa) {
		List<Endereco> enderecos = findAllByPessoa(idPessoa);

		Optional<Endereco> endereco = enderecos.stream().filter(e -> e.getTipoEndereco().equals(TipoEndereco.PRINCIPAL))
				.findFirst();

		return endereco.orElseThrow(() -> new EnderecoException("Você não possui um endereço principal"));
	}

	@Override
	public Endereco create(Long idPessoa, Endereco endereco) {
		Pessoa pessoa = pessoaService.findById(idPessoa);

		verificarTipoEndereco(pessoa.getEnderecos(), endereco);

		endereco = repository.save(endereco);

		pessoa.addEndereco(endereco);

		pessoaService.update(idPessoa, pessoa);

		return endereco;
	}

	@Override
	public Endereco update(Long idPessoa, Long idEndereco, Endereco obj) {
		Pessoa pessoa = pessoaService.findById(idPessoa);

		Endereco enderecoAtualizado = contemEndereco(pessoa.getEnderecos(), idEndereco);

		obj.setId(enderecoAtualizado.getId());

		verificarTipoEndereco(pessoa.getEnderecos(), obj);

		enderecoAtualizado.setCep(obj.getCep());
		enderecoAtualizado.setCidade(obj.getCidade());
		enderecoAtualizado.setLogradouro(obj.getLogradouro());
		enderecoAtualizado.setNumero(obj.getNumero());
		enderecoAtualizado.setTipoEndereco(obj.getTipoEndereco());

		return repository.save(enderecoAtualizado);
	}

	@Override
	public void delete(Long idPessoa, Long idEndereco) {
		Pessoa pessoa = pessoaService.findById(idPessoa);

		contemEndereco(pessoa.getEnderecos(), idEndereco);

		pessoa.getEnderecos().removeIf((e -> e.getId().equals(idEndereco)));

		repository.deleteById(idEndereco);

	}

	
	// Esse método verifica se a pessoa possui um Endereço Principal, caso possua, lança a Exception
	private void verificarTipoEndereco(List<Endereco> enderecos, Endereco obj) {
		if (obj.getTipoEndereco() == TipoEndereco.PRINCIPAL) {

			for (Endereco endereco : enderecos) {

				if (endereco.getTipoEndereco() == TipoEndereco.PRINCIPAL) {

					if (obj.getId() == null || !endereco.equals(obj)) {
						throw new EnderecoException("Já existe um endereço principal definido");
					}

				}
			}
		}

	}
	
	// Esse método verifica se na lista de enderecos de pessoa(especificada pelo id da requisição), possui o id do Endereço informado como argumento.
	// Objetivo do método é impedir que uma pessoa consiga editar/deletar um endereço que não seja dela
	private Endereco contemEndereco(List<Endereco> enderecosPessoa, Long idEndereco) {
		return enderecosPessoa.stream().filter(endereco -> endereco.getId() == idEndereco).findFirst()
				.orElseThrow(() -> new ObjectNotFoundException(
						"Objeto Não Encontrado! Id: " + idEndereco + ", Tipo: " + Endereco.class.getName()));
	}

}
