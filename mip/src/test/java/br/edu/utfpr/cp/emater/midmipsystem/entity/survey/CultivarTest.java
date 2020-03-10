package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.*;

import org.junit.Before;
import org.junit.Test;

public class CultivarTest {

	private Cultivar cultivar;
	private Validator validator;
	
	@Before
	public void init() {
		cultivar = Cultivar.builder().build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	@Test
	public void createCultivarTest() {
		// cenário
		cultivar.setId(1L);
		cultivar.setName("P95R51");

		// verificações
		assertThat(cultivar.getId()).isEqualTo(1L);
		assertThat(cultivar.getName()).isEqualTo("P95R51");
	}
	
	@Test
	public void createCultivarWithoutValues() {
		assertThat(cultivar.getId()).isNull();
		assertThat(cultivar.getName()).isNull();
	}
	
	@Test
	public void setName() {
		// execução
		cultivar.setName("P95R51");

		// verificações
		assertThat(cultivar.getName()).isEqualTo("P95R51");

	}

	@Test
	public void mustNotAcceptANameLessThanThree() { 
		// execução
		cultivar.setName("ab");
		Set<ConstraintViolation<Cultivar>> violations = validator.validate(cultivar);
		
		// verificação
		assertThat(violations.toString()).contains("A identificação da cultivar deve ter entre 3 e 50 caracteres");
	}

	@Test
	public void mustNotAcceptANameGreaterThanFifty() { 
		// execução
		cultivar.setName("ababababab ababababab ababababab ababababab ababababab");
		Set<ConstraintViolation<Cultivar>> violations = validator.validate(cultivar);
		
		// verificação
		assertThat(violations.toString()).contains("A identificação da cultivar deve ter entre 3 e 50 caracteres");
	}

	
	
	

}
