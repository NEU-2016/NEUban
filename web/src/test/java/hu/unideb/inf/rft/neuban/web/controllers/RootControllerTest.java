package hu.unideb.inf.rft.neuban.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(MockitoJUnitRunner.class)
public class RootControllerTest extends AbstractControllerTest {

	private static final String REQUEST_URL_ROOT = "/";
	private static final String REDIRECT_URL_NEUBAN = "/neuban";
	
	@InjectMocks
	private RootController rootController;

	@Override
	protected Object[] controllerUnderTest() {
		return new Object[] { this.rootController };
	}
	
	@Test
	public void redirectToNeubanPathShouldRedirectToNeubanPath() throws Exception {
		this.mockMvc.perform(get(REQUEST_URL_ROOT))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl(REDIRECT_URL_NEUBAN));
	}

}
