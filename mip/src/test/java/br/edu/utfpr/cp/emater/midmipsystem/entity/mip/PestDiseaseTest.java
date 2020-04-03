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

public class PestDiseaseTest {
	private PestDisease pestDisease;
	private Validator validator;
	
	@Before
	public void init() {
		pestDisease = PestDisease.builder().usualName("").build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void createPest() {
		pestDisease.setId(1l);
		pestDisease.setUsualName("Lagartas com Nomuraea (Doença Branca)");
		
		assertThat(pestDisease.getId()).isEqualTo(1l);
		assertThat(pestDisease.getUsualName()).isEqualTo("Lagartas com Nomuraea (Doença Branca)");
	}
	
	@Test
	public void setUsualName() {
		pestDisease.setUsualName("Lagartas com Nomuraea (Doença Branca)");

		assertThat(pestDisease.getUsualName()).isEqualTo("Lagartas com Nomuraea (Doença Branca)");

	}

	@Test
	public void mustNotAcceptANameLessThanFive() { 
		pestDisease.setUsualName("abab");
		Set<ConstraintViolation<PestDisease>> violations = validator.validate(pestDisease);
		
		assertThat(violations.toString()).contains("O nome deve ter entre 5 e 50 caracteres");
	}

	@Test
	public void mustNotAcceptANameGreaterThanFifty() { 
		pestDisease.setUsualName("ababababab ababababab ababababab ababababab abababa");
		Set<ConstraintViolation<PestDisease>> violations = validator.validate(pestDisease);
		
		assertThat(violations.toString()).contains("O nome deve ter entre 5 e 50 caracteres");
	}

	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		pestDisease.setUsualName(null);
	}
}
