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
	public void  createLocationData() {
		
		locationData.setLatitude("52°48'45.68\"");
		locationData.setLongitude("25°58'35.63\"");
		
		assertThat(locationData.getLatitude()).isEqualTo("52°48'45.68\"");
		assertThat(locationData.getLongitude()).isEqualTo("25°58'35.63\"");
	}
	
	@Test
	public void createLocationDataWithoutValues() {
		assertThat(locationData.getLatitude()).isNull();
		assertThat(locationData.getLongitude()).isNull();
	}
	
	@Test
	public void setLatitude() {
		locationData.setLatitude("52°48'45.68\"");
		assertThat(locationData.getLatitude()).isEqualTo("52°48'45.68\"");
	}

	@Test(expected = NullPointerException.class)
	public void setNullLatitude() {
		locationData.setLatitude(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void setInvalidLatitude() {
		locationData.setLatitude("");
	}

	@Test
	public void setLongitude() {
		locationData.setLongitude("25°58'35.63\"");
		assertThat(locationData.getLongitude()).isEqualTo("25°58'35.63\"");
	}
	
	
	@Test(expected = NullPointerException.class)
	public void setNullLongitude() {
		locationData.setLongitude(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void setInvalidLongitude() {
		locationData.setLongitude("");
	}
	
	
}
