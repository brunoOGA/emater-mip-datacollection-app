package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.activation.FileDataSource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;

public class SurveyTest {

	private Survey survey;
	private Field field;
	private Harvest harvest;
	private Validator validator;

	@Before
	public void init() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		City city = City.builder().name("Itapejara D´Oeste").state(State.PR).build();
		city.setId(1l);
		Set<City> cities = new HashSet<>();
		cities.add(city);
		Farmer farmer = Farmer.builder().id(1l).name("Gilson Dariva").build();
		MacroRegion macroRegion = MacroRegion.builder().id(1l).name("Pato Branco").build();
		Region region = Region.builder().id(1l).name("Pato Branco").cities(cities).macroRegion(macroRegion).build();
		Supervisor supervisor = Supervisor.builder().id(1l).name("Luiz Francisco Lovato")
				.email("grpatobranco@emater.pr.gov.br").region(region).build();
		field = Field.builder().id(1l).city(city).farmer(farmer).location("1").name("Trevo").build();
		field.addSupervisor(supervisor);
		harvest = Harvest.builder().id(1l).begin(new Date(117, 8, 26)).end(new Date(118, 1, 15)).name("P95R51").build();
		survey = Survey.builder().build();
	}

	@Test
	public void createSurveyTest() {
		survey = Survey.builder().id(1l).cultivarName("P95R51").sporeCollectorPresent(true)
				.collectorInstallationDate(new Date(117, 10, 1)).rustResistant(false).bt(false)
				.sowedDate(new Date(117, 8, 26)).emergenceDate(new Date(117, 9, 10)).harvestDate(new Date(117, 1, 15))
				.longitude("25°58'35.63\"").latitude("52°48'45.68\"").productivityField(4636d).productivityFarmer(4215d)
				.separatedWeight(true).totalArea(7.26).totalPlantedArea(242).plantPerMeter(15).soyaPrice(70.00)
				.applicationCostCurrency(49).harvest(harvest).field(field).build();

		assertThat(survey.getApplicationCostCurrency()).isEqualTo(49);
		assertThat(survey.getCultivarName()).isEqualTo("P95R51");
		assertThat(survey.getFarmerString()).isEqualTo("Gilson Dariva");
		assertThat(survey.getFieldCityName()).isEqualTo("Itapejara D´oeste");
		assertThat(survey.getFieldLocation()).isEqualTo("1");
		assertThat(survey.getFieldName()).isEqualTo("Trevo");
		assertThat(survey.getHarvestName()).isEqualTo("P95r51");
		assertThat(survey.getLatitude()).isEqualTo("52°48'45.68\"");
		assertThat(survey.getLongitude()).isEqualTo("25°58'35.63\"");
		assertThat(survey.getPlantPerMeter()).isEqualTo(15);
		assertThat(survey.getProductivityFarmer()).isEqualTo(4215);
		assertThat(survey.getProductivityField()).isEqualTo(4636);
		assertThat(survey.getSoyaPrice()).isEqualTo(70);
		assertThat(survey.getTotalArea()).isEqualTo(7.26);
		assertThat(survey.getTotalPlantedArea()).isEqualTo(242);
		assertThat(survey.isBt()).isFalse();
		assertThat(survey.isRustResistant()).isFalse();
		assertThat(survey.isSeparatedWeight()).isTrue();
		assertThat(survey.isSporeCollectorPresent()).isTrue();
		assertThat(survey.getCity()).toString().contains("Itapejara D´oeste (PR)");
		assertThat(survey.getCollectorInstallationDate()).isEqualTo(new Date(117, 10, 1));
		assertThat(survey.getEmergenceDate()).isEqualTo(new Date(117, 9, 10));
		assertThat(survey.getFarmerId()).isEqualTo(1);
		assertThat(survey.getFieldCityId()).isEqualTo(1l);
		assertThat(survey.getFieldCityState()).isEqualTo(State.PR);
		assertThat(survey.getFieldId()).isEqualTo(1l);
		assertThat(survey.getHarvestBeginDate()).isEqualTo(new Date(117, 8, 26));
		assertThat(survey.getHarvestDate()).isEqualTo(new Date(117, 1, 15));
		assertThat(survey.getHarvestEndDate()).isEqualTo(new Date(118, 1, 15));
		assertThat(survey.getHarvestId()).isEqualTo(1l);
		assertThat(survey.getId()).isEqualTo(1l);
		assertThat(survey.getSowedDate()).isEqualTo(new Date(117, 8, 26));
		assertThat(survey.getFieldSupervisorNames()).contains("Luiz Francisco Lovato");
		assertThat(survey.getFieldSupervisors()).isNotEmpty();
		assertThat(survey.getMacroRegion()).isNotEmpty();
		assertThat(survey.getRegion()).isNotEmpty();
	}

	@Test
	public void createSurveyWithoutHarvest() {
		survey.setField(field);
		Set<ConstraintViolation<Survey>> violations = validator.validate(survey);

		assertThat(violations.toString()).contains("Uma pesquisa deve ser referente a uma safra");
	}

	@Test
	public void createSurveyWithoutField() {
		survey.setHarvest(harvest);
		Set<ConstraintViolation<Survey>> violations = validator.validate(survey);

		assertThat(violations.toString()).contains("Uma pesquisa deve conter uma unidade de referência");
	}

	@Test
	public void getInvalidCultivarName() {
		survey.setCultivarData(null);
		String cultivarName = survey.getCultivarName();
		assertThat(cultivarName).isNull();
	}

	@Test
	public void isInvalidRustResistant() {
		survey.setCultivarData(null);
		boolean rustResistant = survey.isRustResistant();
		assertThat(rustResistant).isFalse();
	}

	@Test
	public void isInvalidBt() {
		survey.setCultivarData(null);
		boolean bt = survey.isBt();
		assertThat(bt).isFalse();
	}

	@Test
	public void getInvalidSowedDate() {
		survey.setCropData(null);
		Date sowedDate = survey.getSowedDate();
		assertThat(sowedDate).isNull();
	}

	@Test
	public void getInvalidEmergenceDate() {
		survey.setCropData(null);
		Date emergenceDate = survey.getEmergenceDate();
		assertThat(emergenceDate).isNull();
	}

	@Test
	public void getInvalidHarvestDate() {
		survey.setCropData(null);
		Date harvestDate = survey.getHarvestDate();
		assertThat(harvestDate).isNull();
	}

	@Test
	public void getInvalidLongitude() {
		survey.setLocationData(null);
		String longitude = survey.getLongitude();
		assertThat(longitude).isNull();
	}

	@Test
	public void getInvalidLatitude() {
		survey.setLocationData(null);
		String latitude = survey.getLatitude();
		assertThat(latitude).isNull();
	}

	@Test
	public void getInvalidProductivityField() {
		survey.setProductivityData(null);
		double productivityField = survey.getProductivityField();
		assertThat(productivityField).isEqualTo(0);
	}

	@Test
	public void getInvalidProductivityFarmer() {
		survey.setProductivityData(null);
		double productivityFarmer = survey.getProductivityFarmer();
		assertThat(productivityFarmer).isEqualTo(0);
	}

	@Test
	public void isInvalidSeparatedWeight() {
		survey.setProductivityData(null);
		boolean separatedWeight = survey.isSeparatedWeight();
		assertThat(separatedWeight).isFalse();
	}

	@Test
	public void getInvalidTotalArea() {
		survey.setSizeData(null);
		double totalArea = survey.getTotalArea();
		assertThat(totalArea).isEqualTo(0);
	}

	@Test
	public void getInvalidTotalPlantedArea() {
		survey.setSizeData(null);
		double totalPlantedArea = survey.getTotalPlantedArea();
		assertThat(totalPlantedArea).isEqualTo(0);
	}

	@Test
	public void getInvalidPlantPerMeter() {
		survey.setSizeData(null);
		double plantedPerMeter = survey.getPlantPerMeter();
		assertThat(plantedPerMeter).isEqualTo(0);
	}

	@Test
	public void getInvalidFieldId() {
		Long fieldId = survey.getFieldId();
		assertThat(fieldId).isNull();
	}

	@Test
	public void getInvalidFieldName() {
		String fieldName = survey.getFieldName();
		assertThat(fieldName).isNull();
	}

	@Test
	public void getInvalidFieldLocation() {
		String fieldLocation = survey.getFieldLocation();
		assertThat(fieldLocation).isNull();
	}

	@Test
	public void getInvalidFieldCityId() {
		Long fieldCityId = survey.getFieldCityId();
		assertThat(fieldCityId).isNull();
	}

	@Test
	public void getInvalidFieldCityName() {
		String fieldCityName = survey.getFieldCityName();
		assertThat(fieldCityName).isNull();
	}

	@Test
	public void getInvalidFieldCityState() {
		State fieldCityState = survey.getFieldCityState();
		assertThat(fieldCityState).isNull();
	}


	@Test
	public void getInvalidFarmerString() {
		String farmerString = survey.getFarmerString();
		assertThat(farmerString).isNull();
	}

	@Test
	public void getInvalidFieldSupervisors() {
		Set<Supervisor> fieldSupervisors = survey.getFieldSupervisors();
		assertThat(fieldSupervisors).isNull();
	}

	@Test
	public void getInvalidHarvestId() {
		Long harvestId = survey.getHarvestId();
		assertThat(harvestId).isNull();
	}
	
	@Test
	public void getInvalidHarvestName() {
		String harvestName = survey.getHarvestName();
		assertThat(harvestName).isNull();
	}
	
	@Test
	public void getInvalidHarvestBeginDate() {
		Date harvestBeginDate = survey.getHarvestBeginDate();
		assertThat(harvestBeginDate).isNull();
	}
	
	@Test
	public void getInvalidHarvestEndDate() {
		Date harvestEndDate = survey.getHarvestEndDate();
		assertThat(harvestEndDate).isNull();
	}

	@Test
	public void getInvalidFieldSupervisorNames() {
		String fieldSupervisorNames = survey.getFieldSupervisorNames();
		assertThat(fieldSupervisorNames).isNull();
	}
	
	@Test
	public void isInvalidSporeCollectorPresent() {
		survey.setMidData(null);
		boolean sporeCollectorPresent = survey.isSporeCollectorPresent();
		assertThat(sporeCollectorPresent).isFalse();
	}
	
	@Test
	public void getInvalidCollectorInstallationDate() {
		survey.setMidData(null);
		Date collectorInstallationDate = survey.getCollectorInstallationDate();
		assertThat(collectorInstallationDate).isNull();
	}
	
	@Test
	public void getInvalidSoyaPrice() {
		survey.setPulverisationData(null);
		double soyaPrice = survey.getSoyaPrice();
		assertThat(soyaPrice).isEqualTo(0);
	}
	
	@Test
	public void getInvalidApplicationCostCurrency() {
		survey.setPulverisationData(null);
		double applicationCostCurrency = survey.getApplicationCostCurrency();
		assertThat(applicationCostCurrency).isEqualTo(0);
	}
}
