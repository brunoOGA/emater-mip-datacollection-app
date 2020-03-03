package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import javax.validation.*;

import org.junit.Before;
import org.junit.Test;

public class RegionTest {

	private Region region;
	private Validator validator;
	
	@Before
	public void init() {
		region = Region.builder().id(1L).name("").macroRegion(null).cities(null).build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}

	@Test
	public void createRegionTest() {
		// cenário
		MacroRegion macroRegion = macroRegion();
		Set<City> cities = cities();
		region.setCities(cities);
		region.setMacroRegion(macroRegion);
		region.setName("Cornélio Procópio");

		// verificações
		assertThat(region.getId()).isEqualTo(1L);
		assertThat(region.getName()).isEqualTo("Cornélio Procópio");
		assertThat(region.getMacroRegion()).isEqualTo(macroRegion);
		assertThat(region.getCities()).isEqualTo(cities);
	}

	@Test(expected = RuntimeException.class)
	public void createInvalidRegionTest() throws Exception {
		Region.builder().build();
	}

	@Test
	public void setName() {
		// execução
		region.setName("Londrina");

		// verificações
		assertThat(region.getName()).isEqualTo("Londrina");

	}

	@Test
	public void mustNotAcceptANameLessThanFive() { 
		// execução
		region.setName("ab");
		Set<ConstraintViolation<Region>> violations = validator.validate(region);
		
		// verificação
		assertThat(violations.toString()).contains("O nome da região deve ter entre 5 e 50 caracteres");
	}

	@Test
	public void mustNotAcceptANameGreaterThanFifty() { 
		// execução
		region.setName("ababababab ababababab ababababab ababababab ababababab");
		Set<ConstraintViolation<Region>> violations = validator.validate(region);
		
		// verificação
		assertThat(violations.toString()).contains("O nome da região deve ter entre 5 e 50 caracteres");
	}

	@Test(expected = NullPointerException.class)
	public void mustNotAcceptANullName() {
		// execução
		region.setName(null);
	}

	@Test
	public void addACityWhereThereIsNoCityContainer() {
		// cenário
		City city = City.builder().name("Andirá").state(State.PR).build();

		// execução
		boolean var = region.addCity(city);

		// verificação
		assertThat(var).isTrue();

	}

	@Test
	public void addACityInACityContainer() {
		// cenário
		Set<City> cities = cities();
		region.setCities(cities);
		City city = City.builder().name("Andirá").state(State.PR).build();

		// execução
		boolean var = region.addCity(city);

		// verificação
		assertThat(var).isTrue();
		assertThat(region.getCities().size()).isEqualTo(3);

	}

	@Test
	public void removeACityInACityContainer() {
		// cenário
		Set<City> cities = cities();
		region.setCities(cities);
		City city = City.builder().name("Andirá").state(State.PR).build();

		// execução
		region.addCity(city);
		boolean var = region.removeCity(city);

		// verificação
		assertThat(var).isTrue();
		assertThat(region.getCities().size()).isEqualTo(2);

	}

	@Test
	public void removeACityThatIsNotInTheCitiesContainer() {
		// cenário
		Set<City> cities = cities();
		region.setCities(cities);
		City city = City.builder().name("Andirá").state(State.PR).build();

		// execução
		boolean var = region.removeCity(city);

		// verificação
		assertThat(var).isFalse();

	}

	@Test
	public void removeACityWhereThereIsNoCityContainer() {
		// cenário
		City city = City.builder().name("Andirá").state(State.PR).build();

		// execução
		boolean var = region.removeCity(city);

		// verificação
		assertThat(var).isFalse();

	}

	@Test
	public void getMacroRegionName() {
		// cenário
		MacroRegion macroRegion = macroRegion();
		region.setMacroRegion(macroRegion);

		// execução
		String macroRegionName = region.getMacroRegionName();

		// verificação
		assertThat(macroRegionName).isEqualTo("Macro Norte");
	}
	
	@Test
	public void getInvalidMacroRegionName() {
		// execução
		String macroRegionName = region.getMacroRegionName();

		// verificação
		assertThat(macroRegionName).isNull();
	}

	@Test
	public void getMacroRegionId() {
		// cenário
		MacroRegion macroRegion = macroRegion();
		region.setMacroRegion(macroRegion);

		// execução
		Long id = region.getMacroRegionId();

		// verificação
		assertThat(id).isEqualTo(1L);
	}

	@Test
	public void getInvalidMacroRegionId() {
		// execução
		Long id = region.getMacroRegionId();

		// verificação
		assertThat(id).isNull();
	}

	@Test
	public void getCityNames() {
		// cenário
		Set<City> cities = cities();
		region.setCities(cities);

		// execução
		Set<String> cityNames = region.getCityNames();

		// verificação
		assertThat(cityNames.contains("Cornélio Procópio")).isTrue();
		assertThat(cityNames.contains("Santa Mariana")).isTrue();
	}

	@Test
	public void getInvalidCityNames() {
		// execução
		Set<String> cityNames = region.getCityNames();

		// verificação
		assertThat(cityNames).isNull();
	}

	private MacroRegion macroRegion() {
		return MacroRegion.builder().id(1L).name("Macro Norte").build();

	}

	private Set<City> cities() {
		City city1 = City.builder().name("Cornélio Procópio").state(State.PR).build();
		City city2 = City.builder().name("Santa Mariana").state(State.PR).build();
		Set<City> cities = new HashSet<>();
		cities.add(city1);
		cities.add(city2);
		return cities;
	}

}
