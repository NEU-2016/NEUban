package hu.unideb.inf.rft.neuban.service.handler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import hu.unideb.inf.rft.neuban.persistence.repositories.BoardRepository;
import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentBoardIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUserIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.RelationNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;

@RunWith(MockitoJUnitRunner.class)
public class BoardHandlerTest {

	private static final String BOARD_TITLE = "TestTitle";
	private static final Long EXPECTED_BOARD_ID = 1L;
	private static final Long NOT_EXPECTED_BOARD_ID = 2L;
	private static final Long USER_ID = 1L;
	private static final String USER_NAME = "admin";
	private static final String USER_PASSWORD = "admin";
	private static final String NON_EXISTENT_USER_NAME = "non-existent username";
	private static final Long NON_EXISTENT_USER_ID = 0L;
	private static final Long NON_EXISTENT_BOARD_ID = 0L;

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	@Mock
	private BoardRepository boardRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ModelMapper modelMapper;

	@Mock
	private BoardService boardService;

	@InjectMocks
	private BoardHandler boardHandler;

	@Mock
	private UserService userService;

	@Test(expected = IllegalArgumentException.class)
	public void removeUserFromBoardByUserIdAndByBoardIdShouldThrowIllegalArgumentExceptionWhenUserIdIsNull()
			throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException {

		this.boardHandler.removeUserFromBoardByUserIdAndByBoardId(null, EXPECTED_BOARD_ID);

	}

	@Test(expected = IllegalArgumentException.class)
	public void removeUserFromBoardByUserIdAndByBoardIdShouldThrowIllegalArgumentExceptionWhenBoardIdIsNull()
			throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException {

		this.boardHandler.removeUserFromBoardByUserIdAndByBoardId(USER_ID, null);

	}

	@Test
	public void removeUserFromBoardByUserIdAndByBoardIdShouldThrowNonExistentUserIdExceptionWhenUserNotExists()
			throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException {
		// Given
		given(this.userService.getById(USER_ID)).willReturn(null);
		expectedException.expect(NonExistentUserIdException.class);

		// When
		this.boardHandler.removeUserFromBoardByUserIdAndByBoardId(NON_EXISTENT_USER_ID, EXPECTED_BOARD_ID);
		// Then

		then(this.userService).should().getById(NON_EXISTENT_USER_ID);
	}

	@Test
	public void removeUserFromBoardByUserIdAndByBoardIdShouldThrowNoRelationFoundExceptionWhenUserHasNoBoards()
			throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException {

		// Given

		final BoardDto expectedBoardDto = BoardDto.builder().id(EXPECTED_BOARD_ID).title(BOARD_TITLE).columns(null)
				.build();
		final UserDto expectedUserDtoWithoutExpectedBoard = UserDto.builder().id(USER_ID).userName(USER_NAME)
				.password(USER_PASSWORD).boards(new ArrayList<>()).build();

		given(this.userService.getById(USER_ID)).willReturn(expectedUserDtoWithoutExpectedBoard);
		given(this.boardService.getById(EXPECTED_BOARD_ID)).willReturn(expectedBoardDto);

		expectedException.expect(RelationNotFoundException.class);

		// When
		this.boardHandler.removeUserFromBoardByUserIdAndByBoardId(USER_ID, EXPECTED_BOARD_ID);

		// Then
		then(this.userService).should().getById(USER_ID);
		then(this.boardService).should().getById(EXPECTED_BOARD_ID);
	}

	@Test
	public void removeUserFromBoardByUserIdAndByBoardIdShouldThrowNoRelationFoundExceptionWhenUserDoNotHaveExpectedBoard()
			throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException {

		// Given

		final BoardDto notExpectedBoardDto = BoardDto.builder().id(NOT_EXPECTED_BOARD_ID).title(BOARD_TITLE)
				.columns(null).build();
		final BoardDto expectedBoardDto = BoardDto.builder().id(EXPECTED_BOARD_ID).title(BOARD_TITLE).columns(null)
				.build();

		List<BoardDto> boards = Lists.newArrayList();
		boards.add(expectedBoardDto);
		final UserDto expectedUserDtoWithoutExpectedBoard = UserDto.builder().id(USER_ID).userName(USER_NAME)
				.password(USER_PASSWORD).boards(boards).build();

		given(this.userService.getById(USER_ID)).willReturn(expectedUserDtoWithoutExpectedBoard);
		given(this.boardService.getById(NOT_EXPECTED_BOARD_ID)).willReturn(notExpectedBoardDto);

		expectedException.expect(RelationNotFoundException.class);

		// When
		this.boardHandler.removeUserFromBoardByUserIdAndByBoardId(USER_ID, NOT_EXPECTED_BOARD_ID);

		// Then
		then(this.userService).should().getById(USER_ID);
		then(this.boardService).should().getById(NOT_EXPECTED_BOARD_ID);
	}

