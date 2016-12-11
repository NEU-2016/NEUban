package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest extends AbstractControllerTest {

	private static final String REQUEST_URL = "/register";
	private static final String VIEW_NAME = "register";
	private static final String INDEX_VIEW = "index";

	private static final String USER_USERNAME_FIELD_NAME = "userName";
	private static final String USER_PASSWORD_FIELD_NAME = "password";
	private static final String USER_PASSWORDCONFIRMATION_FIELD_NAME = "passwordConfirmation";

	private static final String VALID_USERNAME = "username";
	private static final String VALID_PASSWORD = "password";
	private static final String VALID_PASSWORDCONFIRMATION = "password";

	private static final String INVALID_USERNAME = "us";
	private static final String INVALID_PASSWORD = "pas";
	private static final String INVALID_PASSWORDCONFIRMATION = "pas";

	@InjectMocks
	private RegisterController registerController;

	@Mock
	private UserService userService;

	@Mock
	private Validator validator;

	@Mock
	private Errors errors;

	@Override
	protected Object[] controllerUnderTest() {
		return new Object[]{this.registerController};
	}

	@Test
	public void loadRegisterViewShouldRenderRegisterView() throws Exception {
		this.mockMvc.perform(get(REQUEST_URL))
				.andExpect(status().isOk())
				.andExpect(view().name(VIEW_NAME))
				.andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX));
	}

	@Test
	public void userRegisterShouldRenderIndexViewIfUserAttributeIsValid() throws Exception {
		doNothing().when(validator).validate(
				UserDto
						.builder()
						.userName(VALID_USERNAME)
						.password(VALID_PASSWORD)
						.passwordConfirmation(VALID_PASSWORDCONFIRMATION)
						.build(),
				errors
		);
		this.mockMvc.perform(post(REQUEST_URL)
				.param(USER_USERNAME_FIELD_NAME, VALID_USERNAME)
				.param(USER_PASSWORD_FIELD_NAME, VALID_PASSWORD)
				.param(USER_PASSWORDCONFIRMATION_FIELD_NAME, VALID_PASSWORDCONFIRMATION)
		)
				.andExpect(status().isOk())
				.andExpect(view().name(INDEX_VIEW))
				.andExpect(forwardedUrl(VIEW_PREFIX + INDEX_VIEW + VIEW_SUFFIX))
				.andExpect(model().hasNoErrors());
	}

	//TODO fix tests
	/*
	@Test
	public void userRegisterShouldRenderRegisterViewWithUsernameErrorIfUserAttributeHasInvalidUsername() throws Exception {
		testWithIncorrectCredentialsAndErrors(INVALID_USERNAME, VALID_PASSWORD, VALID_PASSWORDCONFIRMATION, USER_USERNAME_FIELD_NAME);
	}

	@Test
	public void userRegisterShouldRenderRegisterViewWithPasswordErrorIfUserAttributeHasInvalidPassword() throws Exception {
		testWithIncorrectCredentialsAndErrors(VALID_USERNAME, INVALID_PASSWORD, VALID_PASSWORDCONFIRMATION, USER_PASSWORD_FIELD_NAME);
	}

	@Test
	public void userRegisterShouldRenderRegisterViewWithPasswordConfirmationErrorIfUserAttributeHasInvalidPasswordConfirmation() throws Exception {
		testWithIncorrectCredentialsAndErrors(VALID_USERNAME, VALID_PASSWORD, INVALID_PASSWORDCONFIRMATION, USER_PASSWORDCONFIRMATION_FIELD_NAME);
	}

	private void testWithIncorrectCredentialsAndErrors(
			String username,
			String password,
			String passwordConfirmation,
			String expectedErrorField
	) throws Exception {
		doNothing().when(validator).validate(
				UserDto
						.builder()
						.userName(username)
						.password(password)
						.passwordConfirmation(passwordConfirmation)
						.build(),
				errors
		);

		FieldError fieldError = mock(FieldError.class);

		given(errors.getAllErrors()).willReturn(Collections.singletonList(fieldError));
		given(fieldError.getField()).willReturn(expectedErrorField);

		this.mockMvc.perform(post(REQUEST_URL)
				.param(USER_USERNAME_FIELD_NAME, username)
				.param(USER_PASSWORD_FIELD_NAME, password)
				.param(USER_PASSWORDCONFIRMATION_FIELD_NAME, passwordConfirmation)
		)
				.andExpect(status().isOk())
				.andExpect(view().name(VIEW_NAME))
				.andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX))
				.andExpect(model().attributeExists(expectedErrorField));
	}
	*/
}
