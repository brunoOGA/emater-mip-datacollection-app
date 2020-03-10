package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class MIDDataTest {
	
	private MIDData mIDData;
	
	@Before
	public void init() {
		mIDData = MIDData.builder().build();
	}

	@Test
	public void  createMIDDataTest() {
		Date collectorInstallationDate = new Date(117, 10, 1);
		mIDData.setCollectorInstallationDate(collectorInstallationDate);
		mIDData.setSporeCollectorPresent(true);

		assertThat(mIDData.getCollectorInstallationDate()).isEqualTo(collectorInstallationDate);
		assertThat(mIDData.isSporeCollectorPresent()).isTrue();
	}
	
	@Test
	public void createMIDDataWithoutValues() {
		assertThat(mIDData.getCollectorInstallationDate()).isNull();
		assertThat(mIDData.isSporeCollectorPresent()).isFalse();
	}
}
