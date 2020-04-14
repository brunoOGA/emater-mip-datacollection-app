package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.*;

import org.junit.Before;
import org.junit.Test;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;

public class MIDSampleSporeCollectorOccurrenceTest {

	private MIDSampleSporeCollectorOccurrence mIDSampleSporeCollectorOccurrence;
	
	@Before
	public void init() {
		mIDSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder().build();
	}
	
	@Test
	public void createMIDSampleSporeCollectorOccurrence() {
		BladeReadingResponsiblePerson bladeReadingResponsiblePerson = createBladeReadingResponsiblePerson();
		Date bladeReadingDate = new Date(118, 0, 16);
		mIDSampleSporeCollectorOccurrence.setBladeReadingDate(bladeReadingDate);
		mIDSampleSporeCollectorOccurrence.setBladeInstalledPreCold(true);
		mIDSampleSporeCollectorOccurrence.setBladeReadingResponsiblePerson(bladeReadingResponsiblePerson);
		mIDSampleSporeCollectorOccurrence.setBladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.COM_ESPOROS_SEM_TESTAR_VIABILIDADE);
	
		assertThat(mIDSampleSporeCollectorOccurrence.getBladeReadingDate()).isEqualTo(bladeReadingDate);
		assertThat(mIDSampleSporeCollectorOccurrence.isBladeInstalledPreCold()).isTrue();
		assertThat(mIDSampleSporeCollectorOccurrence.getBladeReadingResponsiblePerson()).isEqualTo(bladeReadingResponsiblePerson);
		assertThat(mIDSampleSporeCollectorOccurrence.getBladeReadingRustResultCollector()).isEqualTo(AsiaticRustTypesSporeCollector.COM_ESPOROS_SEM_TESTAR_VIABILIDADE);
	}
	
	@Test
	public void isBladeInstalledPreColdTrue() {
		mIDSampleSporeCollectorOccurrence.setBladeInstalledPreCold(true);
		
		assertThat(mIDSampleSporeCollectorOccurrence.isBladeInstalledPreCold()).isTrue();
	}
	
	@Test
	public void isBladeInstalledPreColdFalse() {
		mIDSampleSporeCollectorOccurrence.setBladeInstalledPreCold(false);
		
		assertThat(mIDSampleSporeCollectorOccurrence.isBladeInstalledPreCold()).isFalse();
	}
	
	@Test
	public void getBladeReadingDate() {
		Date bladeReadingDate = new Date(118, 0, 16);
		mIDSampleSporeCollectorOccurrence.setBladeReadingDate(bladeReadingDate);
		
		assertThat(mIDSampleSporeCollectorOccurrence.getBladeReadingDate()).isEqualTo(bladeReadingDate);
	}
	
	@Test
	public void getNullBladeReadingDate() {
		mIDSampleSporeCollectorOccurrence.setBladeReadingDate(null);
		
		assertThat(mIDSampleSporeCollectorOccurrence.getBladeReadingDate()).isNull();
	}
	
	@Test
	public void getBladeReadingResponsiblePerson() {
		BladeReadingResponsiblePerson bladeReadingResponsiblePerson = createBladeReadingResponsiblePerson();
		mIDSampleSporeCollectorOccurrence.setBladeReadingResponsiblePerson(bladeReadingResponsiblePerson);
		
		assertThat(mIDSampleSporeCollectorOccurrence.getBladeReadingResponsiblePerson()).isEqualTo(bladeReadingResponsiblePerson);
	}
	
	@Test
	public void getNullBladeReadingResponsiblePerson() {
		mIDSampleSporeCollectorOccurrence.setBladeReadingResponsiblePerson(null);
		
		assertThat(mIDSampleSporeCollectorOccurrence.getBladeReadingResponsiblePerson()).isNull();
	}
	
	@Test
	public void getBladeReadingRustResultCollector() {
		mIDSampleSporeCollectorOccurrence.setBladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.COM_ESPOROS_SEM_TESTAR_VIABILIDADE);
		
		assertThat(mIDSampleSporeCollectorOccurrence.getBladeReadingRustResultCollector()).isEqualTo(AsiaticRustTypesSporeCollector.COM_ESPOROS_SEM_TESTAR_VIABILIDADE);
	}
	
	@Test
	public void getNullBladeReadingRustResultCollector() {
		mIDSampleSporeCollectorOccurrence.setBladeReadingRustResultCollector(null);
		
		assertThat(mIDSampleSporeCollectorOccurrence.getBladeReadingRustResultCollector()).isNull();
	}
	
	public BladeReadingResponsiblePerson createBladeReadingResponsiblePerson() {
		City city = City.builder().name("Itapejara D´Oeste").state(State.PR).build();
		city.setId(1l);
		BladeReadingResponsibleEntity entity = BladeReadingResponsibleEntity.builder().id(1l).name("Emater-PB.").city(city).build();
		return BladeReadingResponsiblePerson.builder().id(1l).name("Gustavo M. de Oliveira").entity(entity).build();
	}
	
}
