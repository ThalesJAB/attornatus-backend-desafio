package br.com.attornatusdesafio.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import br.com.attornatusdesafio.entities.Pessoa;
import br.com.attornatusdesafio.entities.dtos.PessoaDTO;
import br.com.attornatusdesafio.services.PessoaService;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/pessoas")
public class PessoaController {

	@Autowired
	private PessoaService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<List<PessoaDTO>> findAll() {
		 return ResponseEntity.ok().body(service.findAll().stream()
				 .map(pessoa -> mapper.map(pessoa, PessoaDTO.class))
				 .collect(Collectors.toList()));
	}
	
	

	@GetMapping(value = "/{id}")
	public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
		Pessoa obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Pessoa> create(@Valid @RequestBody PessoaDTO obj) {
		
		Pessoa novaPessoa = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novaPessoa.getId()).toUri();
		return ResponseEntity.created(uri).body(novaPessoa);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> update(@PathVariable Long id, @Valid @RequestBody PessoaDTO obj) {

		Pessoa attPessoa = service.update(id, obj);

		return ResponseEntity.ok().body(attPessoa);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		service.delete(id);

		return ResponseEntity.noContent().build();
	}

}
