package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.CropData;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import lombok.Getter;

public class MIPSampleTest {
	private MIPSample mIPSample;
	
	@Before
	public void init() {
		mIPSample = MIPSample.builder().build();
	}
	
	@Test
	public void createMIPSample() {
		Survey survey = createSurvey();
		Date sampleDate = new Date(117, 0, 24);
		mIPSample = MIPSample.builder().id(1l).defoliation(2).sampleDate(sampleDate).growthPhase(GrowthPhase.R7_1).survey(survey).build();
		
		assertThat(mIPSample.getId()).isEqualTo(1l);
		assertThat(mIPSample.getDefoliation()).isEqualTo(2);
		assertThat(mIPSample.getSampleDate()).isEqualTo(sampleDate);
		assertThat(mIPSample.getSurvey()).isEqualTo(survey);
		assertThat(mIPSample.getGrowthPhase()).isEqualTo(GrowthPhase.R7_1);
	}
	
	@Test
	public void addPestOccurrence() {
		Pest pest = Pest.builder().id(1l).usualName("Percevejo Marrom").pestSize(PestSize.ADULTO).scientificName("Euschistus sp.").build();
		boolean aux = mIPSample.addPestOccurrence(pest, 1d);
		assertThat(aux).isTrue();
		assertThat(mIPSample.getOccurrenceByPest(pest)).isNotEmpty();
	}

	@Test
	public void addPestDiseaseOccurrence() {
		PestDisease pestDisease = PestDisease.builder().id(1l).usualName("Lagartas com Nomuraea (Doença Branca)").build();
		boolean aux = mIPSample.addPestDiseaseOccurrence(pestDisease, 1d);
		assertThat(aux).isTrue();
		assertThat(mIPSample.getMipSamplePestDiseaseOccurrence()).isNotEmpty();
	}
	
	@Test
	public void addPestNaturalPredatorOccurrence() {
		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
		boolean aux = mIPSample.addPestNaturalPredatorOccurrence(pestNaturalPredator, 1d);
		assertThat(aux).isTrue();
		assertThat(mIPSample.getMipSampleNaturalPredatorOccurrence()).isNotEmpty();
	}
	
	@Test
	public void getHarvestName() {
		mIPSample.setSurvey(createSurvey());
		
		assertThat(mIPSample.getHarvestName()).isEqualTo("P95r51");
	}
	
	@Test
	public void getNullHarvestName() {
		mIPSample.setSurvey(null);
		
		assertThat(mIPSample.getHarvestName()).isNull();
	}
	
	@Test
	public void getHarvestId() {
		mIPSample.setSurvey(createSurvey());
		
		assertThat(mIPSample.getHarvestId().get()).isEqualTo(1);
	}
	
	@Test
	public void getFarmerName() {
		mIPSample.setSurvey(createSurvey());
		
		assertThat(mIPSample.getFarmerName()).isEqualTo("Gilson Dariva");
	}
	
	@Test
	public void obtainTheNullNameOfTheFarmerBeingSurveyNull() {
		mIPSample.setSurvey(null);
		
		assertThat(mIPSample.getFarmerName()).isNull();
	}
	
	@Test
	public void getFieldName() {
		mIPSample.setSurvey(createSurvey());
		
		assertThat(mIPSample.getFieldName()).isEqualTo("Trevo");
	}
	
	@Test
	public void obtainTheNullNameOfTheFieldBeingSurveyNull() {
		mIPSample.setSurvey(null);
		
		assertThat(mIPSample.getFieldName()).isNull();
	}
	
	@Test
	public void getCityName() {
		mIPSample.setSurvey(createSurvey());
		
		assertThat(mIPSample.getCityName()).isEqualTo("Itapejara D´oeste");
	}
	
	@Test
	public void getTheCityNullNameBeingSurveyNull() {
		mIPSample.setSurvey(null);
		
		assertThat(mIPSample.getCityName()).isNull();
	}
	
	@Test
	public void getSupervisorNames() {
		mIPSample.setSurvey(createSurvey());
		
		assertThat(mIPSample.getSupervisorNames()).isEqualTo("[Luiz Francisco Lovato]");
	}
	
	@Test
	public void obtainTheNullNamesOfTheSupervisorsBeingSurveyNull() {
		mIPSample.setSurvey(null);
		
		assertThat(mIPSample.getSupervisorNames()).isNull();
	}
	
	@Test
	public void getCultivarName() {
		mIPSample.setSurvey(createSurvey());
		
		assertThat(mIPSample.getCultivarName()).isEqualTo("P95R51");
	}
	
	@Test
	public void obtainTheNullNameOfTheCultivarBeingSurveyNull() {
		mIPSample.setSurvey(null);
		
		assertThat(mIPSample.getCultivarName()).isNull();
	}
	
	@Test
	public void getDAE() {
		Date sampleDate = new Date(118, 0, 18);
		Date emergenceDate = new Date(117, 9, 10);
		Survey survey = Survey.builder().emergenceDate(emergenceDate).build();
		mIPSample = MIPSample.builder().survey(survey).sampleDate(sampleDate).build();
		assertThat(mIPSample.getDAE()).isEqualTo(100);
	}
	
