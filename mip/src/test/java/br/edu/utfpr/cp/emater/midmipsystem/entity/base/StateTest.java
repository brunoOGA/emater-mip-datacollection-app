package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StateTest {

	@Test 
	public void stateName() {
		assertThat(State.PR.getName()).isEqualTo("Paraná");
	}

}
