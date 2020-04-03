package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import lombok.Getter;

public class MIPSampleNaturalPredatorOccurrenceTest {
	
	private MIPSampleNaturalPredatorOccurrence mIPSampleNaturalPredatorOccurrence; 
	
	@Before
	public void init() {
		mIPSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().build();
	}
	
	@Test
	public void createMIPSamplePestDiseaseOccurrence() {
		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
		mIPSampleNaturalPredatorOccurrence.setValue(1);
		mIPSampleNaturalPredatorOccurrence.setPestNaturalPredator(pestNaturalPredator);
		
		assertThat(mIPSampleNaturalPredatorOccurrence.getValue()).isEqualTo(1l);
		assertThat(mIPSampleNaturalPredatorOccurrence.getPestNaturalPredator()).isEqualTo(pestNaturalPredator);
	}

	@Test
	public void setValue() {
		mIPSampleNaturalPredatorOccurrence.setValue(0.1);
		
		assertThat(mIPSampleNaturalPredatorOccurrence.getValue()).isEqualTo(0.1);
	}
	
	@Test
	public void itShouldAcceptZeroValue() {
		mIPSampleNaturalPredatorOccurrence.setValue(0);
		
		assertThat(mIPSampleNaturalPredatorOccurrence.getValue()).isEqualTo(0);
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldNotAcceptNegativeValue() {
		mIPSampleNaturalPredatorOccurrence.setValue(-1);
	}
	
	@Test
	public void getPestNaturalPredatorUsualName() {
		PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id(1l).usualName("Cycloneda sanguinea").build();
		mIPSampleNaturalPredatorOccurrence.setPestNaturalPredator(pestNaturalPredator);
		
		assertThat(mIPSampleNaturalPredatorOccurrence.getPestNaturalPredatorUsualName()).isEqualTo("Cycloneda Sanguinea");
	}
	
	@Test
	public void getNullPestNaturalPredatorUsualName() {
		mIPSampleNaturalPredatorOccurrence.setPestNaturalPredator(null);
		
		assertThat(mIPSampleNaturalPredatorOccurrence.getPestNaturalPredatorUsualName()).isNull();
	}
}
