package br.com.attornatusdesafio.entities;

import java.io.Serializable;

import br.com.attornatusdesafio.entities.enums.TipoEndereco;
import br.com.attornatusdesafio.entities.interfaces.Cep;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude={"logradouro", "cep", "cidade", "numero"})
public class Endereco implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter private Long id;
	
    @NotBlank(message = "Campo LOGRADOURO é requerido")
    @Getter @Setter private String logradouro;

    @NotBlank(message = "Campo CEP é requerido")
    @Cep
    @Getter @Setter private String cep;

    @NotNull(message = "Campo NÚMERO é requerido")
    @Positive(message = "O campo NÚMERO deve ser um número inteiro positivo")
    @Getter @Setter private Integer numero;

    @NotBlank(message = "Campo CIDADE é requerido")
    @Getter @Setter private String cidade;
    
    @NotNull(message = "Campo ENDEREÇO é requerido")
    @Getter @Setter private TipoEndereco tipoEndereco;

}
