package br.com.attornatusdesafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.attornatusdesafio.entities.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

	
	@Query("SELECT e FROM Endereco e WHERE e.id IN (SELECT endereco.id FROM Pessoa p JOIN p.enderecos endereco WHERE p.id = :idpessoa)")
	List<Endereco> findAllByPessoa(@Param(value = "idpessoa") Long idPessoa);

}
