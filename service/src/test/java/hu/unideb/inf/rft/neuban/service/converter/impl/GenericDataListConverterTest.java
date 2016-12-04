package hu.unideb.inf.rft.neuban.service.converter.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.service.converter.DataListConverter;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GenericDataListConverterTest {

    private static final long FIRST_BOARD_ID = 1L;
    private static final long SECOND_BOARD_ID = 2L;

    private static final String FIRST_BOARD_TITLE = "First board title";
    private static final String SECOND_BOARD_TITLE = "Second board title";

    private final List<BoardEntity> boardEntityList = Lists.newArrayList(
            BoardEntity.builder().id(FIRST_BOARD_ID).title(FIRST_BOARD_TITLE).build(),
            BoardEntity.builder().id(SECOND_BOARD_ID).title(SECOND_BOARD_TITLE).build()
    );

    private final List<BoardDto> boardDtoList = Lists.newArrayList(
            BoardDto.builder().id(FIRST_BOARD_ID).title(FIRST_BOARD_TITLE).build(),
            BoardDto.builder().id(SECOND_BOARD_ID).title(SECOND_BOARD_TITLE).build()
    );

    private DataListConverter<BoardEntity, BoardDto> listDataConverterService;

    @Mock
    private SingleBoardDataConverter singleBoardDataConverter;

    @Before
    public void setUp() {
        this.listDataConverterService = new GenericDataListConverter<>(BoardEntity.class, BoardDto.class, this.singleBoardDataConverter);
    }

    @Test
    public void convertToSourcesShouldReturnEmptySourceObjectListWhenParameterTargetObjectListIsNull() {
        // Given

        // When
        final List<BoardEntity> result = this.listDataConverterService.convertToSources(null);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        verifyZeroInteractions(this.singleBoardDataConverter);
    }

    @Test
    public void convertToSourcesShouldReturnEmptySourceObjectListWhenParameterTargetObjectListIsEmpty() {
        // Given

        // When
        final List<BoardEntity> result = this.listDataConverterService.convertToSources(Collections.emptyList());

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        verifyZeroInteractions(this.singleBoardDataConverter);
    }

    @Test
    public void convertToSourcesShouldReturnSourceObjectListWithTwoElementsWhenParameterTargetObjectListContainsTwoElements() {
        // Given
        final Optional<BoardEntity> firstBoardEntityOptional = Optional.ofNullable(boardEntityList.get(0));
        final Optional<BoardEntity> secondBoardEntityOptional = Optional.ofNullable(boardEntityList.get(1));

        given(this.singleBoardDataConverter.convertToSource(boardDtoList.get(0))).willReturn(firstBoardEntityOptional);
        given(this.singleBoardDataConverter.convertToSource(boardDtoList.get(1))).willReturn(secondBoardEntityOptional);

        // When
        final List<BoardEntity> result = this.listDataConverterService.convertToSources(boardDtoList);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result, equalTo(boardEntityList));

        then(this.singleBoardDataConverter).should().convertToSource(boardDtoList.get(0));
        then(this.singleBoardDataConverter).should().convertToSource(boardDtoList.get(1));
        verifyNoMoreInteractions(this.singleBoardDataConverter);
    }

    @Test
    public void convertToTargetsShouldReturnEmptyTargetObjectListWhenParameterSourceObjectListIsNull() {
        // Given

        // When
        final List<BoardDto> result = this.listDataConverterService.convertToTargets(null);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        verifyZeroInteractions(this.singleBoardDataConverter);
    }

    @Test
    public void convertToTargetsShouldReturnEmptyTargetObjectListWhenParameterSourceObjectListIsEmpty() {
        // Given

        // When
        final List<BoardDto> result = this.listDataConverterService.convertToTargets(Collections.emptyList());

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        verifyZeroInteractions(this.singleBoardDataConverter);
    }

    @Test
    public void convertToTargetsShouldReturnTargetObjectListWithTwoElementsWhenParameterSourceObjectListContainsTwoElements() {
        // Given
        final Optional<BoardDto> firstBoardDtoOptional = Optional.ofNullable(boardDtoList.get(0));
        final Optional<BoardDto> secondBoardDtoOptional = Optional.ofNullable(boardDtoList.get(1));

        given(this.singleBoardDataConverter.convertToTarget(boardEntityList.get(0))).willReturn(firstBoardDtoOptional);
        given(this.singleBoardDataConverter.convertToTarget(boardEntityList.get(1))).willReturn(secondBoardDtoOptional);

        // When
        final List<BoardDto> result = this.listDataConverterService.convertToTargets(boardEntityList);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result, equalTo(boardDtoList));

        then(this.singleBoardDataConverter).should().convertToTarget(boardEntityList.get(0));
        then(this.singleBoardDataConverter).should().convertToTarget(boardEntityList.get(1));
        verifyNoMoreInteractions(this.singleBoardDataConverter);
    }
}
