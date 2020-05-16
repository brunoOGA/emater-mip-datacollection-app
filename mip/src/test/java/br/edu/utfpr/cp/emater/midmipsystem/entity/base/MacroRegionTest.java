package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class MacroRegionTest {

	private MacroRegion macroRegion;
	private Validator validator;
	
	@Before
	public void init() {
		macroRegion = MacroRegion.builder().name("").build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}

	@Test
	public void createMacroRegionTest() {
		// cenário
		macroRegion.setId(1l);
		macroRegion.setName("Macro Norte");

		// verificações
		assertThat(macroRegion.getId()).isEqualTo(1L);
		assertThat(macroRegion.getName()).isEqualTo("Macro Norte");
	}

	@Test
	public void setName() {
		// execução
		macroRegion.setName("Macro Norte");

		// verificações
		assertThat(macroRegion.getName()).isEqualTo("Macro Norte");

	}

	@Test
	public void mustNotAcceptANameLessThanThree() { 
		// execução
		macroRegion.setName("ab");
		Set<ConstraintViolation<MacroRegion>> violations = validator.validate(macroRegion);
		
		// verificação
		assertThat(violations.toString()).contains("O nome da macrorregião deve ter entre 3 e 50 caracteres");
	}

	@Test
	public void mustNotAcceptANameGreaterThanFifty() { 
		// execução
		macroRegion.setName("ababababab ababababab ababababab ababababab ababababab");
		Set<ConstraintViolation<MacroRegion>> violations = validator.validate(macroRegion);
		
		// verificação
		assertThat(violations.toString()).contains("O nome da macrorregião deve ter entre 3 e 50 caracteres");
	}


	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		// execução
		macroRegion.setName(null);
	}

}