	@Test
	public void removeUserFromBoardByUserIdAndByBoardIdShouldThrowNonExistentBoardIdExceptionWhenBoardNotExists()
			throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException {

		// Given
		final UserDto expectedUserDto = UserDto.builder().id(USER_ID).userName(USER_NAME).password(USER_PASSWORD)
				.boards(new ArrayList<>()).build();

		given(this.userService.getById(USER_ID)).willReturn(expectedUserDto);
		given(this.boardService.getById(NON_EXISTENT_BOARD_ID)).willReturn(null);
		expectedException.expect(NonExistentBoardIdException.class);

		// When
		this.boardHandler.removeUserFromBoardByUserIdAndByBoardId(USER_ID, NON_EXISTENT_BOARD_ID);

		// Then
		then(this.boardService).should().getById(NON_EXISTENT_BOARD_ID);
	}

	@Test
	public void removeUserFromBoardByUserIdAndByBoardIdTest()
			throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException {

		// Given
		final BoardDto expectedUserBoard = BoardDto.builder().id(EXPECTED_BOARD_ID).title(BOARD_TITLE).columns(null)
				.build();
		List<BoardDto> boards = Lists.newArrayList();
		boards.add(expectedUserBoard);
		final UserDto expectedUserDtoBeforeRemove = UserDto.builder().id(USER_ID).userName(USER_NAME)
				.password(USER_PASSWORD).boards(boards).build();

		final UserDto expectedUserDtoAfterRemove = UserDto.builder().id(USER_ID).userName(USER_NAME)
				.password(USER_PASSWORD).boards(new ArrayList<>()).build();
		given(this.userService.getById(USER_ID)).willReturn(expectedUserDtoBeforeRemove, expectedUserDtoAfterRemove);
		given(this.boardService.getById(EXPECTED_BOARD_ID)).willReturn(expectedUserBoard);

		// When
		this.boardHandler.removeUserFromBoardByUserIdAndByBoardId(USER_ID, EXPECTED_BOARD_ID);
		final UserDto actualUserDto = this.userService.getById(USER_ID);

		// Then
		assertThat(actualUserDto, equalTo(expectedUserDtoAfterRemove));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addUserToBoardByUserIdAndByBoardIdShouldThrowIllegalArgumentExceptionWhenUserIdIsNull()
			throws NonExistentUserIdException, NonExistentBoardIdException {

		this.boardHandler.addUserToBoardByUserIdAndByBoardId(null, EXPECTED_BOARD_ID);

	}

	@Test(expected = IllegalArgumentException.class)
	public void addUserToBoardByUserIdAndByBoardIdShouldThrowIllegalArgumentExceptionWhenBoardIdIsNull()
			throws NonExistentUserIdException, NonExistentBoardIdException {

		this.boardHandler.addUserToBoardByUserIdAndByBoardId(USER_ID, null);

	}

	@Test
	public void addUserToBoardByUserIdAndByBoardIdShouldThrowNonExistentUserIdExceptionWhenUserNotExists()
			throws NonExistentUserIdException, NonExistentBoardIdException {
		// Given
		given(this.userService.getById(USER_ID)).willReturn(null);
		expectedException.expect(NonExistentUserIdException.class);

		// When
		this.boardHandler.addUserToBoardByUserIdAndByBoardId(NON_EXISTENT_USER_ID, EXPECTED_BOARD_ID);
		// Then

		then(this.userService).should().getById(NON_EXISTENT_USER_ID);
	}

	@Test
	public void addUserToBoardByUserIdAndByBoardIdShouldThrowNonExistentBoardIdExceptionWhenBoardNotExists()
			throws NonExistentUserIdException, NonExistentBoardIdException {

		// Given
		final UserDto expectedUserDto = UserDto.builder().id(USER_ID).userName(USER_NAME).password(USER_PASSWORD)
				.boards(new ArrayList<>()).build();

		given(this.userService.getById(USER_ID)).willReturn(expectedUserDto);
		given(this.boardService.getById(NON_EXISTENT_BOARD_ID)).willReturn(null);
		expectedException.expect(NonExistentBoardIdException.class);

		// When
		this.boardHandler.addUserToBoardByUserIdAndByBoardId(USER_ID, NON_EXISTENT_BOARD_ID);

		// Then
		then(this.boardService).should().getById(NON_EXISTENT_BOARD_ID);
	}

	@Test
	public void addUserToBoardByUserIdAndByBoardIdTest() throws NonExistentUserIdException, NonExistentBoardIdException {

		// Given

		final BoardDto expectedUserBoard = BoardDto.builder().id(EXPECTED_BOARD_ID).title(BOARD_TITLE).columns(null)
				.build();
		final UserDto expectedUserDtoBeforeAdd = UserDto.builder().id(USER_ID).userName(USER_NAME)
				.password(USER_PASSWORD).boards(new ArrayList<>()).build();

		List<BoardDto> boards = Lists.newArrayList();
		boards.add(expectedUserBoard);

		final UserDto expectedUserDtoAfterAdd = UserDto.builder().id(USER_ID).userName(USER_NAME)
				.password(USER_PASSWORD).boards(boards).build();

		given(this.userService.getById(USER_ID)).willReturn(expectedUserDtoBeforeAdd, expectedUserDtoAfterAdd);
		given(this.boardService.getById(EXPECTED_BOARD_ID)).willReturn(expectedUserBoard);

		// When
		this.boardHandler.addUserToBoardByUserIdAndByBoardId(USER_ID, EXPECTED_BOARD_ID);

		final UserDto actualUserDto = this.userService.getById(USER_ID);

		// Then
		assertThat(actualUserDto, notNullValue());
		assertThat(actualUserDto, equalTo(expectedUserDtoAfterAdd));

	}

	@Test(expected = IllegalArgumentException.class)
	public void createBoardByDefaultUserIdAndByTitleShouldThrowIllegalArgumentExceptionWhenBoardTitleIsNull()
			throws NonExistentUserIdException, NonExistentBoardIdException {

		this.boardHandler.createBoardByDefaultUserIdAndByTitle(USER_ID, null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void createBoardByDefaultUserIdAndByTitleShouldThrowIllegalArgumentExceptionWhenUserIdIsNull()
			throws NonExistentUserIdException, NonExistentBoardIdException {

		this.boardHandler.createBoardByDefaultUserIdAndByTitle(null, BOARD_TITLE);

	}

	@Test
	public void createBoardByDefaultUserIdAndByTitleShouldThrowNonExistentUserIdExceptionWhenUserNotExists()
			throws NonExistentUserIdException {
		// Given
		given(this.userService.getByUserName(NON_EXISTENT_USER_NAME)).willReturn(null);
		expectedException.expect(NonExistentUserIdException.class);

		// When
		this.boardHandler.createBoardByDefaultUserIdAndByTitle(NON_EXISTENT_USER_ID, BOARD_TITLE);
		// Then

		then(this.userService).should().getById(NON_EXISTENT_USER_ID);
	}

	@Test
	public void createBoardByDefaultUserIdAndByTitleTest() throws NonExistentUserIdException {

		final BoardDto expectedBoardDto = BoardDto.builder().id(EXPECTED_BOARD_ID).title(BOARD_TITLE).columns(null)
				.build();
		final UserDto expectedUserDtoForGetByIdBeforeSave = UserDto.builder().id(USER_ID).userName(USER_NAME)
				.password(USER_PASSWORD).boards(new ArrayList<>()).build();
		List<BoardDto> boards = Lists.newArrayList();
		boards.add(expectedBoardDto);
		final UserDto expectedUserDtoForGetByIdAfterSave = UserDto.builder().id(USER_ID).userName(USER_NAME)
				.password(USER_PASSWORD).boards(boards).build();

		given(this.userService.getById(USER_ID)).willReturn(expectedUserDtoForGetByIdBeforeSave,
				expectedUserDtoForGetByIdAfterSave);
		given(this.boardService.getById(EXPECTED_BOARD_ID)).willReturn(expectedBoardDto);

		// When
		this.boardHandler.createBoardByDefaultUserIdAndByTitle(USER_ID, BOARD_TITLE);
		final BoardDto actualBoardDto = this.boardService.getById(EXPECTED_BOARD_ID);
		final UserDto actualUserDtoAfterSave = this.userService.getById(USER_ID);

		// Then

		assertThat(actualBoardDto, notNullValue());
		assertThat(actualBoardDto, equalTo(expectedBoardDto));

		assertThat(actualUserDtoAfterSave, notNullValue());
		assertThat(actualUserDtoAfterSave, equalTo(expectedUserDtoForGetByIdAfterSave));

	}
}