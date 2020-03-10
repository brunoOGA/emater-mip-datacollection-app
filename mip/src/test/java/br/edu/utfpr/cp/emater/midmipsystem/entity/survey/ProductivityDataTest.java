package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ProductivityDataTest {
	private ProductivityData productivityData;
	
	@Before
	public void init() {
		productivityData = ProductivityData.builder().build();
	}

	@Test
	public void createProductivityDataTest() {
		productivityData.setProductivityFarmer(4215d);
		productivityData.setProductivityField(4636d);
		productivityData.setSeparatedWeight(true);
		
		assertThat(productivityData.getProductivityFarmer()).isEqualTo(4215d);
		assertThat(productivityData.getProductivityField()).isEqualTo(4636d);
		assertThat(productivityData.isSeparatedWeight()).isTrue();
	}
	
	@Test
	public void createProductivityDataWithoutValues() {
		assertThat(productivityData.getProductivityFarmer()).isEqualTo(0);
		assertThat(productivityData.getProductivityField()).isEqualTo(0);
		assertThat(productivityData.isSeparatedWeight()).isFalse();
	}
	
}
