package br.com.attornatusdesafio.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.attornatusdesafio.entities.Endereco;
import br.com.attornatusdesafio.services.EnderecoService;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/pessoas/{idPessoa}/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoService service;

	@GetMapping
	public ResponseEntity<List<Endereco>> findAll(@PathVariable Long idPessoa) {

		List<Endereco> enderecoList = service.findAllByPessoa(idPessoa);

		return ResponseEntity.ok().body(enderecoList);

	}
	
	@GetMapping(value="/principal")
	public ResponseEntity<Endereco> findByEnderecoPrincipal(@PathVariable Long idPessoa) {

		Endereco endereco = service.findByEnderecoPrincipal(idPessoa);

		return ResponseEntity.ok().body(endereco);

	}

	@PostMapping
	public ResponseEntity<Endereco> create(@Valid @PathVariable Long idPessoa, @RequestBody Endereco endereco) {

		Endereco enderecoNovo = service.create(idPessoa, endereco);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/pessoas/{idPessoa}/enderecos/{id}").buildAndExpand(idPessoa, enderecoNovo.getId()).toUri();

		return ResponseEntity.created(uri).body(enderecoNovo);

	}

	@PutMapping(value = "/{idEndereco}")
	public ResponseEntity<Endereco> update(@Valid @PathVariable Long idPessoa, @PathVariable Long idEndereco,
			@RequestBody Endereco obj) {
		obj = service.update(idPessoa, idEndereco, obj);

		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping(value = "/{idEndereco}")
	public ResponseEntity<Void> delete(@PathVariable Long idPessoa, @PathVariable Long idEndereco) {
		service.delete(idPessoa, idEndereco);

		return ResponseEntity.noContent().build();
	}

}
