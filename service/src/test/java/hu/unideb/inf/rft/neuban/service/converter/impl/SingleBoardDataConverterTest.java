package hu.unideb.inf.rft.neuban.service.converter.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;
import hu.unideb.inf.rft.neuban.service.converter.DataListConverter;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SingleBoardDataConverterTest {

    private static final long BOARD_ID = 1L;
    private static final String BOARD_TITLE = "Board title";

    private static final long FIRST_ID = 1L;
    private static final long SECOND_ID = 2L;
    private static final long THIRD_ID = 3L;
    private static final String COLUMN_TITLE = "Column title";

    private final List<ColumnEntity> columnEntityList = Lists.newArrayList(
            ColumnEntity.builder().id(FIRST_ID).title(COLUMN_TITLE).build(),
            ColumnEntity.builder().id(SECOND_ID).title(COLUMN_TITLE).build(),
            ColumnEntity.builder().id(THIRD_ID).title(COLUMN_TITLE).build()
    );

    private final List<ColumnDto> columnDtoList = Lists.newArrayList(
            ColumnDto.builder().id(FIRST_ID).title(COLUMN_TITLE).build(),
            ColumnDto.builder().id(SECOND_ID).title(COLUMN_TITLE).build(),
            ColumnDto.builder().id(THIRD_ID).title(COLUMN_TITLE).build()
    );

    private final BoardEntity boardEntity = BoardEntity.builder()
            .id(BOARD_ID)
            .title(BOARD_TITLE)
            .columns(columnEntityList)
            .build();

    private final BoardDto boardDto = BoardDto.builder()
            .id(BOARD_ID)
            .title(BOARD_TITLE)
            .columns(columnDtoList)
            .build();

    @InjectMocks
    private SingleBoardDataConverter singleBoardDataConverter;

    @Mock
    private DataListConverter<ColumnEntity, ColumnDto> columnDataListConverter;

    @Test
    public void convertToSourceShouldReturnEmptyOptionalWhenParameterBoardDtoIsNull() {
        // Given

        // When
        final Optional<BoardEntity> result = this.singleBoardDataConverter.convertToSource(null);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        verifyZeroInteractions(this.columnDataListConverter);
    }

    @Test
    public void convertToSourceShouldReturnOptionalWithValueWhenParameterBoardDtoIsNonNull() {
        // Given
        given(this.columnDataListConverter.convertToSources(columnDtoList)).willReturn(columnEntityList);

        // When
        final Optional<BoardEntity> result = this.singleBoardDataConverter.convertToSource(boardDto);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(boardEntity));

        then(this.columnDataListConverter).should().convertToSources(columnDtoList);
    }

    @Test
    public void convertToTargetShouldReturnEmptyOptionalWhenParameterBoardEntityIsNull() {
        // Given

        // When
        final Optional<BoardDto> result = this.singleBoardDataConverter.convertToTarget(null);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        verifyZeroInteractions(this.columnDataListConverter);
    }

    @Test
    public void convertToTargetShouldReturnOptionalWithValueWhenParameterBoardEntityIsNonNull() {
        // Given
        given(this.columnDataListConverter.convertToTargets(columnEntityList)).willReturn(columnDtoList);

        // When
        final Optional<BoardDto> result = this.singleBoardDataConverter.convertToTarget(boardEntity);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(boardDto));

        then(this.columnDataListConverter).should().convertToTargets(columnEntityList);
    }
}