	@Test
	public void itShouldReturnZeroWhenASampleDateIsEarlierThanAEmergenceDate() {
		Date emergenceDate = new Date(118, 0, 18);
		Date sampleDate = new Date(117, 9, 10);
		Survey survey = Survey.builder().emergenceDate(emergenceDate).build();
		mIPSample = MIPSample.builder().survey(survey).sampleDate(sampleDate).build();
		assertThat(mIPSample.getDAE()).isEqualTo(0);
	}
	
	@Test
	public void itShouldReturnZeroWhenASurveyIsNull() {
		Date sampleDate = new Date(118, 0, 18);
		mIPSample = MIPSample.builder().survey(null).sampleDate(sampleDate).build();
		assertThat(mIPSample.getDAE()).isEqualTo(0);
	}
	
	@Test
	public void itShouldReturnZeroWhenAnEmergenceDateIsNull() {
		Date sampleDate = new Date(118, 0, 18);
		Survey survey = Survey.builder().emergenceDate(null).build();
		mIPSample = MIPSample.builder().survey(survey).sampleDate(sampleDate).build();
		assertThat(mIPSample.getDAE()).isEqualTo(0);
	}
	
	@Test
	public void itShouldReturnZeroWhenASampleDateIsNull() {
		Date emergenceDate = new Date(117, 9, 10);
		Survey survey = Survey.builder().emergenceDate(emergenceDate).build();
		mIPSample = MIPSample.builder().sampleDate(null).survey(survey).build();
		assertThat(mIPSample.getDAE()).isEqualTo(0);
	}
	
	@Test
	public void getOccurrenceByPest() {
		Pest pest = Pest.builder().id(1l).usualName("Percevejo Marrom").pestSize(PestSize.ADULTO).scientificName("Euschistus sp.").build();
		mIPSample.addPestOccurrence(pest, 1d);
		assertThat(mIPSample.getOccurrenceByPest(pest)).isNotEmpty();
	}
	
	@Test
	public void mustReturnEmptyForAPestThatIsNotPresent() {
		Pest pest = Pest.builder().id(1l).usualName("Percevejo Marrom").pestSize(PestSize.ADULTO).scientificName("Euschistus sp.").build();
		Pest pest2 = Pest.builder().id(1l).usualName("Percevejo Barriga Verde").pestSize(PestSize.ADULTO).scientificName("Dichelops ssp.").build();
		mIPSample.addPestOccurrence(pest, 1d);
		assertThat(mIPSample.getOccurrenceByPest(pest2)).isEmpty();
	}
	
//	@Test
//	public void mustReturnEmptyWhenLookingForOccurrenceAndThereIsNoMipSamplePestOccurrence() {
//		Pest pest = Pest.builder().id(1l).usualName("Percevejo Marrom").pestSize(PestSize.ADULTO).scientificName("Euschistus sp.").build();
//		
//		assertThat(mIPSample.getOccurrenceByPest(pest)).isEmpty();
//	}
	
	@Test
	public void getOccurrenceByPredator() {
		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
		mIPSample.addPestNaturalPredatorOccurrence(pestNaturalPredator, 1d);
		assertThat(mIPSample.getOccurrenceByPredator(pestNaturalPredator)).isNotEmpty();
	}
	
	@Test
	public void mustReturnEmptyForAPredatorThatIsNotPresent() {
		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
		PestNaturalPredator pestNaturalPredator2 = PestNaturalPredator.builder().id(1l).usualName("Lebia concinna").build();
		
		mIPSample.addPestNaturalPredatorOccurrence(pestNaturalPredator, 1d);
		
		assertThat(mIPSample.getOccurrenceByPredator(pestNaturalPredator2)).isEmpty();
	}
	
//	@Test
//	public void mustReturnEmptyWhenLookingForOccurrenceAndThereIsNoMipSampleNaturalPredatorOccurrence() {
//		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
//		
//		assertThat(mIPSample.getOccurrenceByPredator(pestNaturalPredator)).isEmpty();
//	}
	
	@Test 
	public void getOccurrenceValueByPest() {
		Pest pest = Pest.builder().id(1l).usualName("Percevejo Marrom").pestSize(PestSize.ADULTO).scientificName("Euschistus sp.").build();
		mIPSample.addPestOccurrence(pest, 2d);
		
		assertThat(mIPSample.getOccurrenceValueByPest(pest)).isEqualTo(2d);
	}
	
	@Test 
	public void mustReturnZeroForAPestThatIsNotPresent() {
		Pest pest = Pest.builder().id(1l).usualName("Percevejo Marrom").pestSize(PestSize.ADULTO).scientificName("Euschistus sp.").build();
		Pest pest2 = Pest.builder().id(1l).usualName("Percevejo Barriga Verde").pestSize(PestSize.ADULTO).scientificName("Dichelops ssp.").build();
		mIPSample.addPestOccurrence(pest, 1d);
		assertThat(mIPSample.getOccurrenceValueByPest(pest2)).isEqualTo(0);
	}
	
//	@Test 
//	public void mustReturnZeroWhenLookingForOccurrenceAndThereIsNoMipSamplePestOccurrence() {
//		Pest pest = Pest.builder().id(1l).usualName("Percevejo Marrom").pestSize(PestSize.ADULTO).scientificName("Euschistus sp.").build();
//		
//		assertThat(mIPSample.getOccurrenceValueByPest(pest)).isEqualTo(0);
//	}
	
