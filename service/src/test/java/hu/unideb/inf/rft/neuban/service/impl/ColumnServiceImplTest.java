package hu.unideb.inf.rft.neuban.service.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.ColumnRepository;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ColumnServiceImplTest {

    private static final long BOARD_ID = 1L;
    private static final String BOARD_TITLE = "Board title";
    private static final long FIRST_COLUMN_ID = 1L;
    private static final long SECOND_COLUMN_ID = 2L;
    private static final long THIRD_COLUMN_ID = 3L;
    private static final long FOURTH_COLUMN_ID = 4L;
    private static final String COLUMN_TITLE = "Column title";

    private final ColumnEntity columnEntity = ColumnEntity.builder()
            .id(FIRST_COLUMN_ID)
            .title(COLUMN_TITLE)
            .build();

    private final ColumnDto firstColumnDto = ColumnDto.builder()
            .id(FIRST_COLUMN_ID)
            .title(COLUMN_TITLE)
            .build();
    private final ColumnDto secondColumnDto = ColumnDto.builder()
            .id(SECOND_COLUMN_ID)
            .title(COLUMN_TITLE)
            .build();
    private final ColumnDto thirdColumnDto = ColumnDto.builder()
            .id(THIRD_COLUMN_ID)
            .title(COLUMN_TITLE)
            .build();

    private final List<ColumnDto> columnDtoList = Lists.newArrayList(firstColumnDto, secondColumnDto, thirdColumnDto);

    private final BoardDto boardDto = BoardDto.builder()
            .id(BOARD_ID)
            .title(BOARD_TITLE)
            .columns(columnDtoList)
            .build();

    @InjectMocks
    private ColumnServiceImpl columnService;

    @Mock
    private BoardService boardService;
    @Mock
    private ColumnRepository columnRepository;

    @Test(expected = IllegalArgumentException.class)
    public void findAllByBoardIdShouldThrowIllegalArgumentExceptionWhenParamBoardIdIsNull() {
        // Given
        given(this.boardService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.columnService.findAllByBoardId(null);

        // Then
    }

    @Test
    public void findAllByBoardIdShouldReturnEmptyOptionalWhenBoardDoesNotExist() {
        // Given
        given(this.boardService.get(BOARD_ID)).willReturn(Optional.empty());

        // When
        final List<ColumnDto> result = this.columnService.findAllByBoardId(BOARD_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        then(this.boardService).should().get(BOARD_ID);
        verifyNoMoreInteractions(this.boardService);
    }

    @Test
    public void findAllByBoardIdShouldReturnAListWithThreeElementsWhenBoardExists() {
        // Given
        given(this.boardService.get(BOARD_ID)).willReturn(Optional.of(boardDto));

        // When
        final List<ColumnDto> result = this.columnService.findAllByBoardId(BOARD_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
        assertThat(result, equalTo(columnDtoList));

        then(this.boardService).should().get(BOARD_ID);
        verifyNoMoreInteractions(this.boardService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeShouldThrowIllegalArgumentExceptionWhenParamColumnIdDoesNotExist() throws ColumnNotFoundException {
        // Given

        // When
        this.columnService.remove(null);

        // Then
    }

    @Test(expected = ColumnNotFoundException.class)
    public void removeShouldThrowColumnNotFoundExceptionWhenColumnDoesNotExist() throws ColumnNotFoundException {
        // Given
        given(this.columnRepository.findOne(FIRST_COLUMN_ID)).willReturn(null);

        // When
        this.columnService.remove(FIRST_COLUMN_ID);

        // Then
    }

    @Test
    public void removeShouldBeSuccessFulDeletingWhenColumnDidExist() throws ColumnNotFoundException {
        // Given
        given(this.columnRepository.findOne(FIRST_COLUMN_ID)).willReturn(columnEntity);

        // When
        this.columnService.remove(FIRST_COLUMN_ID);

        // Then
        then(this.columnRepository).should().findOne(FIRST_COLUMN_ID);
        then(this.columnRepository).should().delete(FIRST_COLUMN_ID);
        verifyNoMoreInteractions(this.columnRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateShouldThrowIllegalArgumentExceptionWhenParamBoardIdNotExists() throws BoardNotFoundException {
        // Given
        given(this.boardService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.columnService.saveOrUpdate(null, firstColumnDto);

        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateShouldThrowIllegalArgumentExceptionWhenParamColumnDtoIsNull() throws BoardNotFoundException {
        // Given

        // When
        this.columnService.saveOrUpdate(BOARD_ID, null);

        // Then
    }

    @Test(expected = BoardNotFoundException.class)
    public void saveOrUpdateShouldThrowBoardNotFoundExceptionWhenBoardDoesNotExist() throws BoardNotFoundException {
        // Given
        given(this.boardService.get(BOARD_ID)).willReturn(Optional.empty());

        // When
        this.columnService.saveOrUpdate(BOARD_ID, firstColumnDto);

        // Then
    }

    @Test
    public void saveOrUpdateShouldBeSuccessFulSavingWhenParamColumnDtoIdIsNull() throws BoardNotFoundException {
        // Given
        final ColumnDto columnDtoWithoutId = ColumnDto.builder()
                .id(null)
                .title(COLUMN_TITLE)
                .build();

        given(this.boardService.get(BOARD_ID)).willReturn(Optional.of(boardDto));

        final ArgumentCaptor<BoardDto> boardDtoArgumentCaptor = ArgumentCaptor.forClass(BoardDto.class);

        // When
        this.columnService.saveOrUpdate(BOARD_ID, columnDtoWithoutId);

        verify(this.boardService).update(boardDtoArgumentCaptor.capture());

        // Then
        assertThat(boardDtoArgumentCaptor.getValue(), notNullValue());
        assertThat(boardDtoArgumentCaptor.getValue().getColumns(), notNullValue());
        assertThat(boardDtoArgumentCaptor.getValue().getColumns().size(), equalTo(4));
        assertTrue(boardDtoArgumentCaptor.getValue().getColumns().contains(columnDtoWithoutId));

        then(this.boardService).should().get(BOARD_ID);
        then(this.boardService).should().update(boardDto);
        verifyNoMoreInteractions(this.boardService);
    }

    @Test
    public void saveOrUpdateShouldBeSuccessFulSavingWhenParamColumnDtoDoesNotExistOnTheBoard() throws BoardNotFoundException {
        // Given
        final ColumnDto newColumnDto = ColumnDto.builder()
                .id(FOURTH_COLUMN_ID)
                .title(COLUMN_TITLE)
                .build();

        given(this.boardService.get(BOARD_ID)).willReturn(Optional.of(boardDto));

        final ArgumentCaptor<BoardDto> boardDtoArgumentCaptor = ArgumentCaptor.forClass(BoardDto.class);

        // When
        this.columnService.saveOrUpdate(BOARD_ID, newColumnDto);

        verify(this.boardService).update(boardDtoArgumentCaptor.capture());

        // Then
        assertThat(boardDtoArgumentCaptor.getValue(), notNullValue());
        assertThat(boardDtoArgumentCaptor.getValue().getColumns(), notNullValue());
        assertThat(boardDtoArgumentCaptor.getValue().getColumns().size(), equalTo(4));
        assertTrue(boardDtoArgumentCaptor.getValue().getColumns().contains(newColumnDto));

        then(this.boardService).should().get(BOARD_ID);
        then(this.boardService).should().update(boardDto);
        verifyNoMoreInteractions(this.boardService);
    }
}
