package hu.unideb.inf.rft.neuban.web.controllers;

import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public abstract class AbstractControllerTest {

	protected MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controllerUnderTest()).build();
	}

	protected abstract Object[] controllerUnderTest();
}
