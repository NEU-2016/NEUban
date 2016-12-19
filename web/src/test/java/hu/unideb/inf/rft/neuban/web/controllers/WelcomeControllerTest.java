package hu.unideb.inf.rft.neuban.web.controllers;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.Principal;
import java.util.Optional;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class WelcomeControllerTest extends AbstractControllerTest {

	private static final String REQUEST_URL = "/secure/welcome";
	private static final String VIEW_NAME = "secure/welcome";

	private static final String USERNAME_MODEL_OBJECT_NAME = "username";
	private static final String BOARD_LIST_MODEL_OBJECT_NAME = "boardList";
	private static final String CARD_LIST_MODEL_OBJECT_NAME = "cardList";

	private static final String EXISTING_PRINCIPAL_USERNAME = "admin";
	private static final String NON_EXISTING_PRINCIPAL_USERNAME = "admin_but_this_time_it_does_not_exist_haha";

	private static final String EXPECTED_PRINCIPAL_ERROR_MESSAGE_MODEL_OBJECT = "NonExistentPrincipalUserException: Non-existent logged in user :" + NON_EXISTING_PRINCIPAL_USERNAME;

	@InjectMocks
	private WelcomeController welcomeController;

	@Mock
	private Principal principal;

	@Mock
	private UserService userService;

	@Mock
	private BoardService boardService;

	@Override
	protected Object[] controllerUnderTest() {
		return new Object[]{this.welcomeController};
	}

	@Test
	public void loadWelcomeViewShouldRenderWelcomeViewIfPrincipalExists() throws Exception {
		when(principal.getName()).thenReturn(EXISTING_PRINCIPAL_USERNAME);
		when(userService.getByUserName(EXISTING_PRINCIPAL_USERNAME)).thenReturn(Optional.of(UserDto.builder().id(1L).userName(EXISTING_PRINCIPAL_USERNAME).build()));
		when(boardService.getAllByUserId(anyLong())).thenReturn(Lists.newArrayList());
		this.mockMvc.perform(get(REQUEST_URL).principal(principal))
				.andExpect(status().isOk())
				.andExpect(view().name(VIEW_NAME))
				.andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX))
				.andExpect(model().attribute(USERNAME_MODEL_OBJECT_NAME, EXISTING_PRINCIPAL_USERNAME))
				.andExpect(model().attribute(BOARD_LIST_MODEL_OBJECT_NAME, Lists.newArrayList()))
				.andExpect(model().attribute(CARD_LIST_MODEL_OBJECT_NAME, Lists.newArrayList()));
	}

	@Test
	public void loadWelcomeViewShouldRenderErrorViewIfPrincipalDoesNotExists() throws Exception {
		when(principal.getName()).thenReturn(NON_EXISTING_PRINCIPAL_USERNAME);
		when(userService.getByUserName(NON_EXISTING_PRINCIPAL_USERNAME)).thenReturn(Optional.empty());
		this.mockMvc.perform(get(REQUEST_URL).principal(principal))
				.andExpect(status().isOk())
				.andExpect(view().name(ERROR_VIEW))
				.andExpect(model().attribute(ERROR_MESSAGE_MODEL_OBJECT_NAME, EXPECTED_PRINCIPAL_ERROR_MESSAGE_MODEL_OBJECT));
	}
}
