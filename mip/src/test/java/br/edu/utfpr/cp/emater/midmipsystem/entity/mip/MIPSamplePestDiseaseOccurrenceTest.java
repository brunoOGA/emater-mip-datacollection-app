package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class MIPSamplePestDiseaseOccurrenceTest {
	
	private MIPSamplePestDiseaseOccurrence mIPSamplePestDiseaseOccurrence; 
	
	@Before
	public void init() {
		mIPSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().build();
	}
	
	@Test
	public void createMIPSamplePestDiseaseOccurrence() {
		PestDisease pestDisease = PestDisease.builder().id(1l).usualName("Lagartas com Nomuraea (Doença Branca)").build();
		mIPSamplePestDiseaseOccurrence.setValue(1);
		mIPSamplePestDiseaseOccurrence.setPestDisease(pestDisease);
		
		assertThat(mIPSamplePestDiseaseOccurrence.getValue()).isEqualTo(1l);
		assertThat(mIPSamplePestDiseaseOccurrence.getPestDisease()).isEqualTo(pestDisease);
	}

	@Test
	public void setValue() {
		mIPSamplePestDiseaseOccurrence.setValue(0.1);
		
		assertThat(mIPSamplePestDiseaseOccurrence.getValue()).isEqualTo(0.1);
	}
	
	@Test
	public void itShouldAcceptZeroValue() {
		mIPSamplePestDiseaseOccurrence.setValue(0);
		
		assertThat(mIPSamplePestDiseaseOccurrence.getValue()).isEqualTo(0);
	}
	
//	@Test(expected = RuntimeException.class)
//	public void shouldNotAcceptNegativeValue() {
//		mIPSamplePestDiseaseOccurrence.setValue(-1);
//	}
	
	@Test
	public void getPestDiseaseUsualName() {
		PestDisease pestDisease = PestDisease.builder().id(1l).usualName("Lagartas com Nomuraea (Doença Branca)").build();
		mIPSamplePestDiseaseOccurrence.setPestDisease(pestDisease);
		
		assertThat(mIPSamplePestDiseaseOccurrence.getPestDiseaseUsualName()).isEqualTo("Lagartas com Nomuraea (Doença Branca)");
	}
	
	@Test
	public void getNullPestDiseaseUsualName() {
		mIPSamplePestDiseaseOccurrence.setPestDisease(null);
		
		assertThat(mIPSamplePestDiseaseOccurrence.getPestDiseaseUsualName()).isNull();
	}
	
}
