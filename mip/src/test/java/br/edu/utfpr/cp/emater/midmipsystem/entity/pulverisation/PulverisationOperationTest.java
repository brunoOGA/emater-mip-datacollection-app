package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.ProductUseClassDifferFromTargetException;

public class PulverisationOperationTest {

	private PulverisationOperation pulverisationOperation;
	private Validator validator;
	
	@Before
	public void init() {
		pulverisationOperation = PulverisationOperation.builder().build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void createPulverisationOperation() {
		Survey survey = Survey.builder().build();
		Date sampleDate = new Date(118, 0, 16); 
		
		pulverisationOperation.setId(1l);
		pulverisationOperation.setSurvey(survey);
		pulverisationOperation.setSampleDate(sampleDate);
		pulverisationOperation.setGrowthPhase(GrowthPhase.V2);
		pulverisationOperation.setCaldaVolume(125d);
		pulverisationOperation.setPulverisationArea(2d);
		
		assertThat(pulverisationOperation.getId()).isEqualTo(1l);
		assertThat(pulverisationOperation.getSurvey()).isEqualTo(survey);
		assertThat(pulverisationOperation.getSampleDate()).isEqualTo(sampleDate);
		assertThat(pulverisationOperation.getGrowthPhase()).isEqualTo(GrowthPhase.V2);
		assertThat(pulverisationOperation.getCaldaVolume()).isEqualTo(125d);
		assertThat(pulverisationOperation.getPulverisationArea()).isEqualTo(2d);
	}
	
	@Test
	public void setSampleDate() {
		Date sampleDate = new Date(118, 0, 16); 
		pulverisationOperation.setSampleDate(sampleDate);
		
		assertThat(pulverisationOperation.getSampleDate()).isEqualTo(sampleDate);
	}
	
	@Test
	public void setNullSampleDate() {
		pulverisationOperation.setSampleDate(null);
		
		Set<ConstraintViolation<PulverisationOperation>> violations = validator.validate(pulverisationOperation);

		assertThat(violations.toString()).contains("A data da coleta precisa ser informada!");
	}
	
	@Test
	public void addOperationOccurrence() throws ProductUseClassDifferFromTargetException {
		boolean result = pulverisationOperation.addOperationOccurrence(createProduct(), 13.5, 1.2, createTarget());
		assertThat(result).isTrue();
		assertThat(pulverisationOperation.getOperationOccurrences().size()).isEqualTo(1);
	}
	
	@Test(expected = ProductUseClassDifferFromTargetException.class)
	public void addIncorrectOperationOccurrence() throws ProductUseClassDifferFromTargetException  {
		Product product = Product.builder().name("").useClass(UseClass.ACARICIDA).build();
		Target target = Target.builder().useClass(UseClass.ADJUVANTE).build();
		pulverisationOperation.addOperationOccurrence(product, 13.5, 1.2, target);
	}
	
	@Test
	public void getSoyaPrice() {
		pulverisationOperation.setSurvey(createSurvey());
		
		assertThat(pulverisationOperation.getSoyaPrice()).isEqualTo(70);
	}
	
	@Test
	public void getNullSurveySoyaPrice() {
		pulverisationOperation.setSurvey(null);
		
		assertThat(pulverisationOperation.getSoyaPrice()).isZero();
	}
	
	@Test
	public void getNullPulverisationDataSoyaPrice() {
		Survey survey = createSurvey();
		survey.setPulverisationData(null);
		pulverisationOperation.setSurvey(survey);
		
		assertThat(pulverisationOperation.getSoyaPrice()).isZero();
	}
	
	@Test
	public void getApplicationCostCurrency() {
		pulverisationOperation.setSurvey(createSurvey());
		
		assertThat(pulverisationOperation.getApplicationCostCurrency()).isEqualTo(49);
	}
	
	@Test
	public void getNullSurveyApplicationCostCurrency() {
		pulverisationOperation.setSurvey(null);
		
		assertThat(pulverisationOperation.getApplicationCostCurrency()).isZero();
	}
	
	@Test
	public void getNullPulverisationDataApplicationCostCurrency() {
		Survey survey = createSurvey();
		survey.setPulverisationData(null);
		pulverisationOperation.setSurvey(survey);
		
		assertThat(pulverisationOperation.getApplicationCostCurrency()).isZero();
	}
	
	@Test
	public void getApplicationCostQty() {
		pulverisationOperation.setSurvey(createSurvey());
		
		assertThat(pulverisationOperation.getApplicationCostQty()).isEqualTo(0.7);
	}
	
	@Test
	public void getNullSurveyApplicationCostQty() {
		pulverisationOperation.setSurvey(null);
		
		assertThat(pulverisationOperation.getApplicationCostQty()).isZero();
	}
	
	@Test
	public void getNullPulverisationDataApplicationCostQty() {
		Survey survey = createSurvey();
		survey.setPulverisationData(null);
		pulverisationOperation.setSurvey(survey);
		
		assertThat(pulverisationOperation.getApplicationCostQty()).isZero();
	}
	
	@Test
	public void getTotalOperationCostCurrency() throws ProductUseClassDifferFromTargetException {
		pulverisationOperation.addOperationOccurrence(createProduct(), 13.5, 1.2, createTarget()); //16.2
		pulverisationOperation.addOperationOccurrence(createProduct(), 22.5, 4.2, createTarget()); //94.5
		pulverisationOperation.setSurvey(createSurvey()); //49
		
		assertThat(pulverisationOperation.getTotalOperationCostCurrency()).isEqualTo(159.7);
	}
	
	@Test
	public void getZeroOperationCostCurrency() {
		assertThat(pulverisationOperation.getTotalOperationCostCurrency()).isZero();
	}
	
	@Test
	public void isTargetMIP() throws ProductUseClassDifferFromTargetException {
		Target target = Target.builder().useClass(UseClass.INSETICIDA).build();
		Product product = Product.builder().name("").useClass(UseClass.INSETICIDA).build();
		pulverisationOperation.addOperationOccurrence(product, 13.5, 1.2, target);
		
		boolean result = pulverisationOperation.isTargetMIP();
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void isNotTargetMIP() throws ProductUseClassDifferFromTargetException {
		Target target = Target.builder().useClass(UseClass.FUNGICIDA).build();
		Product product = Product.builder().name("").useClass(UseClass.FUNGICIDA).build();
		pulverisationOperation.addOperationOccurrence(product, 13.5, 1.2, target);
		
		boolean result = pulverisationOperation.isTargetMIP();
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void isInseticidaBiologico() throws ProductUseClassDifferFromTargetException {
		Target target = Target.builder().useClass(UseClass.INCETICIDA_BIOLOGICO).build();
		Product product = Product.builder().name("").useClass(UseClass.INCETICIDA_BIOLOGICO).build();
		pulverisationOperation.addOperationOccurrence(product, 13.5, 1.2, target);
		
		boolean result = pulverisationOperation.isInseticidaBiologico();
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void isNotInseticidaBiologico() throws ProductUseClassDifferFromTargetException {
		Target target = Target.builder().useClass(UseClass.FUNGICIDA).build();
		Product product = Product.builder().name("").useClass(UseClass.FUNGICIDA).build();
		pulverisationOperation.addOperationOccurrence(product, 13.5, 1.2, target);
		
		boolean result = pulverisationOperation.isInseticidaBiologico();
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void isTargetMID() throws ProductUseClassDifferFromTargetException {
		Target target = Target.builder().useClass(UseClass.FUNGICIDA).build();
		Product product = Product.builder().name("").useClass(UseClass.FUNGICIDA).build();
		pulverisationOperation.addOperationOccurrence(product, 13.5, 1.2, target);
		
		boolean result = pulverisationOperation.isTargetMID();
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void isNotTargetMID() throws ProductUseClassDifferFromTargetException {
		Target target = Target.builder().useClass(UseClass.INCETICIDA_BIOLOGICO).build();
		Product product = Product.builder().name("").useClass(UseClass.INCETICIDA_BIOLOGICO).build();
		pulverisationOperation.addOperationOccurrence(product, 13.5, 1.2, target);
		
		boolean result = pulverisationOperation.isTargetMID();
		
		assertThat(result).isFalse();
	}
	@Test
	public void getDAE() {
		Date sampleDate = new Date(118, 0, 18);
		Date emergenceDate = new Date(117, 9, 10);
		Survey survey = Survey.builder().emergenceDate(emergenceDate).build();
		pulverisationOperation = PulverisationOperation.builder().survey(survey).sampleDate(sampleDate).build();
		assertThat(pulverisationOperation.getDAE()).isEqualTo(100);
	}
	
	@Test
	public void itShouldReturnZeroWhenASampleDateIsEarlierThanAEmergenceDate() {
		Date emergenceDate = new Date(118, 0, 18);
		Date sampleDate = new Date(117, 9, 10);
		Survey survey = Survey.builder().emergenceDate(emergenceDate).build();
		pulverisationOperation = PulverisationOperation.builder().survey(survey).sampleDate(sampleDate).build();
		assertThat(pulverisationOperation.getDAE()).isEqualTo(0);
	}
	
	@Test
	public void itShouldReturnZeroWhenASurveyIsNull() {
		Date sampleDate = new Date(118, 0, 18);
		pulverisationOperation = PulverisationOperation.builder().survey(null).sampleDate(sampleDate).build();
		assertThat(pulverisationOperation.getDAE()).isEqualTo(0);
	}
	
	@Test
	public void itShouldReturnZeroWhenAnEmergenceDateIsNull() {
		Date sampleDate = new Date(118, 0, 18);
		Survey survey = Survey.builder().emergenceDate(null).build();
		pulverisationOperation = PulverisationOperation.builder().survey(survey).sampleDate(sampleDate).build();
		assertThat(pulverisationOperation.getDAE()).isEqualTo(0);
	}
	
	@Test
	public void itShouldReturnZeroWhenASampleDateIsNull() {
		Date emergenceDate = new Date(117, 9, 10);
		Survey survey = Survey.builder().emergenceDate(emergenceDate).build();
		pulverisationOperation = PulverisationOperation.builder().sampleDate(null).survey(survey).build();
		assertThat(pulverisationOperation.getDAE()).isEqualTo(0);
	}
	
	public Survey createSurvey() {
		City city = City.builder().name("Itapejara D´Oeste").state(State.PR).build();
		city.setId(1l);
		Set<City> cities = new HashSet<>();
		cities.add(city);
		Farmer farmer = Farmer.builder().id(1l).name("Gilson Dariva").build();
		MacroRegion macroRegion = MacroRegion.builder().id(1l).name("Pato Branco").build();
		Region region = Region.builder().id(1l).name("Pato Branco").cities(cities).macroRegion(macroRegion).build();
		Supervisor supervisor = Supervisor.builder().id(1l).name("Luiz Francisco Lovato")
				.email("grpatobranco@emater.pr.gov.br").region(region).build();
		Field field = Field.builder().id(1l).city(city).farmer(farmer).location("1").name("Trevo").build();
		field.addSupervisor(supervisor);
		Harvest harvest = Harvest.builder().id(1l).begin(new Date(117, 8, 26)).end(new Date(118, 1, 15)).name("P95R51").build();
		Survey survey = Survey.builder().id(1l).cultivarName("P95R51").sporeCollectorPresent(true)
				.collectorInstallationDate(new Date(117, 10, 1)).rustResistant(false).bt(false)
				.sowedDate(new Date(117, 8, 26)).emergenceDate(new Date(117, 9, 10)).harvestDate(new Date(117, 1, 15))
				.longitude("25°58'35.63\"").latitude("52°48'45.68\"").productivityField(4636d).productivityFarmer(4215d)
				.separatedWeight(true).totalArea(7.26).totalPlantedArea(242).plantPerMeter(15).soyaPrice(70.00)
				.applicationCostCurrency(49).harvest(harvest).field(field).build();
		return survey;
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
