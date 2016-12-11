package hu.unideb.inf.rft.neuban.service.impl;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import hu.unideb.inf.rft.neuban.persistence.repositories.BoardRepository;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentBoardIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUserIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.RelationNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.impl.shared.SingleDataUpdateServiceImpl;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceImplTest {

    private static final Long EXPECTED_BOARD_ID = 1L;
    private static final Long NOT_EXPECTED_BOARD_ID = 2L;
    private static final Long NON_EXISTENT_USER_ID = 0L;
    private static final Long NON_EXISTENT_BOARD_ID = 0L;
    private static final long BOARD_ID = 1L;
    private static final String BOARD_TITLE = "Title";

    private static final long USER_ID = 1L;
    private static final long USER2_ID = 2L;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "passwordHash";
    private static final String USERNAME2 = "username2";
    private static final String PASSWORD2 = "passwordHash2";

    private final BoardEntity boardEntity = BoardEntity.builder().id(BOARD_ID).title(BOARD_TITLE)
            .columns(Collections.emptyList()).build();

    private final BoardEntity not_Expectedboard_Entity = BoardEntity.builder().id(NOT_EXPECTED_BOARD_ID)
            .title(BOARD_TITLE).columns(Collections.emptyList()).build();

    private final BoardDto boardDto = BoardDto.builder().id(BOARD_ID).title(BOARD_TITLE)
            .columns(Collections.emptyList()).build();

    private final UserDto userDto = UserDto.builder().id(USER_ID).userName(USERNAME).password(PASSWORD).role(Role.USER)
            .boards(Collections.nCopies(3, boardDto)).build();

    private final UserDto userDto2 = UserDto.builder().id(USER2_ID).userName(USERNAME2).password(PASSWORD2)
            .role(Role.USER).boards(Collections.nCopies(3, boardDto)).build();

    @InjectMocks
    private BoardServiceImpl boardService;

    @Mock
    private UserService userService;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private SingleDataGetService<BoardDto, Long> singleBoardDataGetService;
    @Mock
    private SingleDataUpdateServiceImpl<BoardEntity, BoardDto, Long, BoardNotFoundException> singleBoardDataUpdateService;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test(expected = IllegalArgumentException.class)
    public void getShouldThrowIllegalArgumentExceptionWhenParamBoardIdIsNull() {
        // Given
        given(this.singleBoardDataGetService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.boardService.get(null);

        // Then
    }

    @Test
    public void getShouldReturnEmptyOptionalWhenBoardDoesNotExist() {
        // Given
        given(this.singleBoardDataGetService.get(BOARD_ID)).willReturn(Optional.empty());

        // When
        Optional<BoardDto> result = this.boardService.get(BOARD_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        then(this.singleBoardDataGetService).should().get(BOARD_ID);
        verifyNoMoreInteractions(this.singleBoardDataGetService);
    }

    @Test
    public void getShouldReturnWithNonEmptyOptionalWhenBoardDoesExist() {
        // Given
        given(this.singleBoardDataGetService.get(BOARD_ID)).willReturn(Optional.of(boardDto));

        // When
        Optional<BoardDto> result = this.boardService.get(BOARD_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(boardDto));

        then(this.singleBoardDataGetService).should().get(BOARD_ID);
        verifyNoMoreInteractions(this.singleBoardDataGetService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllByUserIdShouldThrowIllegalArgumentExceptionWhenParamUserIdIsNull() {
        // Given
        given(this.userService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.boardService.getAllByUserId(null);

        // Then
    }

    @Test
    public void getAllByUserIdShouldReturnEmptyListWhenUserDoesNotExist() {
        // Given
        given(this.userService.get(USER_ID)).willReturn(Optional.empty());

        // When
        final List<BoardDto> result = this.boardService.getAllByUserId(USER_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        then(this.userService).should().get(USER_ID);
        verifyNoMoreInteractions(this.userService);
    }

    @Test
    public void getAllByUserIdShouldReturnListWithThreeElementsWhenUserDoesExist() {
        // Given
        given(this.userService.get(USER_ID)).willReturn(Optional.of(userDto));

        // When
        final List<BoardDto> result = this.boardService.getAllByUserId(USER_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(false));
        assertThat(result.size(), equalTo(3));
        assertThat(result, equalTo(Collections.nCopies(3, boardDto)));

        then(this.userService).should().get(USER_ID);
        verifyNoMoreInteractions(this.userService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowIllegalArgumentExceptionWhenParamBoardDtoDoesNotExist() throws DataNotFoundException {
        // Given
        doThrow(IllegalArgumentException.class).when(this.singleBoardDataUpdateService).update(null);

        // When
        this.boardService.update(null);

        // Then
    }

    @Test(expected = BoardNotFoundException.class)
    public void updateShouldThrowBoardNotFoundExceptionWhenParamBoardDtoIdIsInvalid() throws DataNotFoundException {
        // Given
        final BoardDto boardDtoWithoutId = BoardDto.builder().id(null).build();

        doThrow(BoardNotFoundException.class).when(this.singleBoardDataUpdateService).update(boardDtoWithoutId);

        // When
        this.boardService.update(boardDtoWithoutId);

        // Then
    }

    @Test
    public void updateShouldBeSuccessFulUpdatingWhenParamBoardDtoExistsAndValid() throws DataNotFoundException {
        // Given
        doNothing().when(this.singleBoardDataUpdateService).update(boardDto);

        // When
        this.boardService.update(boardDto);
        // Then

        then(this.singleBoardDataUpdateService).should().update(boardDto);
        verifyNoMoreInteractions(this.singleBoardDataUpdateService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserFromBoardByUserIdAndByBoardIdShouldThrowIllegalArgumentExceptionWhenUserIdIsNull()
            throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException, DataNotFoundException {

        this.boardService.removeUserFromBoardByUserIdAndByBoardId(null, EXPECTED_BOARD_ID);

    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserFromBoardByUserIdAndByBoardIdShouldThrowIllegalArgumentExceptionWhenBoardIdIsNull()
            throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException, DataNotFoundException {

        this.boardService.removeUserFromBoardByUserIdAndByBoardId(USER_ID, null);

    }

    @Test
    public void removeUserFromBoardByUserIdAndByBoardIdShouldThrowNonExistentUserIdExceptionWhenUserNotExists()
            throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException, DataNotFoundException {
        // Given
        given(this.userService.get(NON_EXISTENT_USER_ID)).willReturn(Optional.empty());
        expectedException.expect(NonExistentUserIdException.class);

        // When
        this.boardService.removeUserFromBoardByUserIdAndByBoardId(NON_EXISTENT_USER_ID, EXPECTED_BOARD_ID);
        // Then

        then(this.userService).should().get(NON_EXISTENT_USER_ID);
    }

    /*
        @Test
        public void removeUserFromBoardByUserIdAndByBoardIdShouldThrowNoRelationFoundExceptionWhenUserHasNoBoards()
                throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException {

            // Given

            final BoardDto expectedBoardDto = BoardDto.builder().id(EXPECTED_BOARD_ID).title(BOARD_TITLE).columns(null)
                    .build();
            final UserDto expectedUserDtoWithoutExpectedBoard = UserDto.builder().id(USER_ID).userName(USERNAME)
                    .password(PASSWORD).boards(new ArrayList<>()).build();

            given(this.userService.get(USER_ID)).willReturn(Optional.of(expectedUserDtoWithoutExpectedBoard));
            given(this.boardRepository.findOne(EXPECTED_BOARD_ID)).willReturn(boardEntity);
            given(this.modelMapper.map(boardEntity, BoardDto.class)).willReturn(expectedBoardDto);

            expectedException.expect(RelationNotFoundException.class);

            // When
            this.boardService.removeUserFromBoardByUserIdAndByBoardId(USER_ID, EXPECTED_BOARD_ID);

            // Then
            then(this.userService).should().get(USER_ID);
            then(this.boardService).should().get(EXPECTED_BOARD_ID);
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
            final UserDto expectedUserDtoWithoutExpectedBoard = UserDto.builder().id(USER_ID).userName(USERNAME)
                    .password(PASSWORD).boards(boards).build();

            given(this.userService.get(USER_ID)).willReturn(Optional.of(expectedUserDtoWithoutExpectedBoard));
            given(this.boardRepository.findOne(NOT_EXPECTED_BOARD_ID)).willReturn(not_Expectedboard_Entity);
            given(this.modelMapper.map(not_Expectedboard_Entity, BoardDto.class)).willReturn(notExpectedBoardDto);

            expectedException.expect(RelationNotFoundException.class);

            // When
            this.boardService.removeUserFromBoardByUserIdAndByBoardId(USER_ID, NOT_EXPECTED_BOARD_ID);

            // Then
            then(this.userService).should().get(USER_ID);
            then(this.boardService).should().get(NOT_EXPECTED_BOARD_ID);
        }

        @Test
        public void removeUserFromBoardByUserIdAndByBoardIdShouldThrowNonExistentBoardIdExceptionWhenBoardNotExists()
                throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException {

            // Given
            final UserDto expectedUserDto = UserDto.builder().id(USER_ID).userName(USERNAME).password(PASSWORD)
                    .boards(new ArrayList<>()).build();

            given(this.userService.get(USER_ID)).willReturn(Optional.of(expectedUserDto));
            given(this.boardRepository.findOne(EXPECTED_BOARD_ID)).willReturn(boardEntity);
            expectedException.expect(NonExistentBoardIdException.class);

            // When
            this.boardService.removeUserFromBoardByUserIdAndByBoardId(USER_ID, NON_EXISTENT_BOARD_ID);

            // Then
            then(this.boardService).should().get(NON_EXISTENT_BOARD_ID);
        }

        @Test
        public void removeUserFromBoardByUserIdAndByBoardIdTest()
                throws NonExistentUserIdException, NonExistentBoardIdException, RelationNotFoundException {

            // Given
            final BoardDto expectedUserBoard = BoardDto.builder().id(EXPECTED_BOARD_ID).title(BOARD_TITLE).columns(null)
                    .build();
            List<BoardDto> boards = Lists.newArrayList();
            boards.add(expectedUserBoard);
            final UserDto expectedUserDtoBeforeRemove = UserDto.builder().id(USER_ID).userName(USERNAME).password(PASSWORD)
                    .boards(boards).build();

            final UserDto expectedUserDtoAfterRemove = UserDto.builder().id(USER_ID).userName(USERNAME).password(PASSWORD)
                    .boards(new ArrayList<>()).build();
            given(this.userService.get(USER_ID)).willReturn(Optional.of(expectedUserDtoBeforeRemove),
                    Optional.of(expectedUserDtoAfterRemove));

            given(this.boardRepository.findOne(EXPECTED_BOARD_ID)).willReturn(boardEntity);
            given(this.modelMapper.map(boardEntity, BoardDto.class)).willReturn(expectedUserBoard);
            // When
            this.boardService.removeUserFromBoardByUserIdAndByBoardId(USER_ID, EXPECTED_BOARD_ID);
            final Optional<UserDto> actualUserDto = this.userService.get(USER_ID);

            // Then
            assertThat(actualUserDto, notNullValue());
            assertThat(actualUserDto.isPresent(), is(true));
            assertThat(actualUserDto.get(), equalTo(expectedUserDtoAfterRemove));
        }
    */
    @Test(expected = IllegalArgumentException.class)
    public void addUserToBoardByUserIdAndByBoardIdShouldThrowIllegalArgumentExceptionWhenUserIdIsNull()
            throws NonExistentUserIdException, NonExistentBoardIdException, DataNotFoundException {

        this.boardService.addUserToBoardByUserIdAndByBoardId(null, EXPECTED_BOARD_ID);

    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserToBoardByUserIdAndByBoardIdShouldThrowIllegalArgumentExceptionWhenBoardIdIsNull()
            throws NonExistentUserIdException, NonExistentBoardIdException, DataNotFoundException {

        this.boardService.addUserToBoardByUserIdAndByBoardId(USER_ID, null);

    }

    @Test
    public void addUserToBoardByUserIdAndByBoardIdShouldThrowNonExistentUserIdExceptionWhenUserNotExists()
            throws NonExistentUserIdException, NonExistentBoardIdException, DataNotFoundException {
        // Given
        given(this.userService.get(NON_EXISTENT_USER_ID)).willReturn(Optional.empty());
        expectedException.expect(NonExistentUserIdException.class);

        // When
        this.boardService.addUserToBoardByUserIdAndByBoardId(NON_EXISTENT_USER_ID, EXPECTED_BOARD_ID);
        // Then

        then(this.userService).should().get(NON_EXISTENT_USER_ID);
    }
/*
    @Test
	public void addUserToBoardByUserIdAndByBoardIdShouldThrowNonExistentBoardIdExceptionWhenBoardNotExists()
			throws NonExistentUserIdException, NonExistentBoardIdException {

		// Given
		final UserDto expectedUserDto = UserDto.builder().id(USER_ID).userName(USERNAME).password(PASSWORD)
				.boards(new ArrayList<>()).build();

		given(this.userService.get(USER_ID)).willReturn(Optional.of(expectedUserDto));
		given(this.boardRepository.findOne(NOT_EXPECTED_BOARD_ID)).willReturn(not_Expectedboard_Entity);
		expectedException.expect(NonExistentBoardIdException.class);

		// When
		this.boardService.addUserToBoardByUserIdAndByBoardId(USER_ID, NON_EXISTENT_BOARD_ID);

		// Then
		then(this.boardService).should().get(NON_EXISTENT_BOARD_ID);
	}

	@Test
	public void addUserToBoardByUserIdAndByBoardIdTest()
			throws NonExistentUserIdException, NonExistentBoardIdException {

		// Given

		final BoardDto expectedUserBoard = BoardDto.builder().id(EXPECTED_BOARD_ID).title(BOARD_TITLE).columns(null)
				.build();
		final UserDto expectedUserDtoBeforeAdd = UserDto.builder().id(USER_ID).userName(USERNAME).password(PASSWORD)
				.boards(new ArrayList<>()).build();

		List<BoardDto> boards = Lists.newArrayList();
		boards.add(expectedUserBoard);

		final UserDto expectedUserDtoAfterAdd = UserDto.builder().id(USER_ID).userName(USERNAME).password(PASSWORD)
				.boards(boards).build();

		given(this.userService.get(USER_ID)).willReturn(Optional.of(expectedUserDtoBeforeAdd),
				Optional.of(expectedUserDtoAfterAdd));
		given(this.boardRepository.findOne(EXPECTED_BOARD_ID)).willReturn(boardEntity);
		given(this.modelMapper.map(boardEntity, BoardDto.class)).willReturn(expectedUserBoard);

		// When
		this.boardService.addUserToBoardByUserIdAndByBoardId(USER_ID, EXPECTED_BOARD_ID);

		final Optional<UserDto> actualUserDto = this.userService.get(USER_ID);

		// Then
		assertThat(actualUserDto, notNullValue());
		assertThat(actualUserDto.isPresent(), is(true));
		assertThat(actualUserDto.get(), equalTo(expectedUserDtoAfterAdd));

	}*/

    @Test(expected = IllegalArgumentException.class)
    public void createBoardShouldThrowIllegalArgumentExceptionWhenBoardTitleIsNull()
            throws NonExistentUserIdException, NonExistentBoardIdException, DataNotFoundException {

        this.boardService.createBoard(USER_ID, null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void createBoardShouldThrowIllegalArgumentExceptionWhenUserIdIsNull()
            throws NonExistentUserIdException, NonExistentBoardIdException, DataNotFoundException {

        this.boardService.createBoard(null, BOARD_TITLE);

    }

    @Test
    public void createBoardShouldThrowNonExistentUserIdExceptionWhenUserNotExists() throws NonExistentUserIdException, DataNotFoundException {
        // Given
        given(this.userService.get(NON_EXISTENT_USER_ID)).willReturn(Optional.empty());
        expectedException.expect(NonExistentUserIdException.class);

        // When
        this.boardService.createBoard(NON_EXISTENT_USER_ID, BOARD_TITLE);
        // Then

        then(this.userService).should().get(NON_EXISTENT_USER_ID);
    }
/*
	@Test
	public void createBoardTest() throws NonExistentUserIdException {
		// Given
		final BoardDto expectedBoardDto = BoardDto.builder().id(EXPECTED_BOARD_ID).title(BOARD_TITLE).columns(null)
				.build();
		final UserDto expectedUserDtoForGetByIdBeforeSave = UserDto.builder().id(USER_ID).userName(USERNAME)
				.password(PASSWORD).boards(Lists.newArrayList()).build();
		List<BoardDto> boards = Lists.newArrayList();
		boards.add(expectedBoardDto);
		final UserDto expectedUserDtoForGetByIdAfterSave = UserDto.builder().id(USER_ID).userName(USERNAME)
				.password(PASSWORD).boards(boards).build();

		given(this.userService.get(USER_ID)).willReturn(Optional.of(expectedUserDtoForGetByIdBeforeSave),
				Optional.of(expectedUserDtoForGetByIdAfterSave));
		given(this.modelMapper.map(boardEntity, BoardDto.class)).willReturn(expectedBoardDto);
		given(this.boardRepository.findOne(EXPECTED_BOARD_ID)).willReturn(boardEntity);

		// When
		this.boardService.createBoard(USER_ID, BOARD_TITLE);
		final Optional<BoardDto> actualBoardDto = this.boardService.get(EXPECTED_BOARD_ID);
		final Optional<UserDto> actualUserDtoAfterSave = this.userService.get(USER_ID);

		// Then

		assertThat(actualBoardDto, notNullValue());
		assertThat(actualBoardDto.isPresent(), is(true));
		assertThat(actualBoardDto.get(), equalTo(expectedBoardDto));

		assertThat(actualUserDtoAfterSave, notNullValue());
		assertThat(actualUserDtoAfterSave.isPresent(), is(true));
		assertThat(actualUserDtoAfterSave.get(), equalTo(expectedUserDtoForGetByIdAfterSave));
	}*/

    @Test(expected = IllegalArgumentException.class)
    public void removeShouldThrowIllegalArgumentExceptionWhenBoardIdIsNull() throws DataNotFoundException {
        this.boardService.remove(null);
    }

    @Test
    public void removeShouldThrowBoardNotFoundExceptionWhenBoardDoesNotExist() throws DataNotFoundException {
        // Given

        given(this.boardRepository.findOne(BOARD_ID)).willReturn(null);

        // When

        expectedException.expect(BoardNotFoundException.class);
        this.boardService.remove(BOARD_ID);
    }

    @Test
    public void removeShouldDeleteRelationThenDeleteBoard() throws DataNotFoundException {
        // Given

        final UserDto userDtoBeforeDelete = UserDto.builder().id(USER_ID).userName(USERNAME).password(PASSWORD)
                .role(Role.USER).boards(Lists.newArrayList(boardDto)).build();

        final UserDto userDto2BeforeDelete = UserDto.builder().id(USER2_ID).userName(USERNAME2).password(PASSWORD2)
                .role(Role.USER).boards(Lists.newArrayList(boardDto)).build();

        final UserDto userDtoAfterDelete = UserDto.builder().id(USER_ID).userName(USERNAME).password(PASSWORD)
                .role(Role.USER).boards(Lists.newArrayList()).build();

        final UserDto userDto2AfterDelete = UserDto.builder().id(USER2_ID).userName(USERNAME2).password(PASSWORD2)
                .role(Role.USER).boards(Lists.newArrayList()).build();

        final List<UserDto> expectedUserDtosBeforeSave = Lists.newArrayList(userDtoBeforeDelete, userDto2BeforeDelete);
        final List<UserDto> expectedUserDtosAfterSave = Lists.newArrayList(userDtoAfterDelete, userDto2AfterDelete);

        given(this.userService.getAll()).willReturn(expectedUserDtosBeforeSave, expectedUserDtosAfterSave);
        given(this.boardRepository.findOne(BOARD_ID)).willReturn(boardEntity);

        given(this.modelMapper.map(boardEntity, BoardDto.class)).willReturn(boardDto);

        // When

        List<UserDto> actualUserDtosBeforeSave = this.userService.getAll();
        this.boardService.remove(BOARD_ID);
        List<UserDto> actualUserDtosAfterSave = this.userService.getAll();

        // Then

        assertThat(actualUserDtosBeforeSave, equalTo(expectedUserDtosBeforeSave));
        assertThat(actualUserDtosAfterSave, equalTo(expectedUserDtosAfterSave));

        then(this.boardRepository).should().findOne(BOARD_ID);
        then(this.boardRepository).should().delete(BOARD_ID);

    }

}
