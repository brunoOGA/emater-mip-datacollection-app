package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class ProductTest {

	private Product product;
	private Validator validator;
	
	@Before
	public void init() {
		product = Product.builder().name("").build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void createProduct() {
		product.setId(1l);
		product.setName("2-4D");
		product.setUseClass(UseClass.HERBICIDA);
		product.setUnit(ProductUnit.L);
		product.setConcentrationActiveIngredient("806");
		product.setRegisterNumber(3009l);
		product.setCompany("Nortox");
		product.setToxiClass(ToxiClass.I);
		product.setActiveIngredient("2,4-D");
		
		assertThat(product.getId()).isEqualTo(1l);
		assertThat(product.getName()).isEqualTo("2-4d");
		assertThat(product.getUseClass()).isEqualTo(UseClass.HERBICIDA);
		assertThat(product.getUnit()).isEqualTo(ProductUnit.L);
		assertThat(product.getConcentrationActiveIngredient()).isEqualTo("806");
		assertThat(product.getRegisterNumber()).isEqualTo(3009l);
		assertThat(product.getCompany()).isEqualTo("Nortox");
		assertThat(product.getToxiClass()).isEqualTo(ToxiClass.I);
		assertThat(product.getActiveIngredient()).isEqualTo("2,4-D");
	}
	
	
	@Test
	public void setName() {
		// execução
		product.setName("2-4D");

		// verificações
		assertThat(product.getName()).isEqualTo("2-4d");

	}

	@Test
	public void mustNotAcceptANameLessThanThree() { 
		// execução
		product.setName("ab");
		Set<ConstraintViolation<Product>> violations = validator.validate(product);
		
		// verificação
		assertThat(violations.toString()).contains("O nome deve ter entre 3 e 50 caracteres");
	}

	@Test
	public void mustNotAcceptANameGreaterThanFifty() { 
		// execução
		product.setName("ababababab ababababab ababababab ababababab ababababab");
		Set<ConstraintViolation<Product>> violations = validator.validate(product);
		
		// verificação
		assertThat(violations.toString()).contains("O nome deve ter entre 3 e 50 caracteres");
	}

	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		product.setName(null);
	}
	
	@Test
	public void getUnitDescription() {
		product.setUnit(ProductUnit.L);
		
		assertThat(product.getUnitDescription()).isEqualTo("L");
	}
	
	@Test
	public void getNullUnitDescription() {
		product.setUnit(null);
		
		assertThat(product.getUnitDescription()).isNull();
	}
	
	@Test
	public void getUseClassDescription() {
		product.setUseClass(UseClass.HERBICIDA);
		
		assertThat(product.getUseClassDescription()).isEqualTo("Herbicida");
	}
	
	@Test
	public void getNullUseClassDescription() {
		product.setUseClass(null);
		
		assertThat(product.getUseClassDescription()).isNull();
	}
	
	@Test
	public void getToxiClassDescription() {
		product.setToxiClass(ToxiClass.I);
		
		assertThat(product.getToxiClassDescription()).isEqualTo("I");
	}
	
	@Test
	public void getNullToxiClassDescription() {
		product.setToxiClass(null);
		
		assertThat(product.getToxiClassDescription()).isEmpty();
	}
}
