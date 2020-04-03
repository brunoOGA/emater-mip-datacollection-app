package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import lombok.Getter;

public class PestNaturalPredatorTest {

	private PestNaturalPredator pestNaturalPredator;
	private Validator validator;
	
	@Before
	public void init() {
		pestNaturalPredator = PestNaturalPredator.builder().usualName("").build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void createPest() {
		pestNaturalPredator.setId(1l);
		pestNaturalPredator.setUsualName("Cycloneda sanguinea");
		
		assertThat(pestNaturalPredator.getId()).isEqualTo(1l);
		assertThat(pestNaturalPredator.getUsualName()).isEqualTo("Cycloneda Sanguinea");
	}
	
	@Test
	public void setUsualName() {
		pestNaturalPredator.setUsualName("Cycloneda sanguinea");

		assertThat(pestNaturalPredator.getUsualName()).isEqualTo("Cycloneda Sanguinea");

	}

	@Test
	public void mustNotAcceptANameLessThanFive() { 
		pestNaturalPredator.setUsualName("abab");
		Set<ConstraintViolation<PestNaturalPredator>> violations = validator.validate(pestNaturalPredator);
		
		assertThat(violations.toString()).contains("O nome deve ter entre 5 e 50 caracteres");
	}

	@Test
	public void mustNotAcceptANameGreaterThanFifty() { 
		pestNaturalPredator.setUsualName("ababababab ababababab ababababab ababababab abababa");
		Set<ConstraintViolation<PestNaturalPredator>> violations = validator.validate(pestNaturalPredator);
		
		assertThat(violations.toString()).contains("O nome deve ter entre 5 e 50 caracteres");
	}

	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		pestNaturalPredator.setUsualName(null);
	}
	
	@Test
	public void getDescription() {
		pestNaturalPredator.setUsualName("Cycloneda sanguinea");

		assertThat(pestNaturalPredator.getDescription()).isEqualTo("Cycloneda Sanguinea");
	}
}
