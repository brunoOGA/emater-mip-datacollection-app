package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class HarvestTest {

	private Harvest harvest;
	private Validator validator;
	
	@Before
	public void init() {
		harvest = Harvest.builder().name("").build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	@Test
	public void createHarvest() {
		Date begin = new Date(117, 8, 26);
		Date end = new Date(118, 1, 15);
		harvest.setId(1L);
		harvest.setBegin(begin);
		harvest.setName("P95R51");
		harvest.setEnd(end);
		
		assertThat(harvest.getId()).isEqualTo(1L);
		assertThat(harvest.getBegin()).isEqualTo(begin);
		assertThat(harvest.getName()).isEqualTo("P95r51");
		assertThat(harvest.getEnd()).isEqualTo(end);
	}
	
	@Test(expected = NullPointerException.class)
	public void createInvalidHarvest() {
		Harvest.builder().build();
	}
	
	@Test
	public void setName() {
		// execução
		harvest.setName("P95R51");

		// verificações
		assertThat(harvest.getName()).isEqualTo("P95r51");

	}

	@Test
	public void mustNotAcceptANameLessThanFive() { 
		// execução
		harvest.setName("ab");
		Set<ConstraintViolation<Harvest>> violations = validator.validate(harvest);
		
		// verificação
		assertThat(violations.toString()).contains("A identificação da safra deve ter entre 5 e 50 caracteres");
	}

	@Test
	public void mustNotAcceptANameGreaterThanFifty() { 
		// execução
		harvest.setName("ababababab ababababab ababababab ababababab ababababab");
		Set<ConstraintViolation<Harvest>> violations = validator.validate(harvest);
		
		// verificação
		assertThat(violations.toString()).contains("A identificação da safra deve ter entre 5 e 50 caracteres");
	}

	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		// execução
		harvest.setName(null);
	}
	
	@Test
	public void setBegin() {
		Date begin = new Date(117, 8, 26);
		harvest.setBegin(begin);
		assertThat(harvest.getBegin()).isEqualTo(begin);
	}
	
//	@Test(expected = NullPointerException.class)
//	public void setNullBegin() {
//		harvest.setBegin(null);
//	}
	
	@Test
	public void setEnd() {
		Date end = new Date(118, 1, 15);
		harvest.setEnd(end);
		assertThat(harvest.getEnd()).isEqualTo(end);
	}
	
//	@Test(expected = NullPointerException.class)
//	public void setNullEnd() {
//		harvest.setBegin(null);
//	}
	
}
