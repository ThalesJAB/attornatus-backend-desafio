package br.com.attornatusdesafio.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.attornatusdesafio.entities.Pessoa;
import br.com.attornatusdesafio.entities.dtos.PessoaDTO;
import br.com.attornatusdesafio.repositories.PessoaRepository;
import br.com.attornatusdesafio.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class PessoaServiceImplTest {
	private static final LocalDate DATA_DE_NASCIMENTO2 = LocalDate.of(2000, 8, 03);

	private static final String NOME2 = "Thales";

	private static final long ID_PESSOA2 = 2L;

	private static final LocalDate DATA_DE_NASCIMENTO1 = LocalDate.of(1969, 11, 15);

	private static final String NOME1 = "Ricardo";

	private static final long ID_PESSOA1 = 1L;

	@InjectMocks
	private PessoaServiceImpl service;

	@Mock
	private PessoaRepository repository;

	@Mock
	private ModelMapper mapper;

	private Pessoa pessoa1;

	private Pessoa pessoa2;

	private PessoaDTO pessoaDTO1;

	private Optional<Pessoa> pessoaOptional;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		start();
	}

	@Test
	void whenFindByIdThenReturnAnPessoaInstance() {
		when(repository.findById(anyLong())).thenReturn(pessoaOptional);

		Pessoa response = service.findById(ID_PESSOA1);

		assertNotNull(response);
		assertEquals(Pessoa.class, response.getClass());
		assertEquals(ID_PESSOA1, response.getId());
		assertEquals(NOME1, response.getNome());
		assertEquals(DATA_DE_NASCIMENTO1, response.getDataNascimento());


	}

	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(ID_PESSOA1)).thenReturn(pessoaOptional);
		
		try {
			service.findById(ID_PESSOA2);
			
		}catch(Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto Não Encontrado! Id: " + ID_PESSOA2 + ", Tipo: " + Pessoa.class.getName(), e.getMessage());
		}
	}

	@Test
	void whenFindAllThenReturnAnListOfPessoas() {
		when(repository.findAll()).thenReturn(List.of(pessoa1, pessoa2));
		
		List<Pessoa> response = service.findAll();
		
		assertNotNull(response);
		assertEquals(2, response.size());
		assertEquals(Pessoa.class, response.get(0).getClass());
		assertEquals(Pessoa.class, response.get(1).getClass());
		assertEquals(ID_PESSOA1, response.get(0).getId());
		assertEquals(NOME1, response.get(0).getNome());
		assertEquals(DATA_DE_NASCIMENTO1, response.get(0).getDataNascimento());
		assertEquals(ID_PESSOA2, response.get(1).getId());
		assertEquals(NOME2, response.get(1).getNome());
		assertEquals(DATA_DE_NASCIMENTO2, response.get(1).getDataNascimento());

	}

	@Test
	void whenCreateReturnSucess() {
		when(repository.save(any())).thenReturn(pessoa1);
		
		Pessoa response = service.create(pessoaDTO1);
		
		assertNotNull(response);
		assertEquals(Pessoa.class, response.getClass());
		assertEquals(ID_PESSOA1, response.getId());
		assertEquals(NOME1, response.getNome());
		assertEquals(DATA_DE_NASCIMENTO1, response.getDataNascimento());
		
	}

	@Test
	void whenUpdateReturnSucess() {
		when(repository.findById(anyLong())).thenReturn(pessoaOptional);
		when(repository.save(any())).thenReturn(pessoa2);
		
		Pessoa response = service.update(ID_PESSOA1, pessoaDTO1);
		
		assertNotNull(response);
		assertEquals(Pessoa.class, response.getClass());
		assertEquals(NOME2, response.getNome());
		assertEquals(DATA_DE_NASCIMENTO2, response.getDataNascimento());
		
	}

	@Test
	void whenUpdateReturnAnObjectNotFoundException() {
		when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Objeto Não Encontrado! Id: " + ID_PESSOA1 + ", Tipo: " + Pessoa.class.getName()));
		
		try {
			
			service.update(ID_PESSOA1, pessoaDTO1);
			
		} catch(Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto Não Encontrado! Id: " + ID_PESSOA1 + ", Tipo: " + Pessoa.class.getName(), e.getMessage());
		}
	
	}

	@Test
	void WhenDeleteReturnSucess() {
		when(repository.findById(anyLong())).thenReturn(pessoaOptional);
		doNothing().when(repository).deleteById(anyLong());
		
		
		service.delete(ID_PESSOA1);
		verify(repository, times(1)).deleteById(anyLong());;
	}

	@Test
	void WhenDeleteReturnAnObjectNotFound() {
		when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Objeto Não Encontrado! Id: " + ID_PESSOA1 + ", Tipo: " + Pessoa.class.getName()));
		
		try {
			service.delete(ID_PESSOA1);
		}catch(Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto Não Encontrado! Id: " + ID_PESSOA1 + ", Tipo: " + Pessoa.class.getName(), e.getMessage());
		}
		
		
		
	}

	private void start() {
		pessoa1 = new Pessoa(ID_PESSOA1, NOME1, DATA_DE_NASCIMENTO1, null);
		pessoa2 = new Pessoa(ID_PESSOA2, NOME2, DATA_DE_NASCIMENTO2, null);
		pessoaDTO1 = new PessoaDTO(ID_PESSOA1, NOME1, DATA_DE_NASCIMENTO1);
		pessoaOptional = Optional.of(new Pessoa(ID_PESSOA1, NOME1, DATA_DE_NASCIMENTO1, null));
	}

}
