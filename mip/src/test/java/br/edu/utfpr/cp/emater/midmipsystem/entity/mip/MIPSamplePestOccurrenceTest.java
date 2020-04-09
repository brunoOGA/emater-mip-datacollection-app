package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.PulverisationData;
import lombok.Getter;

public class MIPSamplePestOccurrenceTest {

	private MIPSamplePestOccurrence mIPSamplePestOccurrence;
	
	@Before
	public void init() {
		mIPSamplePestOccurrence = MIPSamplePestOccurrence.builder().build();
	}
	
	@Test
	public void createMIPSamplePestOccurrence() {
		Pest pest = createPest();
		mIPSamplePestOccurrence.setPest(pest);
		mIPSamplePestOccurrence.setValue(1);
		
		assertThat(mIPSamplePestOccurrence.getPest()).isEqualTo(pest);
		assertThat(mIPSamplePestOccurrence.getValue()).isEqualTo(1);
	}
	
	@Test
	public void setValue() {
		mIPSamplePestOccurrence.setValue(0.1);
		
		assertThat(mIPSamplePestOccurrence.getValue()).isEqualTo(0.1);
	}
	
	@Test
	public void itShouldAcceptZeroValue() {
		mIPSamplePestOccurrence.setValue(0);
		
		assertThat(mIPSamplePestOccurrence.getValue()).isEqualTo(0);
	}
	
//	@Test(expected = RuntimeException.class)
//	public void shouldNotAcceptNegativeValue() {
//		mIPSamplePestOccurrence.setValue(-1);
//	}
	
	@Test
	public void getPestUsualName() {
		Pest pest = createPest();
		mIPSamplePestOccurrence.setPest(pest);
		
		assertThat(mIPSamplePestOccurrence.getPestUsualName()).isEqualTo("Percevejo Marrom");
	}
	
	@Test
	public void getNullPestUsualName() {
		mIPSamplePestOccurrence.setPest(null);
		
		assertThat(mIPSamplePestOccurrence.getPestUsualName()).isNull();
	}
	
	@Test
	public void getPestScientificName() {
		Pest pest = createPest();
		mIPSamplePestOccurrence.setPest(pest);
		
		assertThat(mIPSamplePestOccurrence.getPestScientificName()).isEqualTo("Euschistus sp.");
	}
	
	@Test
	public void getNullPestScientificName() {
		mIPSamplePestOccurrence.setPest(null);
		
		assertThat(mIPSamplePestOccurrence.getPestScientificName()).isNull();
	}
	
	@Test
	public void getPestSizeName() {
		Pest pest = createPest();
		mIPSamplePestOccurrence.setPest(pest);
		
		assertThat(mIPSamplePestOccurrence.getPestSizeName()).isEqualTo("Adulto");
	}
	
	@Test
	public void getNullPestSizeName() {
		mIPSamplePestOccurrence.setPest(null);
		
		assertThat(mIPSamplePestOccurrence.getPestSizeName()).isNull();
	}
	
	public Pest createPest() {
		Pest pest = Pest.builder().id(1l).usualName("Percevejo Marrom").pestSize(PestSize.ADULTO).scientificName("Euschistus sp.").build();
		return pest;
	}
}
