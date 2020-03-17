package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class CultivarDataTest {

	private CultivarData cultivarData;
	private Validator validator;
	
	@Before
	public void init() {
		cultivarData = CultivarData.builder().build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	@Test
	public void createCultivarTest() {
		// cenário
		cultivarData.setCultivarName("P95R51");
		cultivarData.setBt(true);
		cultivarData.setRustResistant(false);

		// verificações
		assertThat(cultivarData.getCultivarName()).isEqualTo("P95r51");
		assertThat(cultivarData.isBt()).isTrue();
		assertThat(cultivarData.isRustResistant()).isFalse();
	}
	
	@Test
	public void createCultivarWithoutValues() {
		assertThat(cultivarData.getCultivarName()).isNull();
		assertThat(cultivarData.isBt()).isFalse();
		assertThat(cultivarData.isRustResistant()).isFalse();
	}
	
	@Test
	public void setName() {
		// execução
		cultivarData.setCultivarName("P95R51");

		// verificações
		assertThat(cultivarData.getCultivarName()).isEqualTo("P95r51");

	}

	@Test
	public void mustNotAcceptANameLessThanThree() { 
		// execução
		cultivarData.setCultivarName("ab");
		Set<ConstraintViolation<CultivarData>> violations = validator.validate(cultivarData);
		
		// verificação
		assertThat(violations.toString()).contains("A identificação da cultivar deve ter entre 3 e 50 caracteres");
	}

	@Test
	public void mustNotAcceptANameGreaterThanFifty() { 
		// execução
		cultivarData.setCultivarName("ababababab ababababab ababababab ababababab ababababab");
		Set<ConstraintViolation<CultivarData>> violations = validator.validate(cultivarData);
		
		// verificação
		assertThat(violations.toString()).contains("A identificação da cultivar deve ter entre 3 e 50 caracteres");
	}
	
	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		// execução
		cultivarData.setCultivarName(null);
	}
	
}
