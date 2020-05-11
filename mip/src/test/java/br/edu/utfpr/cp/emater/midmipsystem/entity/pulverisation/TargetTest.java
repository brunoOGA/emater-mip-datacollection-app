package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class TargetTest {

	private Target target;
	private Validator validator;
	
	@Before
	public void init() {
		target = Target.builder().build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void createProduct() {
		target.setId(1l);
		target.description("Herbicida seletivo para aplicação no controle de plantas.");
		target.setUseClass(UseClass.HERBICIDA);
		
		assertThat(target.getId()).isEqualTo(1l);
		assertThat(target.getDescription()).isEqualTo("Herbicida Seletivo Para Aplicação No Controle De Plantas.");
		assertThat(target.getUseClass()).isEqualTo(UseClass.HERBICIDA);
	}
	
	@Test
	public void setDescription() {
		// execução
		target.description("Herbicida seletivo para aplicação no controle de plantas.");

		// verificações
		assertThat(target.getDescription()).isEqualTo("Herbicida Seletivo Para Aplicação No Controle De Plantas.");

	} 

	@Test
	public void mustNotAcceptADescriptionLessThanThree() { 
		// execução
		target.description("ab");
		Set<ConstraintViolation<Target>> violations = validator.validate(target);

		// verificação
		assertThat(violations.toString()).contains("A descrição deve ter entre 3 e 80 caracteres");
	}

	@Test
	public void mustNotAcceptADescriptionGreaterThanEighty() { 
		// execução
		target.description("ababababab ababababab ababababab ababababab ababababab ababababab ababababab ababababab ababababab ababababab");
		Set<ConstraintViolation<Target>> violations = validator.validate(target);

		// verificação
		assertThat(violations.toString()).contains("A descrição deve ter entre 3 e 80 caracteres");
	}

	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullDescription() {
		// execução
		target.description(null);
	}
	
	@Test
	public void isInseticidaUseClass() {
		target.setUseClass(UseClass.INSETICIDA);
		
		assertThat(target.isInseticidaUseClass()).isTrue();
	}
	
	@Test
	public void isNotInseticidaUseClass() {
		target.setUseClass(UseClass.ESTIMULANTE);
		
		assertThat(target.isInseticidaUseClass()).isFalse();
	}
	
	@Test
	public void isInseticidaBiologicoUseClass() {
		target.setUseClass(UseClass.INCETICIDA_BIOLOGICO);
		
		assertThat(target.isInseticidaBiologicoUseClass()).isTrue();
	}
	
	@Test
	public void isNotInseticidaBiologicoUseClass() {
		target.setUseClass(UseClass.ESTIMULANTE);
		
		assertThat(target.isInseticidaBiologicoUseClass()).isFalse();
	}
	
	@Test
	public void isFungicidaUseClass() {
		target.setUseClass(UseClass.FUNGICIDA);
		
		assertThat(target.isFungicidaUseClass()).isTrue();
	}
	
	@Test
	public void isNotFungicidaUseClass() {
		target.setUseClass(UseClass.ESTIMULANTE);
		
		assertThat(target.isFungicidaUseClass()).isFalse();
	}
	
	@Test
	public void isAcaricidaUseClass() {
		target.setUseClass(UseClass.ACARICIDA);
		
		assertThat(target.isAcaricidaUseClass()).isTrue();
	}
	
	@Test
	public void isNotAcaricidaUseClass() {
		target.setUseClass(UseClass.ESTIMULANTE);
		
		assertThat(target.isAcaricidaUseClass()).isFalse();
	}
	
}
