package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class LocationDataTest {
	
	private LocationData locationData;
	
	@Before
	public void init() {
		locationData = LocationData.builder().build();
	}
	
	
	@Test
	public void  test1() {
		
		locationData.setLatitude("2020");
		locationData.setLongitude("1010");
		
		assertThat(locationData.getLatitude()).isEqualTo("2020");
		assertThat(locationData.getLongitude()).isEqualTo("1010");
	}
	
	@Test
	public void test2() {
		assertThat(locationData.getLatitude()).isNull();
		assertThat(locationData.getLongitude()).isNull();
	}


	
}
