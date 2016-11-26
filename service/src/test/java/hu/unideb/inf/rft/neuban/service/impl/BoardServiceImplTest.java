package hu.unideb.inf.rft.neuban.service.impl;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import hu.unideb.inf.rft.neuban.persistence.repositories.BoardRepository;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.junit.Test;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceImplTest {

    private static final long BOARD_ID = 1L;
    private static final String BOARD_TITLE = "Title";

    private static final long USER_ID = 1L;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "passwordHash";

    private final BoardEntity boardEntity = BoardEntity.builder()
            .id(BOARD_ID)
            .title(BOARD_TITLE)
            .columns(Collections.emptyList())
            .build();

    private final BoardDto boardDto = BoardDto.builder()
            .id(BOARD_ID)
            .title(BOARD_TITLE)
            .columns(Collections.emptyList())
            .build();

    private final UserDto userDto = UserDto.builder()
            .id(USER_ID)
            .userName(USERNAME)
            .password(PASSWORD)
            .role(Role.USER)
            .boards(Collections.nCopies(3, boardDto))
            .build();

    @InjectMocks
    private BoardServiceImpl boardService;

    @Mock
    private UserService userService;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test(expected = IllegalArgumentException.class)
    public void getShouldThrowIllegalArgumentExceptionWhenParamBoardIdIsNull() {
        // Given

        // When
        this.boardService.get(null);

        // Then
    }

    @Test
    public void getShouldReturnEmptyOptionalWhenBoardDoesNotExist() {
        // Given
        given(this.boardRepository.findOne(BOARD_ID)).willReturn(null);

        // When
        Optional<BoardDto> result = this.boardService.get(BOARD_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        then(this.boardRepository).should().findOne(BOARD_ID);
        verifyZeroInteractions(this.modelMapper);
        verifyNoMoreInteractions(this.boardRepository);
    }

    @Test
    public void getShouldReturnExistingBoardWithOptionalWhenBoardExists() {
        // Given
        given(this.boardRepository.findOne(BOARD_ID)).willReturn(boardEntity);
        given(this.modelMapper.map(boardEntity, BoardDto.class)).willReturn(boardDto);

        // When
        Optional<BoardDto> result = this.boardService.get(BOARD_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(boardDto));

        then(this.boardRepository).should().findOne(BOARD_ID);
        then(this.modelMapper).should().map(boardEntity, BoardDto.class);
        verifyNoMoreInteractions(this.boardRepository, this.modelMapper);
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
    public void updateShouldThrowIllegalArgumentExceptionWhenParamBoardDtoDoesNotExist() throws BoardNotFoundException {
        // Given

        // When
        this.boardService.update(null);

        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowIllegalArgumentExceptionWhenParamBoardDtoIdDoesNotExist() throws BoardNotFoundException {
        // Given
        final BoardDto boardDtoWithoutId = BoardDto.builder().id(null).build();

        // When
        this.boardService.update(boardDtoWithoutId);

        // Then
    }

    @Test(expected = BoardNotFoundException.class)
    public void updateShouldThrowBoardNotFoundExceptionWhenParamBoardDtoIdIsInvalid() throws BoardNotFoundException {
        // Given
        given(this.boardRepository.findOne(BOARD_ID)).willReturn(null);

        // When
        this.boardService.update(boardDto);

        // Then
    }

    @Test
    public void updateShouldBeSuccessFulUpdatingWhenParamBoardDtoExistsAndValid() throws BoardNotFoundException {
        // Given
        given(this.boardRepository.findOne(BOARD_ID)).willReturn(boardEntity);
        given(this.boardRepository.saveAndFlush(boardEntity)).willReturn(boardEntity);

        // When
        final Long result = this.boardService.update(boardDto);

        // Then
        assertThat(result, notNullValue());
        assertThat(result, equalTo(1L));

        then(this.boardRepository).should().findOne(BOARD_ID);
        then(this.boardRepository).should().saveAndFlush(boardEntity);
        verifyNoMoreInteractions(this.boardRepository);
    }
}
