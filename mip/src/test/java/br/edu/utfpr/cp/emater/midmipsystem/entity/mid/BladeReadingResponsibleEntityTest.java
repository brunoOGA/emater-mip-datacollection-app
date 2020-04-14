package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;

public class BladeReadingResponsibleEntityTest {
	
	private BladeReadingResponsibleEntity bladeReadingResponsibleEntity;
	
	@Before
	public void init() {
		bladeReadingResponsibleEntity = BladeReadingResponsibleEntity.builder().name("").build();
	}
	 
	@Test
	public void BladeReadingResponsibleEntity()  {
		City city = createCity();
		bladeReadingResponsibleEntity.setId(1l);
		bladeReadingResponsibleEntity.setName("Emater-PB.");
		bladeReadingResponsibleEntity.setCity(city);
		
		assertThat(bladeReadingResponsibleEntity.getId()).isEqualTo(1l);
		assertThat(bladeReadingResponsibleEntity.getName()).isEqualTo("Emater-pb.");
		assertThat(bladeReadingResponsibleEntity.getCity()).isEqualTo(city);
	}
	
	@Test
	public void getName() {
		bladeReadingResponsibleEntity.setName("Emater-PB.");
		
		assertThat(bladeReadingResponsibleEntity.getName()).isEqualTo("Emater-pb.");
	}
	
	@Test(expected = NullPointerException.class)
	public void getNullName() {
		bladeReadingResponsibleEntity.setName(null);
	}
	
	@Test
	public void getCityName() {
		City city = createCity();
		bladeReadingResponsibleEntity.setCity(city);
		
		assertThat(bladeReadingResponsibleEntity.getCityName()).isEqualTo("Itapejara D´oeste");
	}
	
	@Test
	public void itShouldGetNullNameWhenCityIsNull() {
		bladeReadingResponsibleEntity.setCity(null);
		
		assertThat(bladeReadingResponsibleEntity.getCityName()).isNull();
	}
	
	@Test
	public void getCityId() {
		City city = createCity();
		city.setId(1l);
		bladeReadingResponsibleEntity.setCity(city);
		
		assertThat(bladeReadingResponsibleEntity.getCityId()).isEqualTo(1);
	}
	
	@Test
	public void itShouldGetNullIdWhenCityIsNull() {
		bladeReadingResponsibleEntity.setCity(null);
		
		assertThat(bladeReadingResponsibleEntity.getCityId()).isNull();
	}
	
	@Test
	public void getIdAsString() {
		bladeReadingResponsibleEntity.setId(1l);
		
		assertThat(bladeReadingResponsibleEntity.getIdAsString()).isEqualTo("1");
	}
	
	@Test
	public void itShouldGetNullIdAsStringWhenIdIsNull() {
		bladeReadingResponsibleEntity.setId(null);
		
		assertThat(bladeReadingResponsibleEntity.getIdAsString()).isNull();
	}
	
	public City createCity() {
		return City.builder().name("Itapejara D´Oeste").state(State.PR).build();
	}
}
