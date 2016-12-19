package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class BoardControllerTest extends AbstractControllerTest {

	private static final String EXISTING_PRINCIPAL_USERNAME = "admin";
	private static final String NON_EXISTING_PRINCIPAL_USERNAME = "admin_but_this_time_it_does_not_exist_haha";

	private static final Long VALID_BOARD_ID = 1L;
	private static final Long INVALID_BOARD_ID = 667L;

	private static final String BOARD_URL = "/secure/board";
	private static final String VALID_BOARD_URL = BOARD_URL + "/" + String.valueOf(VALID_BOARD_ID);

	private static final String BOARD_VIEW_NAME = "secure/board";

	private static final String BOARD_ID_REQUEST_PARAM_NAME = "boardId";

	private static final String USERNAME_MODEL_OBJECT_NAME = "username";
	private static final String BOARD_MODEL_OBJECT_NAME = "board";

	private static final String PRINCIPAL_ERROR_MESSAGE_MODEL_OBJECT = "NonExistentPrincipalUserException: Non-existent logged in user :" + NON_EXISTING_PRINCIPAL_USERNAME;

	@InjectMocks
	private BoardController boardController;

	@Mock
	private Principal principal;

	@Mock
	private UserService userService;

	@Mock
	private BoardService boardService;

	@Override
	protected Object[] controllerUnderTest() {
		return new Object[]{this.boardController};
	}

	@Test
	public void loadBoardViewShouldRenderBoardViewIfBoardIdAndPrincipalAreValid() throws Exception {
		when(principal.getName()).thenReturn(EXISTING_PRINCIPAL_USERNAME);
		when(userService.getByUserName(EXISTING_PRINCIPAL_USERNAME)).thenReturn(Optional.of(UserDto.builder().userName(EXISTING_PRINCIPAL_USERNAME).build()));
		when(boardService.get(VALID_BOARD_ID)).thenReturn(Optional.of(BoardDto.builder().build()));
		this.mockMvc.perform(
				get(VALID_BOARD_URL)
						.principal(principal)
						.param(
								BOARD_ID_REQUEST_PARAM_NAME, String.valueOf(VALID_BOARD_ID)
						))
				.andExpect(status().isOk())
				.andExpect(view().name(BOARD_VIEW_NAME))
				.andExpect(forwardedUrl(VIEW_PREFIX + BOARD_VIEW_NAME + VIEW_SUFFIX))
				.andExpect(model().attribute(USERNAME_MODEL_OBJECT_NAME, EXISTING_PRINCIPAL_USERNAME))
				.andExpect(model().attribute(BOARD_MODEL_OBJECT_NAME, BoardDto.builder().build()));
	}

	@Test
	public void loadBoardViewShouldRenderErrorViewIfPrincipalIsInvalid() throws Exception {
		when(principal.getName()).thenReturn(NON_EXISTING_PRINCIPAL_USERNAME);
		when(userService.getByUserName(NON_EXISTING_PRINCIPAL_USERNAME)).thenReturn(Optional.empty());
		this.mockMvc.perform(
				get(VALID_BOARD_URL)
						.principal(principal)
						.param(
								BOARD_ID_REQUEST_PARAM_NAME, String.valueOf(VALID_BOARD_ID)
						))
				.andExpect(status().isOk())
				.andExpect(view().name(ERROR_VIEW))
				.andExpect(model().attribute(ERROR_MESSAGE_MODEL_OBJECT_NAME, PRINCIPAL_ERROR_MESSAGE_MODEL_OBJECT));
	}
}
