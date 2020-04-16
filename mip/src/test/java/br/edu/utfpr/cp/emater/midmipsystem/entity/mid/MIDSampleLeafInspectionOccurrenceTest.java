package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;

public class MIDSampleLeafInspectionOccurrenceTest {

	private MIDSampleLeafInspectionOccurrence mIDSampleLeafInspectionOccurrence;
	
	@Before
	public void init() {
		mIDSampleLeafInspectionOccurrence = MIDSampleLeafInspectionOccurrence.builder().build();
	}
	
	@Test
	public void createMIDSampleLeafInspectionOccurrence() {
		mIDSampleLeafInspectionOccurrence.setGrowthPhase(GrowthPhase.R4);
		mIDSampleLeafInspectionOccurrence.setBladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.SEM_LESOES_VISIVEIS);
	
		assertThat(mIDSampleLeafInspectionOccurrence.getGrowthPhase()).isEqualTo(GrowthPhase.R4);
		assertThat(mIDSampleLeafInspectionOccurrence.getBladeReadingRustResultLeafInspection()).isEqualTo(AsiaticRustTypesLeafInspection.SEM_LESOES_VISIVEIS);
	}
	
	@Test
	public void getGrowthPhase() {
		mIDSampleLeafInspectionOccurrence.setGrowthPhase(GrowthPhase.R4);
		
		assertThat(mIDSampleLeafInspectionOccurrence.getGrowthPhase()).isEqualTo(GrowthPhase.R4);
	}
	
	@Test
	public void getNullGrowthPhase() {
		mIDSampleLeafInspectionOccurrence.setGrowthPhase(null);
		
		assertThat(mIDSampleLeafInspectionOccurrence.getGrowthPhase()).isEqualTo(null);
	}
	
	@Test
	public void getBladeReadingRustResultLeafInspection() {
		mIDSampleLeafInspectionOccurrence.setBladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.SEM_LESOES_VISIVEIS);
		
		assertThat(mIDSampleLeafInspectionOccurrence.getBladeReadingRustResultLeafInspection()).isEqualTo(AsiaticRustTypesLeafInspection.SEM_LESOES_VISIVEIS);
	}
	
	@Test
	public void getNullBladeReadingRustResultLeafInspection() {
		mIDSampleLeafInspectionOccurrence.setBladeReadingRustResultLeafInspection(null);
		
		assertThat(mIDSampleLeafInspectionOccurrence.getBladeReadingRustResultLeafInspection()).isEqualTo(null);
	}
}
