package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class FieldTest {

	private Field field;
//	private Validator validator;
	
	@Before
	public void init() {
		field = Field.builder().name("").build();
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
	}

	@Test
	public void createFieldTest() {
		// cenário
		City city = City.builder().name("Itapejara D´Oeste").state((State.PR)).build();
		Farmer farmer = Farmer.builder().id(1l).name("Gilson Dariva").build();
		Supervisor supervisor = Supervisor.builder().id(1l).email("larimaroli@emater.pr.gov.br").name("Lari Maroli").region(createValidRegion()).build();
		field.setId(1l);
		field.setName("Trevo");
		field.setLocation("25°58'35.63\"S 52°48'45.68\"W");
		field.setCity(city);
		field.setFarmer(farmer);
		field.addSupervisor(supervisor);

		// verificações
		assertThat(field.getId()).isEqualTo(1L);
		assertThat(field.getName()).isEqualTo("Trevo");
		assertThat(field.getLocation()).isEqualTo("25°58'35.63\"S 52°48'45.68\"W");
		assertThat(field.getCity()).isEqualTo(city);
		assertThat(field.getFarmer()).isEqualTo(farmer);
		assertThat(field.getSupervisorNames().toString()).isEqualTo("[Lari Maroli]");
	}


	@Test
	public void setName() {
		// execução
		field.setName("Trevo");

		// verificações
		assertThat(field.getName()).isEqualTo("Trevo");
	}

//	@Test
//	public void mustNotAcceptANameLessThanFive() { 
//		// execução
//		field.setName("ab");
//		Set<ConstraintViolation<Field>> violations = validator.validate(field);
//		
//		// verificação
//		assertThat(violations.toString()).contains("O nome do campo deve ter entre 5 e 50 caracteres");
//	}
//
//	@Test
//	public void mustNotAcceptANameGreaterThanFifty() { 
//		// execução
//		field.setName("ababababab ababababab ababababab ababababab ababababab");
//		Set<ConstraintViolation<Field>> violations = validator.validate(field);
//		
//		// verificação
//		assertThat(violations.toString()).contains("O nome do campo deve ter entre 5 e 50 caracteres");
//	}
	
	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		// execução
		field.setName(null);
	}

	@Test
	public void addSupervisor() {
		Supervisor supervisor1 = Supervisor.builder().id(1l).email("larimaroli@emater.pr.gov.br").name("Lari Maroli").region(createValidRegion()).build();
	
		field.addSupervisor(supervisor1);
		
		assertThat(field.getSupervisorNames().toString()).isEqualTo("[Lari Maroli]");
	}

	@Test
	public void removeSupervisor() {
		Supervisor supervisor1 = Supervisor.builder().id(1l).email("larimaroli@emater.pr.gov.br").name("Lari Maroli").region(createValidRegion()).build();
		Supervisor supervisor2 = Supervisor.builder().id(1l).email("grcornelio@emater.pr.gov.br").name("Eliani Aparecida Marson").region(createValidRegion()).build();
		field.addSupervisor(supervisor1);
		field.addSupervisor(supervisor2);
		
		boolean var = field.removeSupervisor(supervisor2);
		
		assertThat(field.getSupervisorNames().toString()).isEqualTo("[Lari Maroli]");
		assertThat(var).isTrue();
	}
	
	@Test
	public void removeInvalidSupervisor() {
		Supervisor supervisor1 = Supervisor.builder().id(1l).email("larimaroli@emater.pr.gov.br").name("Lari Maroli").region(createValidRegion()).build();
		
		boolean var = field.removeSupervisor(supervisor1);
		
		assertThat(var).isFalse();
	}
	

	@Test
	public void getCityName() {
		City city = City.builder().name("Itapejara D´Oeste").state((State.PR)).build();
		field.setCity(city);
		
		assertThat(field.getCityName()).isEqualTo("Itapejara D´oeste");
	}
	
	@Test
	public void getIvalidCityName() {
		field.setCity(null);
		
		assertThat(field.getCityName()).isNull();
	}
	
	@Test
	public void getCityId() {
		City city = City.builder().name("Itapejara D´Oeste").state((State.PR)).build();
		city.setId(1l);
		field.setCity(city);
		
		assertThat(field.getCityId()).isEqualTo(1);
	}
	
	@Test
	public void getInvalidCityId() {
		field.setCity(null);
		
		assertThat(field.getCityId()).isZero();
	}
	
	@Test
	public void getStateName() {
		City city = City.builder().name("Itapejara D´Oeste").state((State.PR)).build();
		field.setCity(city);
		
		assertThat(field.getStateName()).isEqualTo("Paraná");
	}
	
	@Test
	public void getInvalidStateName() {
		field.setCity(null);
		
		assertThat(field.getStateName()).isNull();
	}
	
	@Test
	public void getFarmerName() {
		Farmer farmer = Farmer.builder().id(1l).name("Gilson Dariva").build();
		field.setFarmer(farmer);
		
		assertThat(field.getFarmerName()).isEqualTo("Gilson Dariva");
	}
	
	@Test
	public void getInvalidFarmerName() {
		field.setFarmer(null);
		
		assertThat(field.getFarmerName()).isNull();
	}
	
	@Test
	public void getFarmerId() {
		Farmer farmer = Farmer.builder().id(1l).name("Gilson Dariva").build();
		field.setFarmer(farmer);
		
		assertThat(field.getFarmerId()).isEqualTo(1);
	}
	
	@Test
	public void getInvalidFarmerId() {
		field.setFarmer(null);
		
		assertThat(field.getFarmerId()).isZero();
	}
	
	@Test
	public void getSupervisorNames() {
		Supervisor supervisor1 = Supervisor.builder().id(1l).email("larimaroli@emater.pr.gov.br").name("Lari Maroli").region(createValidRegion()).build();
		Supervisor supervisor2 = Supervisor.builder().id(1l).email("grcornelio@emater.pr.gov.br").name("Eliani Aparecida Marson").region(createValidRegion()).build();
		
		field.addSupervisor(supervisor1);
		field.addSupervisor(supervisor2);
		
		assertThat(field.getSupervisorNames().toString()).isEqualTo("[Lari Maroli, Eliani Aparecida Marson]");
	}
	
	@Test
	public void getInvalidSupervisorNames() {
		assertThat(field.getSupervisorNames()).isNull();
	}
	
	@Test
	public void getMacroRegion() {
		Supervisor supervisor = Supervisor.builder().id(1l).email("larimaroli@emater.pr.gov.br").name("Lari Maroli").region(createValidRegion()).build();
		
		field.addSupervisor(supervisor);
		
		assertThat(field.getMacroRegion().get().getName()).isEqualTo("Macro Norte");
	}
	
//	@Test
//	public void getInvalidMacroRegion() {
//		assertThat(field.getMacroRegion()).isEmpty();
//	}
	
	@Test
	public void getRegion() {
		Supervisor supervisor = Supervisor.builder().id(1l).email("larimaroli@emater.pr.gov.br").name("Lari Maroli").region(createValidRegion()).build();
		
		field.addSupervisor(supervisor);
		
		assertThat(field.getRegion().get().getName()).isEqualTo("Pato Branco");
	}
	
//	@Test
//	public void getInvalidRegion() {
//		assertThat(field.getRegion()).isEmpty();
//	}
	
	
	
	private Region createValidRegion() {
		City city1 = City.builder().name("Itapejara D´Oeste").state(State.PR).build();
		City city2 = City.builder().name("Pato Branco").state(State.PR).build();
		Set<City> cities = new HashSet<>();
		cities.add(city1);
		cities.add(city2);
		MacroRegion macroRegion = MacroRegion.builder().id(1L).name("Macro Norte").build();
		return Region.builder().id(1L).name("Pato Branco").macroRegion(macroRegion).cities(cities).build();
	}

}