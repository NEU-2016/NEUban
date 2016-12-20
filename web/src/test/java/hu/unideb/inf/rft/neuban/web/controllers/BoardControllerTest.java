package hu.unideb.inf.rft.neuban.web.controllers;

import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.CardService;
import hu.unideb.inf.rft.neuban.service.interfaces.ColumnService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.Principal;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class BoardControllerTest extends AbstractControllerTest {

	private static final String EXISTING_PRINCIPAL_USERNAME = "admin";
	private static final String NON_EXISTING_PRINCIPAL_USERNAME = "admin_but_this_time_it_does_not_exist_haha";

	private static final Long VALID_BOARD_ID = 1L;
	private static final Long INVALID_BOARD_ID = 667L;
	private static final Long VALID_COLUMN_ID = 1L;
	private static final String VALID_COLUMN_TITLE = "Valid column title";
	private static final String VALID_CARD_TITLE = "Valid card title";

	private static final String BOARD_URL = "/secure/board";
	private static final String VALID_BOARD_URL = BOARD_URL + "/" + String.valueOf(VALID_BOARD_ID);
	private static final String INVALID_BOARD_URL = BOARD_URL + "/" + String.valueOf(INVALID_BOARD_ID);

	private static final String CREATE_COLUMN_URL = BOARD_URL + "/" + String.valueOf(VALID_BOARD_ID) + "/createcolumn";
	private static final String REMOVE_COLUMN_URL = BOARD_URL + "/" + String.valueOf(VALID_BOARD_ID) + "/removecolumn/" + String.valueOf(VALID_COLUMN_ID);
	private static final String ADD_CARD_URL = BOARD_URL + "/" + String.valueOf(VALID_BOARD_ID) + "/" + String.valueOf(VALID_COLUMN_ID) + "/addcard";

	private static final String BOARD_VIEW = "secure/board";
	private static final String REDIRECT_TO_BOARD_VIEW = "redirect:/" + BOARD_VIEW + "/" + String.valueOf(VALID_BOARD_ID);

	private static final String BOARD_ID_REQUEST_PARAM_NAME = "boardId";
	private static final String COLUMN_ID_REQUEST_PARAM_NAME = "columnId";
	private static final String COLUMN_TITLE_REQUEST_PARAM_NAME = "columnTitle";
	private static final String CARD_TITLE_REQUEST_PARAM_NAME = "cardTitle";

	private static final String USERNAME_MODEL_OBJECT_NAME = "username";
	private static final String BOARD_MODEL_OBJECT_NAME = "board";

	private static final String PRINCIPAL_ERROR_MESSAGE_MODEL_OBJECT = "NonExistentPrincipalUserException: Non-existent logged in user :" + NON_EXISTING_PRINCIPAL_USERNAME;
	private static final String BOARD_NOT_FOUND_ERROR_MESSAGE_MODEL_OBJECT = "BoardNotFoundException: Board not found: " + INVALID_BOARD_ID;

	@InjectMocks
	private BoardController boardController;

	@Mock
	private Principal principal;

	@Mock
	private UserService userService;

	@Mock
	private BoardService boardService;

	@Mock
	private ColumnService columnService;

	@Mock
	private CardService cardService;

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
				.andExpect(view().name(BOARD_VIEW))
				.andExpect(forwardedUrl(VIEW_PREFIX + BOARD_VIEW + VIEW_SUFFIX))
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

	@Test
	public void loadBoardViewShouldRenderErrorViewIfBoardIdIsInvalid() throws Exception {
		when(principal.getName()).thenReturn(EXISTING_PRINCIPAL_USERNAME);
		when(userService.getByUserName(EXISTING_PRINCIPAL_USERNAME)).thenReturn(Optional.of(UserDto.builder().userName(EXISTING_PRINCIPAL_USERNAME).build()));
		when(boardService.get(INVALID_BOARD_ID)).thenReturn(Optional.empty());
		this.mockMvc.perform(
				get(INVALID_BOARD_URL)
						.principal(principal)
						.param(
								BOARD_ID_REQUEST_PARAM_NAME, String.valueOf(INVALID_BOARD_ID)
						))
				.andExpect(status().isOk())
				.andExpect(view().name(ERROR_VIEW))
				.andExpect(model().attribute(ERROR_MESSAGE_MODEL_OBJECT_NAME, BOARD_NOT_FOUND_ERROR_MESSAGE_MODEL_OBJECT));
	}

	@Test
	public void createColumnShouldRenderBoardViewIfBoardIdAndColumnTitleAreValid() throws Exception {
		doNothing().when(columnService).save(VALID_BOARD_ID, ColumnDto.builder().title(VALID_COLUMN_TITLE).build());
		this.mockMvc.perform(
				post(CREATE_COLUMN_URL)
						.param(BOARD_ID_REQUEST_PARAM_NAME, String.valueOf(VALID_BOARD_ID))
						.param(COLUMN_TITLE_REQUEST_PARAM_NAME, VALID_COLUMN_TITLE))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name(REDIRECT_TO_BOARD_VIEW));
	}

	// TODO Invalid tests

	@Test
	public void removeColumnShouldRenderBoardViewIfBoardIdAndColumnIdAreValid() throws Exception {
		doNothing().when(columnService).remove(VALID_COLUMN_ID);
		this.mockMvc.perform(
				delete(REMOVE_COLUMN_URL)
						.param(BOARD_ID_REQUEST_PARAM_NAME, String.valueOf(VALID_BOARD_ID))
						.param(COLUMN_ID_REQUEST_PARAM_NAME, String.valueOf(VALID_COLUMN_ID)))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name(REDIRECT_TO_BOARD_VIEW));
	}

	// TODO Invalid tests

	@Test
	public void addCardShouldRenderBoardViewIfBoardIdAndColumnIdAndCardTitleAreValid() throws Exception {
		doNothing().when(cardService).save(VALID_COLUMN_ID, CardDto.builder().title(VALID_CARD_TITLE).build());
		this.mockMvc.perform(
				post(ADD_CARD_URL)
						.param(BOARD_ID_REQUEST_PARAM_NAME, String.valueOf(VALID_BOARD_ID))
						.param(COLUMN_TITLE_REQUEST_PARAM_NAME, VALID_COLUMN_TITLE)
						.param(CARD_TITLE_REQUEST_PARAM_NAME, VALID_CARD_TITLE))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name(REDIRECT_TO_BOARD_VIEW));
	}

	// TODO Invalid tests

}
