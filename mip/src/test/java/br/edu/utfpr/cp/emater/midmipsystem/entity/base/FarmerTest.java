package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class FarmerTest {

	private Farmer farmer;
	private Validator validator;
	
	@Before
	public void init() {
		farmer = Farmer.builder().name("").build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}

	@Test
	public void createFarmerTest() {
		// cenário
		farmer.setId(1l);
		farmer.setName("Gilson Dariva");

		// verificações
		assertThat(farmer.getId()).isEqualTo(1L);
		assertThat(farmer.getName()).isEqualTo("Gilson Dariva");
	}

	@Test
	public void setName() {
		// execução
		farmer.setName("Gilson Dariva");

		// verificações
		assertThat(farmer.getName()).isEqualTo("Gilson Dariva");

	}

	@Test
	public void mustNotAcceptANameLessThanThree() { 
		// execução
		farmer.setName("ab");
		Set<ConstraintViolation<Farmer>> violations = validator.validate(farmer);
		
		// verificação
		assertThat(violations.toString()).contains("O nome deve ter entre 3 e 50 caracteres");
	}

	@Test
	public void mustNotAcceptANameGreaterThanFifty() { 
		// execução
		farmer.setName("ababababab ababababab ababababab ababababab ababababab");
		Set<ConstraintViolation<Farmer>> violations = validator.validate(farmer);
		
		// verificação
		assertThat(violations.toString()).contains("O nome deve ter entre 3 e 50 caracteres");
	}


	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		// execução
		farmer.setName(null);
	}


}