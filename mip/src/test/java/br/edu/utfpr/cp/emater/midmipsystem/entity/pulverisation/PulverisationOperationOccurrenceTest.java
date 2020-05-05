package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class PulverisationOperationOccurrenceTest {

	private PulverisationOperationOccurrence pulverisationOperationOccurrence;
	private Validator validator;
	
	@Before
	public void init() {
		pulverisationOperationOccurrence = PulverisationOperationOccurrence.builder().build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void createPulverisationOperationOccurrence() {
		Product product = createProduct();
		Target target = createTarget();
		pulverisationOperationOccurrence.setProduct(product);
		pulverisationOperationOccurrence.setProductPrice(13.5);
		pulverisationOperationOccurrence.setTarget(target);
		pulverisationOperationOccurrence.setDose(1.2);
		
		assertThat(pulverisationOperationOccurrence.getProduct()).isEqualTo(product);
		assertThat(pulverisationOperationOccurrence.getProductPrice()).isEqualTo(13.5);
		assertThat(pulverisationOperationOccurrence.getTarget()).isEqualTo(target);
		assertThat(pulverisationOperationOccurrence.getDose()).isEqualTo(1.2);
	}
	
	@Test 
	public void setProduct() {
		Product product = createProduct();
		pulverisationOperationOccurrence.setProduct(product);
		
		assertThat(pulverisationOperationOccurrence.getProduct()).isEqualTo(product);
	}
	
	@Test
	public void setNullProduct() {
		pulverisationOperationOccurrence.setProduct(null);
		Set<ConstraintViolation<PulverisationOperationOccurrence>> violations = validator.validate(pulverisationOperationOccurrence);

		assertThat(violations.toString()).contains("Um produto deve ser informado");
	}
	
	@Test 
	public void setProductPrice() {
		pulverisationOperationOccurrence.setProductPrice(13.5);
		
		assertThat(pulverisationOperationOccurrence.getProductPrice()).isEqualTo(13.5);
	}
	
	@Test
	public void setNullProductPrice() {
		Set<ConstraintViolation<PulverisationOperationOccurrence>> violations = validator.validate(pulverisationOperationOccurrence);

		assertThat(violations.toString()).contains("O preço do produto deve ser informado");
	}
	
	@Test 
	public void setDose() {
		pulverisationOperationOccurrence.setDose(1.2);
		
		assertThat(pulverisationOperationOccurrence.getDose()).isEqualTo(1.2);
	}
	
	@Test
	public void setNullDose() {
		Set<ConstraintViolation<PulverisationOperationOccurrence>> violations = validator.validate(pulverisationOperationOccurrence);

		assertThat(violations.toString()).contains("A dosagem do produto deve ser informada");
	}
	
	@Test
	public void getProductCostCurrency() {
		pulverisationOperationOccurrence.setProductPrice(13.5);
		pulverisationOperationOccurrence.setDose(1.2);
		
		assertThat(pulverisationOperationOccurrence.getProductCostCurrency()).isEqualTo(16.2);
	}
	
	@Test
	public void getProductCostCurrencyWithProductPriceEqualToZero() {
		pulverisationOperationOccurrence.setProductPrice(0);
		pulverisationOperationOccurrence.setDose(1.2);
		
		assertThat(pulverisationOperationOccurrence.getProductCostCurrency()).isEqualTo(0);
	}
	
	@Test
	public void getProductCostCurrencyWithDoseEqualToZero() {
		pulverisationOperationOccurrence.setProductPrice(13.5);
		pulverisationOperationOccurrence.setDose(0);
		
		assertThat(pulverisationOperationOccurrence.getProductCostCurrency()).isEqualTo(0);
	}
	
	@Test
	public void getProductCostQty() {
		assertThat(pulverisationOperationOccurrence.getProductCostQty()).isZero();
	}
	
	@Test 
	public void getTargetCategoryDescription() {
		pulverisationOperationOccurrence.setTarget(createTarget());
		
		assertThat(pulverisationOperationOccurrence.getTargetCategoryDescription()).isEqualTo("Herbicida");
	}
	
	@Test 
	public void getNullTargetCategoryDescription() {
		pulverisationOperationOccurrence.setTarget(null);
		
		assertThat(pulverisationOperationOccurrence.getTargetCategoryDescription()).isNull();
	}
	
	@Test 
	public void getTargetDescription() {
		pulverisationOperationOccurrence.setTarget(createTarget());
		
		assertThat(pulverisationOperationOccurrence.getTargetDescription()).isEqualTo("Herbicida seletivo para aplicação no controle de plantas.");
	}
	
	@Test 
	public void getNullTargetDescription() {
		pulverisationOperationOccurrence.setTarget(null);
		
		assertThat(pulverisationOperationOccurrence.getTargetDescription()).isNull();
	}
	
	@Test
	public void getProductFormattedName() {
		pulverisationOperationOccurrence.setProduct(createProduct());
		pulverisationOperationOccurrence.setDose(1.2);
		System.out.println(pulverisationOperationOccurrence.getProductFormattedName());
		assertThat(pulverisationOperationOccurrence.getProductFormattedName()).isEqualTo("2-4d - 1.20 (L)");
	}
	
	@Test
	public void getNullProductFormattedName() {
		pulverisationOperationOccurrence.setProduct(null);
		
		assertThat(pulverisationOperationOccurrence.getProductFormattedName()).isNull();
	}
	
	@Test
	public void isTargetMIPisInseticidaUseClass() {
		Target target = Target.builder().useClass(UseClass.INSETICIDA).build();
		pulverisationOperationOccurrence.setTarget(target);
		
		assertThat(pulverisationOperationOccurrence.isTargetMIP()).isTrue();
	}
	
	@Test
	public void isTargetMIPisInseticidaBiologicoUseClass() {
		Target target = Target.builder().useClass(UseClass.INCETICIDA_BIOLOGICO).build();
		pulverisationOperationOccurrence.setTarget(target);
		
		assertThat(pulverisationOperationOccurrence.isTargetMIP()).isTrue();
	}
	
	@Test
	public void isTargetMIPisAcaricidaUseClass() {
		Target target = Target.builder().useClass(UseClass.ACARICIDA).build();
		pulverisationOperationOccurrence.setTarget(target);
		
		assertThat(pulverisationOperationOccurrence.isTargetMIP()).isTrue();
	}
	
	@Test
	public void isNotTargetMip() {
		Target target = Target.builder().useClass(UseClass.FUNGICIDA).build();
		pulverisationOperationOccurrence.setTarget(target);
		
		assertThat(pulverisationOperationOccurrence.isTargetMIP()).isFalse();
	}
	
	@Test
	public void isInseticidaBiologico() {
		Target target = Target.builder().useClass(UseClass.INCETICIDA_BIOLOGICO).build();
		pulverisationOperationOccurrence.setTarget(target);
		
		assertThat(pulverisationOperationOccurrence.isInseticidaBiologico()).isTrue();
	}
	
	@Test
	public void isNotInseticidaBiologico() {
		Target target = Target.builder().useClass(UseClass.FUNGICIDA).build();
		pulverisationOperationOccurrence.setTarget(target);
		
		assertThat(pulverisationOperationOccurrence.isInseticidaBiologico()).isFalse();
	}
	
	@Test
	public void isTargetMID() {
		Target target = Target.builder().useClass(UseClass.FUNGICIDA).build();
		pulverisationOperationOccurrence.setTarget(target);
		
		assertThat(pulverisationOperationOccurrence.isTargetMID()).isTrue();
	}
	
	@Test
	public void isNotTargetMID() {
		Target target = Target.builder().useClass(UseClass.INCETICIDA_BIOLOGICO).build();
		pulverisationOperationOccurrence.setTarget(target);
		
		assertThat(pulverisationOperationOccurrence.isTargetMID()).isFalse();
	}
	
	public Product createProduct() {
		Product product = Product.builder().id(1l).name("2-4D")
			.useClass(UseClass.HERBICIDA).unit(ProductUnit.L)
			.concentrationActiveIngredient("806")
			.registerNumber(3009l)
			.company("Nortox")
			.toxiClass(ToxiClass.I)
			.activeIngredient("2,4-D").build();
		
		return product;
	}
	
	public Target createTarget() {
		Target target = Target.builder()
			.id(1l)
			.description("Herbicida seletivo para aplicação no controle de plantas.")
			.useClass(UseClass.HERBICIDA).build();
		
		return target;
	}
	

}
