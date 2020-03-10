package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import lombok.Getter;


public class CropDataTest {

	private CropData cropData;
	
	@Before
	public void init() {
		 cropData = CropData.builder().build();
	}
	
	@Test
	public void createCropData() {
		Date sowedDate = new Date(117, 8, 26);
		Date emergenceDate = new Date(117, 9, 10);
		Date harvestDate = new Date(117, 1, 15);
		
		cropData.setEmergenceDate(emergenceDate);
		cropData.setHarvestDate(harvestDate);
		cropData.setSowedDate(sowedDate);
		
		assertThat(cropData.getEmergenceDate()).isEqualTo(emergenceDate);
		assertThat(cropData.getHarvestDate()).isEqualTo(harvestDate);
		assertThat(cropData.getSowedDate()).isEqualTo(sowedDate);
	}
	
	@Test
	public void createCropDataWithoutValues() {
		assertThat(cropData.getEmergenceDate()).isNull();
		assertThat(cropData.getHarvestDate()).isNull();
		assertThat(cropData.getSowedDate()).isNull();
	}

}
