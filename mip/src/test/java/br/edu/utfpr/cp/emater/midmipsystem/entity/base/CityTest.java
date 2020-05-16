package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class CityTest {

	private City city;
//	private Validator validator;
	
	@Before
	public void init() {
		city = City.builder().name("").build();
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		validator = factory.getValidator();
	}

	@Test
	public void createCityTest() {
		// cenário
		city.setId(1l);
		city.setName("Cornélio Procópio");
		city.setState(State.PR);

		// verificações
		assertThat(city.getId()).isEqualTo(1L);
		assertThat(city.getName()).isEqualTo("Cornélio Procópio");
		assertThat(city.getState()).isEqualTo(State.PR);
	}

	@Test
	public void setName() {
		// execução
		city.setName("Gilson Dariva");

		// verificações
		assertThat(city.getName()).isEqualTo("Gilson Dariva");

	}

//	@Test
//	public void mustNotAcceptANameLessThanThree() { 
//		// execução
//		city.setName("ab");
//		Set<ConstraintViolation<City>> violations = validator.validate(city);
//		
//		// verificação
//		assertThat(violations.toString()).contains("O nome da cidade deve ter entre 3 e 30 caracteres");
//	}
//
//	@Test
//	public void mustNotAcceptANameGreaterThanThirty() { 
//		// execução
//		city.setName("ababababab ababababab ababababab ababababab ababababab");
//		Set<ConstraintViolation<City>> violations = validator.validate(city);
//		
//		// verificação
//		assertThat(violations.toString()).contains("O nome da cidade deve ter entre 3 e 30 caracteres");
//	}
//
//
	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		// execução
		city.setName(null);
	}
	
	@Test
	public void getIdAsString() {
		// execução
		city.setId(1l);

		// verificações
		assertThat(city.getIdAsString()).isEqualTo("1");

	}
	
	@Test
	public void toStringTest() {
		// execução
		city.setName("Cornélio Procópio");
		city.setState(State.PR);

		// verificações
		assertThat(city.toString()).isEqualTo("Cornélio Procópio (PR)");

	}
	
}