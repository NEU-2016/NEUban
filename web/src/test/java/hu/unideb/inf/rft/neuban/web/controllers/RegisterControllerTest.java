package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.web.mail.NotificationService;
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

	private static final String USER_DTO_MODEL_OBJECT_NAME = "userDto";

	private static final String EXPECTED_ERROR_MESSAGE_MODEL_OBJECT = "InvalidRegistrationException: Invalid registrational data!";

	private static final String USER_USERNAME_FIELD_NAME = "userName";
	private static final String USER_EMAIL_FIELD_NAME = "email";
	private static final String USER_PASSWORD_FIELD_NAME = "password";
	private static final String USER_PASSWORDCONFIRMATION_FIELD_NAME = "passwordConfirmation";

	private static final String VALID_USERNAME = "username";
	private static final String VALID_EMAIL = "validemail@gmail.com";
	private static final String VALID_PASSWORD = "password";
	private static final String VALID_PASSWORDCONFIRMATION = "password";

	private static final String INVALID_USERNAME = "us";
	private static final String INVALID_EMAIL = "invalidemail_at_gmail_dot_com";
	private static final String INVALID_PASSWORD = "pas";
	private static final String INVALID_PASSWORDCONFIRMATION = "pas";

	@InjectMocks
	private RegisterController registerController;

	@Mock
	private UserService userService;

	@Mock
	private NotificationService notificationService;

	@Mock
	private Validator validator;

	@Mock
	private Errors errors;

	@Override
	protected Object[] controllerUnderTest() {
		return new Object[]{this.registerController};
	}

	@Test
	public void loadRegisterViewShouldRenderRegisterViewWithUserDtoModelObject() throws Exception {
		this.mockMvc.perform(get(REQUEST_URL))
				.andExpect(status().isOk())
				.andExpect(view().name(VIEW_NAME))
				.andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX))
				.andExpect(model().attributeExists(USER_DTO_MODEL_OBJECT_NAME));
	}

	@Test
	public void userRegisterShouldRenderIndexViewIfUserAttributeIsValid() throws Exception {
		doNothing().when(validator).validate(
				UserDto
						.builder()
						.userName(VALID_USERNAME)
						.email(VALID_EMAIL)
						.password(VALID_PASSWORD)
						.passwordConfirmation(VALID_PASSWORDCONFIRMATION)
						.build(),
				errors
		);
		this.mockMvc.perform(post(REQUEST_URL)
				.param(USER_USERNAME_FIELD_NAME, VALID_USERNAME)
				.param(USER_EMAIL_FIELD_NAME, VALID_EMAIL)
				.param(USER_PASSWORD_FIELD_NAME, VALID_PASSWORD)
				.param(USER_PASSWORDCONFIRMATION_FIELD_NAME, VALID_PASSWORDCONFIRMATION)
		)
				.andExpect(status().isOk())
				.andExpect(view().name(INDEX_VIEW))
				.andExpect(forwardedUrl(VIEW_PREFIX + INDEX_VIEW + VIEW_SUFFIX));
	}

	@Test
	public void userRegisterShouldRenderErrorViewWithInvalidUsername() throws Exception {
		testWithIncorrectCredentials(INVALID_USERNAME, VALID_EMAIL, VALID_PASSWORD, VALID_PASSWORDCONFIRMATION);
	}

	@Test
	public void userRegisterShouldRenderErrorViewWithInvalidEmail() throws Exception {
		testWithIncorrectCredentials(VALID_USERNAME, INVALID_EMAIL, VALID_PASSWORD, VALID_PASSWORDCONFIRMATION);
	}

	@Test
	public void userRegisterShouldRenderErrorViewWithInvalidPassword() throws Exception {
		testWithIncorrectCredentials(VALID_USERNAME, VALID_EMAIL, INVALID_PASSWORD, VALID_PASSWORDCONFIRMATION);
	}

	@Test
	public void userRegisterShouldRenderErrorViewWithInvalidPasswordConfirmation() throws Exception {
		testWithIncorrectCredentials(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD, INVALID_PASSWORDCONFIRMATION);
	}

	private void testWithIncorrectCredentials(
			String username,
			String email,
			String password,
			String passwordConfirmation
	) throws Exception {
		doNothing().when(validator).validate(
				UserDto
						.builder()
						.userName(username)
						.email(email)
						.password(password)
						.passwordConfirmation(passwordConfirmation)
						.build(),
				errors
		);

		this.mockMvc.perform(post(REQUEST_URL)
				.param(USER_USERNAME_FIELD_NAME, username)
				.param(USER_EMAIL_FIELD_NAME, email)
				.param(USER_PASSWORD_FIELD_NAME, password)
				.param(USER_PASSWORDCONFIRMATION_FIELD_NAME, passwordConfirmation)
		)
				.andExpect(status().isOk())
				.andExpect(view().name(ERROR_VIEW))
				.andExpect(model().attribute(ERROR_MESSAGE_MODEL_OBJECT_NAME, EXPECTED_ERROR_MESSAGE_MODEL_OBJECT));
	}
}
