package br.com.attornatusdesafio.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.attornatusdesafio.entities.Endereco;
import br.com.attornatusdesafio.entities.enums.TipoEndereco;
import br.com.attornatusdesafio.services.EnderecoService;

@SpringBootTest
class EnderecoControllerTest {

	private static final Long ID_PESSOA = 1L;

	private static final Long ID_ENDERECO2 = 2L;

	private static final TipoEndereco TIPO_ENDERECO2 = TipoEndereco.SECUNDARIO;

	private static final String CEP2 = "53432-421";

	private static final Integer NUMERO2 = 453;

	private static final String CIDADE2 = "São Paulo";

	private static final String LOGRADOURO2 = "Rua x";

	private static final TipoEndereco TIPO_ENDERECO1 = TipoEndereco.PRINCIPAL;

	private static final String CIDADE1 = "Curitiba";

	private static final Integer NUMERO1 = 40;

	private static final String CEP1 = "34567-890";

	private static final Long ID_ENDERECO1 = 1L;

	private static final String LOGRADOURO1 = "Rua D";

	@InjectMocks
	private EnderecoController controller;

	@Mock
	private EnderecoService service;

	private Endereco endereco1;

	private Endereco endereco2;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		start();
	}

	@Test
	void whenFindAllThenReturnAListOfAdressPessoa() {
		
		when(service.findAllByPessoa(anyLong())).thenReturn(new ArrayList<>(List.of(endereco1, endereco2)));

		
		ResponseEntity<List<Endereco>> response = controller.findAllByPessoa(ID_PESSOA);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(Endereco.class, response.getBody().get(0).getClass());
		
		assertEquals(2, response.getBody().size());
		assertEquals(ID_ENDERECO1, response.getBody().get(0).getId());
		assertEquals(LOGRADOURO1, response.getBody().get(0).getLogradouro());
		assertEquals(CIDADE1, response.getBody().get(0).getCidade());
		assertEquals(NUMERO1, response.getBody().get(0).getNumero());
		assertEquals(CEP1, response.getBody().get(0).getCep());
		assertEquals(TIPO_ENDERECO1, response.getBody().get(0).getTipoEndereco());

	}

	@Test
	void whenFindByEnderecoPrincipalThenReturnAnListOfPessoa() {
		
		when(service.findByEnderecoPrincipal(anyLong())).thenReturn(endereco1);

		
		ResponseEntity<Endereco> response = controller.findByEnderecoPrincipal(ID_PESSOA);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Endereco.class, response.getBody().getClass());
		
		assertEquals(ID_ENDERECO1, response.getBody().getId());
		assertEquals(LOGRADOURO1, response.getBody().getLogradouro());
		assertEquals(CIDADE1, response.getBody().getCidade());
		assertEquals(NUMERO1, response.getBody().getNumero());
		assertEquals(CEP1, response.getBody().getCep());
		assertEquals(TIPO_ENDERECO1, response.getBody().getTipoEndereco());

	}

	@Test
	void whenCreateReturnSucess() throws JsonProcessingException {
		when(service.create(anyLong(), any())).thenReturn(endereco1);
		
		ResponseEntity<Endereco> response = controller.create(ID_PESSOA, endereco1);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Endereco.class, response.getBody().getClass());
		
		assertEquals(ID_ENDERECO1, response.getBody().getId());
		assertEquals(LOGRADOURO1, response.getBody().getLogradouro());
		assertEquals(CIDADE1, response.getBody().getCidade());
		assertEquals(NUMERO1, response.getBody().getNumero());
		assertEquals(CEP1, response.getBody().getCep());
		assertEquals(TIPO_ENDERECO1, response.getBody().getTipoEndereco());
		
        String expectedJson = "{\"id\":1,\"logradouro\":\"Rua D\",\"cep\":\"34567-890\",\"numero\":40,\"cidade\":\"Curitiba\",\"tipoEndereco\":\"PRINCIPAL\"}";
        String atualJson = objectMapper.writeValueAsString(response.getBody());
        assertEquals(expectedJson, atualJson);
	
		
	}

	@Test
	void whenUpdateReturnSucess() throws JsonProcessingException {
		when(service.update(anyLong(),  anyLong(), any())).thenReturn(endereco1);
		
		ResponseEntity<Endereco> response = controller.update(ID_PESSOA, ID_ENDERECO1, endereco2);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Endereco.class, response.getBody().getClass());
		
		
		assertEquals(ID_ENDERECO1, response.getBody().getId());
		assertEquals(LOGRADOURO1, response.getBody().getLogradouro());
		assertEquals(CIDADE1, response.getBody().getCidade());
		assertEquals(NUMERO1, response.getBody().getNumero());
		assertEquals(CEP1, response.getBody().getCep());
		assertEquals(TIPO_ENDERECO1, response.getBody().getTipoEndereco());
		
        String expectedJson = "{\"id\":1,\"logradouro\":\"Rua D\",\"cep\":\"34567-890\",\"numero\":40,\"cidade\":\"Curitiba\",\"tipoEndereco\":\"PRINCIPAL\"}";
        String atualJson = objectMapper.writeValueAsString(response.getBody());
        assertEquals(expectedJson, atualJson);
		
		
	}

	@Test
	void testDelete() {
		doNothing().when(service).delete(anyLong(), anyLong());

		ResponseEntity<Void> response = controller.delete(ID_PESSOA, ID_ENDERECO1);

		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());

		verify(service, times(1)).delete(anyLong(), anyLong());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	private void start() {
		endereco1 = new Endereco(ID_ENDERECO1, LOGRADOURO1, CEP1, NUMERO1, CIDADE1, TIPO_ENDERECO1);
		endereco2 = new Endereco(ID_ENDERECO2, LOGRADOURO2, CEP2, NUMERO2, CIDADE2, TIPO_ENDERECO2);

		objectMapper = new ObjectMapper();
	}

}
