package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;

public class MIDRustSampleTest {

	private MIDRustSample mIDRustSample;
	private Validator validator;
	
	@Before
	public void init() {
		mIDRustSample = MIDRustSample.builder().build();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void createMIDRustSample() {
		Survey survey = Survey.builder().build();
		Date sampleDate = new Date(118, 0, 16); 
		MIDSampleSporeCollectorOccurrence mIDSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder().build();
		MIDSampleLeafInspectionOccurrence mIDSampleLeafInspectionOccurrence = MIDSampleLeafInspectionOccurrence.builder().build();
		
		mIDRustSample.setId(1l);
		mIDRustSample.setSurvey(survey);
		mIDRustSample.setSampleDate(sampleDate);
		mIDRustSample.setSporeCollectorOccurrence(mIDSampleSporeCollectorOccurrence);
		mIDRustSample.setLeafInspectionOccurrence(mIDSampleLeafInspectionOccurrence);
		
		assertThat(mIDRustSample.getId()).isEqualTo(1l);
		assertThat(mIDRustSample.getSurvey()).isEqualTo(survey);
		assertThat(mIDRustSample.getSampleDate()).isEqualTo(sampleDate);
		assertThat(mIDRustSample.getSporeCollectorOccurrence()).isEqualTo(mIDSampleSporeCollectorOccurrence);
		assertThat(mIDRustSample.getLeafInspectionOccurrence()).isEqualTo(mIDSampleLeafInspectionOccurrence);	
	}
	
	@Test
	public void getSampleDate() {
		Date sampleDate = new Date(118, 0, 16); 
		mIDRustSample.setSampleDate(sampleDate);
		
		assertThat(mIDRustSample.getSampleDate()).isEqualTo(sampleDate);
	}
	
	@Test
	public void getNullSampleDate() {
		mIDRustSample.setSampleDate(null);
		Set<ConstraintViolation<MIDRustSample>> violations = validator.validate(mIDRustSample);
		
		assertThat(violations.toString()).contains("A data da coleta precisa ser informada!");
	}
	
	@Test
	public void getSurvey() {
		Survey survey = Survey.builder().build();
		mIDRustSample.setSurvey(survey);
		
		assertThat(mIDRustSample.getSurvey()).isEqualTo(survey);
	}
	
	@Test
	public void getNullSurvey() {
		mIDRustSample.setSurvey(null);
		
		assertThat(mIDRustSample.getSurvey()).isNull();
	}
	
	@Test 
	public void getSporeCollectorOccurrence() {
		MIDSampleSporeCollectorOccurrence mIDSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder().build();
		
		mIDRustSample.setSporeCollectorOccurrence(mIDSampleSporeCollectorOccurrence);
		
		assertThat(mIDRustSample.getSporeCollectorOccurrence()).isEqualTo(mIDSampleSporeCollectorOccurrence);
	}
	
	@Test 
	public void getNullSporeCollectorOccurrence() {
		mIDRustSample.setSporeCollectorOccurrence(null);
		
		assertThat(mIDRustSample.getSporeCollectorOccurrence()).isNull();
	}
	
	
	@Test 
	public void getLeafInspectionOccurrence() {
		MIDSampleLeafInspectionOccurrence mIDSampleLeafInspectionOccurrence = MIDSampleLeafInspectionOccurrence.builder().build();
		
		mIDRustSample.setLeafInspectionOccurrence(mIDSampleLeafInspectionOccurrence);
		
		assertThat(mIDRustSample.getLeafInspectionOccurrence()).isEqualTo(mIDSampleLeafInspectionOccurrence);
	}
	
	@Test 
	public void getNullLeafInspectionOccurrence() {
		mIDRustSample.setLeafInspectionOccurrence(null);
		
		assertThat(mIDRustSample.getLeafInspectionOccurrence()).isNull();
	}
	
	
	@Test
	public void getSampleDateAsOptional() {
		Date sampleDate = new Date(118, 0, 16); 
		mIDRustSample.setSampleDate(sampleDate);
		
		assertThat(mIDRustSample.getSampleDateAsOptional().get()).isEqualTo(sampleDate);
	}
	
	@Test
	public void getNullSampleDateAsOptional() {
		mIDRustSample.setSampleDate(null);
		
		assertThat(mIDRustSample.getSampleDateAsOptional()).isEmpty();
	}
	
	@Test
	public void isSporePresent() {
		MIDSampleSporeCollectorOccurrence mIDSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder().bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.COM_ESPOROS_SEM_TESTAR_VIABILIDADE).build();
		mIDRustSample.setSporeCollectorOccurrence(mIDSampleSporeCollectorOccurrence);
		
		assertThat(mIDRustSample.isSporePresent()).isTrue();
	}
	
	@Test
	public void iShouldtReturnFalseWhenThereIsNoRustSpore() {
		MIDSampleSporeCollectorOccurrence mIDSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder().bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.SEM_ESPOROS_FERRUGEM).build();
		mIDRustSample.setSporeCollectorOccurrence(mIDSampleSporeCollectorOccurrence);
		
		assertThat(mIDRustSample.isSporePresent()).isFalse();
	}
	
	@Test
	public void isNullSporePresent() {
		mIDRustSample.setSporeCollectorOccurrence(null);
		
		assertThat(mIDRustSample.isSporePresent()).isFalse();
	}
	
}
