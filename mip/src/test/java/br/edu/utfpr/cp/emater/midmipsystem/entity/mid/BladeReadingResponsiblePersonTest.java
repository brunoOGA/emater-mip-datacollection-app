package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import javax.validation.*;

import org.junit.Before;
import org.junit.Test;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;

public class BladeReadingResponsiblePersonTest {

	private BladeReadingResponsiblePerson bladeReadingResponsiblePerson;
	
	@Before
	public void init() {
		bladeReadingResponsiblePerson = BladeReadingResponsiblePerson.builder().name("").build();
	}
	
	@Test
	public void createBladeReadingResponsiblePerson() {
		BladeReadingResponsibleEntity bladeReadingResponsibleEntity = createBladeReadingResponsibleEntity();
		bladeReadingResponsiblePerson.setId(1l);
		bladeReadingResponsiblePerson.setName("Gustavo M. de Oliveira");
		bladeReadingResponsiblePerson.setEntity(bladeReadingResponsibleEntity);
		
		assertThat(bladeReadingResponsiblePerson.getId()).isEqualTo(1l);
		assertThat(bladeReadingResponsiblePerson.getName()).isEqualTo("Gustavo M. De Oliveira");
		assertThat(bladeReadingResponsiblePerson.getEntity()).isEqualTo(bladeReadingResponsibleEntity);
	}
	
	@Test
	public void getEntityName() {
		BladeReadingResponsibleEntity bladeReadingResponsibleEntity = createBladeReadingResponsibleEntity();
		bladeReadingResponsiblePerson.setEntity(bladeReadingResponsibleEntity);
		
		assertThat(bladeReadingResponsiblePerson.getEntityName()).isEqualTo("Emater-pb.");
	}
	
	@Test
	public void itShouldGetNullEntityNameWhenEntityIsNull() {
		bladeReadingResponsiblePerson.setEntity(null);
		
		assertThat(bladeReadingResponsiblePerson.getEntityName()).isNull();
	}
	
	@Test
	public void getEntityId() {
		BladeReadingResponsibleEntity bladeReadingResponsibleEntity = createBladeReadingResponsibleEntity();
		bladeReadingResponsiblePerson.setEntity(bladeReadingResponsibleEntity);
		
		assertThat(bladeReadingResponsiblePerson.getEntityId()).isEqualTo(1l);
	}
	
	@Test
	public void itShouldGetNullEntityIdWhenEntityIsNull() {
		bladeReadingResponsiblePerson.setEntity(null);
		
		assertThat(bladeReadingResponsiblePerson.getEntityId()).isNull();
	}
	
	@Test
	public void getEntityCityName() {
		BladeReadingResponsibleEntity bladeReadingResponsibleEntity = createBladeReadingResponsibleEntity();
		bladeReadingResponsiblePerson.setEntity(bladeReadingResponsibleEntity);
		
		assertThat(bladeReadingResponsiblePerson.getEntityCityName()).isEqualTo("Itapejara D´oeste");
	}
	
	@Test
	public void itShouldGetNullEntityCityNameWhenEntityIsNull() {
		bladeReadingResponsiblePerson.setEntity(null);
		
		assertThat(bladeReadingResponsiblePerson.getEntityCityName()).isNull();
	}
	
	@Test
	public void getEntityCityId() {
		BladeReadingResponsibleEntity bladeReadingResponsibleEntity = createBladeReadingResponsibleEntity();
		bladeReadingResponsiblePerson.setEntity(bladeReadingResponsibleEntity);
		
		assertThat(bladeReadingResponsiblePerson.getEntityCityId()).isEqualTo(1l);
	}
	
	@Test
	public void itShouldGetNullEntityCityIdWhenEntityIsNull() {
		bladeReadingResponsiblePerson.setEntity(null);
		
		assertThat(bladeReadingResponsiblePerson.getEntityCityId()).isNull();
	}
	
	public BladeReadingResponsibleEntity createBladeReadingResponsibleEntity() {
		City city = City.builder().name("Itapejara D´Oeste").state(State.PR).build();
		city.setId(1l);
		return BladeReadingResponsibleEntity.builder().id(1l).name("Emater-PB.").city(city).build();
	}
}
