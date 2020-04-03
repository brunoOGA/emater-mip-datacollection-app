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

import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import lombok.Getter;

public class PestTest {

	private Pest pest;
	private Validator validator;
	
	@Before
	public void init() {
		pest = Pest.builder().usualName("").build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void createPest() {
		pest.setId(1l);
		pest.setPestSize(PestSize.ADULTO);
		pest.setScientificName("Euschistus sp.");
		pest.setUsualName("Percevejo Marrom");
		
		assertThat(pest.getId()).isEqualTo(1l);
		assertThat(pest.getPestSize()).isEqualTo(PestSize.ADULTO);
		assertThat(pest.getScientificName()).isEqualTo("Euschistus sp.");
		assertThat(pest.getUsualName()).isEqualTo("Percevejo Marrom");
	}
	
	@Test
	public void setUsualName() {
		pest.setUsualName("PERCEVEJO MARROM");

		assertThat(pest.getUsualName()).isEqualTo("Percevejo Marrom");

	}

	@Test
	public void mustNotAcceptANameLessThanFive() { 
		pest.setUsualName("abab");
		Set<ConstraintViolation<Pest>> violations = validator.validate(pest);
		
		assertThat(violations.toString()).contains("O nome deve ter entre 5 e 50 caracteres");
	}

	@Test
	public void mustNotAcceptANameGreaterThanFifty() { 
		pest.setUsualName("ababababab ababababab ababababab ababababab abababa");
		Set<ConstraintViolation<Pest>> violations = validator.validate(pest);
		
		assertThat(violations.toString()).contains("O nome deve ter entre 5 e 50 caracteres");
	}

	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		pest.setUsualName(null);
	}
	
	@Test
	public void setScientificName() {
		pest.setScientificName("Euschistus sp.");

		assertThat(pest.getScientificName()).isEqualTo("Euschistus sp.");
	}
	
	@Test
	public void setPestSize() {
		pest.setPestSize(PestSize.ADULTO);
		
		assertThat(pest.getPestSizeName()).isEqualTo("Adulto");
	}
	
	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullPestSize() {
		pest.setPestSize(null);
	}
	
	@Test
	public void mustReturnADescriptionWithScientificNameAndPestSize() {
		pest.setScientificName("Euschistus sp.");
		pest.setPestSize(PestSize.ADULTO);
		pest.setUsualName("Percevejo Marrom");
		
		assertThat(pest.getDescription()).isEqualTo("Euschistus sp. (Adulto)");
	}
	
	@Test
	public void getDescriptionWithNullScientificName() {
		pest.setScientificName(null);
		pest.setPestSize(PestSize.ADULTO);
		pest.setUsualName("Percevejo Marrom");
		
		assertThat(pest.getDescription()).isEqualTo("Percevejo Marrom (Adulto)");
	}
	
	@Test
	public void getDescriptionWithEmptyScientificName() {
		pest.setScientificName("");
		pest.setPestSize(PestSize.ADULTO);
		pest.setUsualName("Percevejo Marrom");
		
		assertThat(pest.getDescription()).isEqualTo("Percevejo Marrom (Adulto)");
	}
}
