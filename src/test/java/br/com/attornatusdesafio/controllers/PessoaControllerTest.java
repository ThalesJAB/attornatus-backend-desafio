package br.com.attornatusdesafio.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.attornatusdesafio.entities.Pessoa;
import br.com.attornatusdesafio.entities.dtos.PessoaDTO;
import br.com.attornatusdesafio.services.PessoaService;

@SpringBootTest
class PessoaControllerTest {

	private static final LocalDate DATA_DE_NASCIMENTO2 = LocalDate.of(2000, 8, 03);

	private static final String NOME2 = "Thales";

	private static final long ID_PESSOA2 = 2L;

	private static final LocalDate DATA_DE_NASCIMENTO1 = LocalDate.of(1969, 11, 15);

	private static final String NOME1 = "Ricardo";

	private static final long ID_PESSOA1 = 1L;

	@InjectMocks
	private PessoaController controller;

	@Mock
	private PessoaService service;

	@Mock
	private ModelMapper mapper;

	private ObjectMapper objectMapper;

	private Pessoa pessoa1;

	private Pessoa pessoa2;

	private PessoaDTO pessoaDTO1;

	private PessoaDTO pessoaDTO2;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		start();
	}

	@Test
	void whenFindByIdThenReturnSucess() {
		when(service.findById(anyLong())).thenReturn(pessoa1);
		
		ResponseEntity<Pessoa> response = controller.findById(ID_PESSOA1);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Pessoa.class, response.getBody().getClass());
		
		assertEquals(ID_PESSOA1, response.getBody().getId());
		assertEquals(NOME1, response.getBody().getNome());
		assertEquals(DATA_DE_NASCIMENTO1, response.getBody().getDataNascimento());
		
	}

	@Test
	void whenFindAllThenReturnAnListOfPessoas() {
		
		when(service.findAll()).thenReturn(new ArrayList<>(List.of(pessoa1, pessoa2)));
		when(mapper.map(any(), any())).thenReturn(pessoaDTO1);
		
		ResponseEntity<List<PessoaDTO>> response = controller.findAll();
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(PessoaDTO.class, response.getBody().get(0).getClass());
		
		response.getBody().get(1).getId();
		
		assertEquals(2, response.getBody().size());
		assertEquals(ID_PESSOA1, response.getBody().get(0).getId());
		assertEquals(NOME1, response.getBody().get(0).getNome());
		assertEquals(DATA_DE_NASCIMENTO1, response.getBody().get(0).getDataNascimento());
		
	}

	@Test
	void whenCreateReturnSucess() throws JsonProcessingException {
		when(service.create(any())).thenReturn(pessoa1);
		
		ResponseEntity<Pessoa> response = controller.create(pessoaDTO1);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Pessoa.class, response.getBody().getClass());
		
		assertNotNull(response.getHeaders().getLocation());
        URI expectedUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoa1.getId()).toUri();
        assertEquals(expectedUri, response.getHeaders().getLocation());

        String expectedJson = "{\"id\":1,\"nome\":\"Ricardo\",\"dataNascimento\":\"1969-11-15\",\"enderecos\":null}";
        String atualJson = objectMapper.writeValueAsString(response.getBody());
        assertEquals(expectedJson, atualJson);
		
		
		assertEquals(ID_PESSOA1, response.getBody().getId());
		assertEquals(NOME1, response.getBody().getNome());
		assertEquals(DATA_DE_NASCIMENTO1, response.getBody().getDataNascimento());

	}

	@Test
	void WhenUpdateReturnSucess() throws JsonProcessingException {
		when(service.update(anyLong(), any())).thenReturn(pessoa1);
		
		ResponseEntity<Pessoa> response = controller.update(ID_PESSOA1, pessoaDTO2);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Pessoa.class, response.getBody().getClass());
		
		assertEquals(ID_PESSOA1, response.getBody().getId());
		assertEquals(NOME1, response.getBody().getNome());
		assertEquals(DATA_DE_NASCIMENTO1, response.getBody().getDataNascimento());
		
	
		String expectedJson = "{\"id\":1,\"nome\":\"Ricardo\",\"dataNascimento\":\"1969-11-15\",\"enderecos\":null}";
        String atualJson = objectMapper.writeValueAsString(response.getBody());
        assertEquals(expectedJson, atualJson);
	}

	@Test
	void whenDeleteThenReturnSucess() {
		doNothing().when(service).delete(anyLong());

		ResponseEntity<Void> response = controller.delete(ID_PESSOA1);

		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());

		verify(service, times(1)).delete(anyLong());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

	}

	private void start() {
		pessoa1 = new Pessoa(ID_PESSOA1, NOME1, DATA_DE_NASCIMENTO1, null);
		pessoa2 = new Pessoa(ID_PESSOA2, NOME2, DATA_DE_NASCIMENTO2, null);
		pessoaDTO1 = new PessoaDTO(ID_PESSOA1, NOME1, DATA_DE_NASCIMENTO1);
		pessoaDTO2 = new PessoaDTO(ID_PESSOA2, NOME2, DATA_DE_NASCIMENTO2);
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

	}

}