	@Test
	public void getOccurrenceValueByPredator() {
		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
		mIPSample.addPestNaturalPredatorOccurrence(pestNaturalPredator, 2d);
		assertThat(mIPSample.getOccurrenceValueByPredator(pestNaturalPredator)).isEqualTo(2d);
	}
	
	@Test
	public void mustReturnZeroForAPredatorThatIsNotPresent() {
		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
		PestNaturalPredator pestNaturalPredator2 = PestNaturalPredator.builder().id(1l).usualName("Lebia concinna").build();
		
		mIPSample.addPestNaturalPredatorOccurrence(pestNaturalPredator, 1d);
		
		assertThat(mIPSample.getOccurrenceValueByPredator(pestNaturalPredator2)).isEqualTo(0);
	}
	
//	@Test
//	public void mustReturnZeroWhenLookingForOccurrenceAndThereIsNoMipSampleNaturalPredatorOccurrence() {
//		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
//		
//		assertThat(mIPSample.getOccurrenceValueByPredator(pestNaturalPredator)).isEqualTo(0);
//	}

	@Test
	public void getDAEAndPredatorOccurrenceByPredator() {
		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
		mIPSample.addPestNaturalPredatorOccurrence(pestNaturalPredator, 2d);
		assertThat(mIPSample.getDAEAndPredatorOccurrenceByPredator(pestNaturalPredator)).isNotEmpty();
	}
	
	@Test
	public void mustReturnEmptyWhenAPredatorIsNotPresent() {
		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
		PestNaturalPredator pestNaturalPredator2 = PestNaturalPredator.builder().id(1l).usualName("Lebia concinna").build();
		
		mIPSample.addPestNaturalPredatorOccurrence(pestNaturalPredator, 1d);
		assertThat(mIPSample.getDAEAndPredatorOccurrenceByPredator(pestNaturalPredator2)).isEmpty();
	}
	
	@Test
	public void mustReturnEmptyForANullPredator() {
		assertThat(mIPSample.getDAEAndPredatorOccurrenceByPredator(null)).isEmpty();
	}
	
//	@Test
//	public void mustReturnEmptyWhenLookingForDAEAndPredatorOccurrenceByPredatorAndThereIsNoMipSampleNaturalPredatorOccurrence() {
//
//		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
//		
//		assertThat(mIPSample.getDAEAndPredatorOccurrenceByPredator(pestNaturalPredator)).isEmpty();
//	
//	}
	
	@Test
	public void getDAEAndPestOccurrenceByPest() {
		Pest pest = Pest.builder().id(1l).usualName("Percevejo Marrom").pestSize(PestSize.ADULTO).scientificName("Euschistus sp.").build();
		mIPSample.addPestOccurrence(pest, 2d);
		assertThat(mIPSample.getDAEAndPestOccurrenceByPest(pest)).isNotEmpty();
	}
	
	@Test
	public void mustReturnEmptyWhenAPestIsNotPresent() {
		Pest pest = Pest.builder().id(1l).usualName("Percevejo Marrom").pestSize(PestSize.ADULTO).scientificName("Euschistus sp.").build();
		Pest pest2 = Pest.builder().id(1l).usualName("Percevejo Barriga Verde").pestSize(PestSize.ADULTO).scientificName("Dichelops ssp.").build();
		mIPSample.addPestOccurrence(pest, 1d);
		assertThat(mIPSample.getDAEAndPestOccurrenceByPest(pest2)).isEmpty();
	}
	
	@Test
	public void mustReturnEmptyForANullPest() {
		assertThat(mIPSample.getDAEAndPestOccurrenceByPest(null)).isEmpty();
	}
	
//	@Test
//	public void mustReturnEmptyWhenLookingForDAEAndPestOccurrenceByPestAndThereIsNoMipSamplePestOccurrence() {
//		Pest pest = Pest.builder().id(1l).usualName("Percevejo Marrom").pestSize(PestSize.ADULTO).scientificName("Euschistus sp.").build();
//		
//		assertThat(mIPSample.getDAEAndPestOccurrenceByPest(pest)).isEmpty();
//	}
	
	@Test
	public void getFieldId() {
		mIPSample.setSurvey(createSurvey());
		assertThat(mIPSample.getFieldId().get()).isEqualTo(1);
	}
	
	@Test
	public void getCity() {
		mIPSample.setSurvey(createSurvey());
		assertThat(mIPSample.getCity().get().toString()).isEqualTo("Itapejara D´oeste (PR)");
	}
	
	@Test
	public void getSampleDateAsOptional() {
		Date sampleDate = new Date(117, 0, 24);
		mIPSample.setSampleDate(sampleDate);
		assertThat(mIPSample.getSampleDateAsOptional().get()).isEqualTo(sampleDate);
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
	
}
