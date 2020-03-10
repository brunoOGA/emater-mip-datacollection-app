package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SizeDataTest {

	private SizeData sizeData;
	
	@Before
	public void init() {
		sizeData = SizeData.builder().build();
	}
	
	@Test
	public void createSizeDataTest() {
		sizeData.setPlantPerMeter(15);
		sizeData.setTotalArea(7.26);
		sizeData.setTotalPlantedArea(242);
		
		assertThat(sizeData.getPlantPerMeter()).isEqualTo(15);
		assertThat(sizeData.getTotalArea()).isEqualTo(7.26);
		assertThat(sizeData.getTotalPlantedArea()).isEqualTo(242);
	}
	
	@Test
	public void createSizeDataWithoutValues() {
		assertThat(sizeData.getPlantPerMeter()).isEqualTo(0);
		assertThat(sizeData.getTotalArea()).isEqualTo(0);
		assertThat(sizeData.getTotalPlantedArea()).isEqualTo(0);
	}
}
