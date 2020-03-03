package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class SupervisorTest {

	private Supervisor supervisor;
	private Validator validator;
	
	@Before
	public void init() {
		supervisor = Supervisor.builder().id(1L).name("").email("").region(null).build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}

	@Test
	public void createSupervisor() {
		// cenário
		Region region = createValidRegion();
		supervisor.setName("Eliani Aparecida Marson");
		supervisor.setEmail("grcornelio@emater.pr.gov.br");
		supervisor.setRegion(region);
		
		// verificação
		assertThat(supervisor.getId()).isEqualTo(1L);
		assertThat(supervisor.getName()).isEqualTo("Eliani Aparecida Marson");
		assertThat(supervisor.getEmail()).isEqualTo("grcornelio@emater.pr.gov.br");
		assertThat(supervisor.getRegion()).isEqualTo(region);
	}


	@Test(expected = NullPointerException.class)
	public void createInvalidSupervisor() throws Exception {
		Supervisor.builder().build();
	}
	
	@Test
	public void setName() {
		// execução
		supervisor.setName("Roberto Aparecido Corredato");

		// verificação
		assertThat(supervisor.getName()).isEqualTo("Roberto Aparecido Corredato");

	}

	@Test
	public void mustNotAcceptANameLessThanFive() {
		// execução
		supervisor.setName("ab");
		Set<ConstraintViolation<Supervisor>> violations = validator.validate(supervisor);
		
		// verificação
		assertThat(violations.toString()).contains("O nome deve ter entre 3 e 50 caracteres");
	}

	@Test
	public void mustNotAcceptANameGreaterThanFifty() {
		// execução
		supervisor.setName("ababababab ababababab ababababab ababababab ababababab");
		Set<ConstraintViolation<Supervisor>> violations = validator.validate(supervisor);
		
		// verificação
		assertThat(violations.toString()).contains("O nome deve ter entre 3 e 50 caracteres");
	}

	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		// execução
		supervisor.setName(null);
	}

	@Test
	public void setEmail() {
		// execução
		supervisor.setEmail("grcornelio@emater.pr.gov.br");

		//verificação
		assertThat(supervisor.getEmail()).isEqualTo("grcornelio@emater.pr.gov.br");
	}

	@Test
	public void mustNotAcceptANullEmail() {
		// execução
		supervisor.setEmail(null);
		Set<ConstraintViolation<Supervisor>> violations = validator.validate(supervisor);
		
		//verificação
		assertThat(violations.toString()).contains("Deve ser informado um e-mail válido");
	}

	@Test
	public void setInvalidEmail() {
		// execução
		supervisor.setEmail("email");
		Set<ConstraintViolation<Supervisor>> violations = validator.validate(supervisor);
		
		//verificação
		assertThat(violations.toString()).contains("Deve ser informado um e-mail válido");
	}

	@Test
	public void getRegionName() {
		// cenário
		Region region = createValidRegion();
		supervisor.setRegion(region);
		
		// execução
		String name = supervisor.getRegionName();

		//verificação
		assertThat(name).isEqualTo("Cornélio Procópio");
	}

	@Test
	public void getInvalidRegionName() {
		// execução
		String name = supervisor.getRegionName();

		//verificação
		assertThat(name).isNull();
	}

	@Test
	public void getRegionId() {
		// cenário
		Region region = createValidRegion();
		supervisor.setRegion(region);

		// execução
		Long id = supervisor.getRegionId();

		//verificação
		assertThat(id).isEqualTo(1L);
	}

	@Test
	public void getInvalidRegionId() {
		// execução
		Long id = supervisor.getRegionId();
		
		//verificação
		assertThat(id).isNull();
	}
	
	@Test
	public void getMacroRegionName() {
		// cenário
		Region region = createValidRegion();
		supervisor.setRegion(region);

		// execução
		String name = supervisor.getMacroRegionName();

		//verificação
		assertThat(name).isEqualTo("Macro Norte");
	}

	@Test
	public void getInvalidMacroRegionName() {
		// execução
		String name = supervisor.getMacroRegionName();

		//verificação
		assertThat(name).isNull();
	}

	@Test
	public void getMacroRegionId() {
		// cenário
		Region region = createValidRegion();
		supervisor.setRegion(region);

		// execução
		Long id = supervisor.getMacroRegionId();

		//verificação
		assertThat(id).isEqualTo(1L);
	}

	@Test
	public void getInvalidMacroRegionId() {
		// execução
		Long id = supervisor.getMacroRegionId();

		//verificação
		assertThat(id).isNull();
	}
	
	@Test
	public void getIdAsString() {
		// execução
		String id = supervisor.getIdAsString();
		
		//verificação
		assertThat(id).isEqualTo("1");
	}
	
	@Test
	public void getInvalidIdAsString() {
		// cenário
		supervisor.setId(null);
		
		// execução
		String id = supervisor.getIdAsString();
		
		//verificação
		assertThat(id).isNull();
	}

	@Test
	public void getCitiesInRegionNames() {
		// cenário
		Region region = createValidRegion();
		supervisor.setRegion(region);
		
		// execução
		String names = supervisor.getCitiesInRegionNames();
		
		//verificação
		assertThat(names).contains("Cornélio Procópio");
		assertThat(names).contains("Santa Mariana");
	}
	
	@Test
	public void getCityNamesInInvalidRegions() {
		// execução
		String names = supervisor.getCitiesInRegionNames();
		
		//verificação
		assertThat(names).isNull();
	}
	
	@Test
	public void getMacroRegion() {
		// cenário
		MacroRegion macroRegion = MacroRegion.builder().id(1L).name("Macro Norte").build();
		Region region = Region.builder().id(1L).name("Cornélio Procópio").macroRegion(macroRegion).cities(null).build();
		supervisor.setRegion(region);
		
		// execução
		Optional<MacroRegion> op = supervisor.getMacroRegion();
		
		//verificação
		assertThat(op).isEqualTo(Optional.of(macroRegion));
	
	}
	
	@Test
	public void getInvalidMacroRegion() {
		// cenário
		Region region = Region.builder().id(1L).name("Cornélio Procópio").macroRegion(null).cities(null).build();
		supervisor.setRegion(region);
		
		// execução
		Optional<MacroRegion> op = supervisor.getMacroRegion();
		
		//verificação
		assertThat(op).isEqualTo(Optional.empty());
	}
	

	private Region createValidRegion() {
		City city1 = City.builder().name("Cornélio Procópio").state(State.PR).build();
		City city2 = City.builder().name("Santa Mariana").state(State.PR).build();
		Set<City> cities = new HashSet<>();
		cities.add(city1);
		cities.add(city2);
		MacroRegion macroRegion = MacroRegion.builder().id(1L).name("Macro Norte").build();
		return Region.builder().id(1L).name("Cornélio Procópio").macroRegion(macroRegion).cities(cities).build();
	}

}
