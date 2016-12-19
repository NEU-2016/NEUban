package hu.unideb.inf.rft.neuban.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest extends AbstractControllerTest {

	private static final String REQUEST_URL = "/login";
	private static final String VIEW_NAME = "login";

	private static final String LOGIN_ERROR_URL = "/login/error";
	private static final String EXPECTED_ERROR_MESSAGE_MODEL_OBJECT = "LoginFailureException: Failed to log in!";

	@InjectMocks
	private LoginController loginController;

	@Override
	protected Object[] controllerUnderTest() {
		return new Object[]{this.loginController};
	}

	@Test
	public void loadLoginViewShouldRenderLoginView() throws Exception {
		this.mockMvc.perform(get(REQUEST_URL))
				.andExpect(status().isOk())
				.andExpect(view().name(VIEW_NAME))
				.andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX));
	}

	@Test
	public void loginErrorShouldRenderErrorView() throws Exception {
		this.mockMvc.perform(get(LOGIN_ERROR_URL))
				.andExpect(status().isOk())
				.andExpect(view().name(ERROR_VIEW))
		.andExpect(model().attribute(ERROR_MESSAGE_MODEL_OBJECT_NAME, EXPECTED_ERROR_MESSAGE_MODEL_OBJECT));
	}
}
