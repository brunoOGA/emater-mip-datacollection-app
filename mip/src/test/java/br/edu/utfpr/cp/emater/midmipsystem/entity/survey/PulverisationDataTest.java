package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class PulverisationDataTest {

	private PulverisationData pulverisationData;
	private Validator validator;
	
	@Before
	public void init() {
		pulverisationData = PulverisationData.builder().build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	@Test
	public void createPulverisationDataTest() {
		pulverisationData.setApplicationCostCurrency(49);
		pulverisationData.setSoyaPrice(70);
		
		assertThat(pulverisationData.getApplicationCostCurrency()).isEqualTo(49);
		assertThat(pulverisationData.getSoyaPrice()).isEqualTo(70);
	}
	
	@Test
	public void createPulverisationDataWithoutValues() {
		assertThat(pulverisationData.getApplicationCostCurrency()).isEqualTo(0);
		assertThat(pulverisationData.getSoyaPrice()).isEqualTo(0);
	}
	
	@Test
	public void getApplicationCostQtyInvalidApplicationCostCurrency() {
		pulverisationData.setSoyaPrice(70);
		
		assertThat(pulverisationData.getApplicationCostQty()).isEqualTo(0);
	}
	
	@Test
	public void getApplicationCostQtyInvalidSoyaPrice() {
		pulverisationData.setApplicationCostCurrency(49);
		
		assertThat(pulverisationData.getApplicationCostQty()).isEqualTo(0);
	}
	
	@Test
	public void getApplicationCostQty() {
		pulverisationData.setApplicationCostCurrency(49);
		pulverisationData.setSoyaPrice(70);
		
		assertThat(pulverisationData.getApplicationCostQty()).isEqualTo(0.7);
	}
	
	@Test
	public void setInvalidApplicationCostCurrency() {
		pulverisationData.setApplicationCostCurrency(-1);
		
		Set<ConstraintViolation<PulverisationData>> violations = validator.validate(pulverisationData);
		
		assertThat(violations.toString()).contains("O custo da aplicação precisa ser um valor maior que zero.");
	}

	@Test
	public void setInvalidSoyaPrice() {
		pulverisationData.setSoyaPrice(-1);
		
		Set<ConstraintViolation<PulverisationData>> violations = validator.validate(pulverisationData);
		
		assertThat(violations.toString()).contains("O custo da aplicação precisa ser um valor maior que zero.");
	}
}
