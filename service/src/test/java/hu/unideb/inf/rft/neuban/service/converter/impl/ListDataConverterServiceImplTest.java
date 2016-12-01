package hu.unideb.inf.rft.neuban.service.converter.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ListDataConverterServiceImplTest {

    private static final long FIRST_BOARD_ID = 1L;
    private static final long SECOND_BOARD_ID = 2L;

    private static final String FIRST_BOARD_TITLE = "First board title";
    private static final String SECOND_BOARD_TITLE = "Second board title";

    private final List<BoardEntity> boardEntities = Lists.newArrayList(
            BoardEntity.builder().id(FIRST_BOARD_ID).title(FIRST_BOARD_TITLE).build(),
            BoardEntity.builder().id(SECOND_BOARD_ID).title(SECOND_BOARD_TITLE).build()
    );

    private final List<BoardDto> boardDtos = Lists.newArrayList(
            BoardDto.builder().id(FIRST_BOARD_ID).title(FIRST_BOARD_TITLE).build(),
            BoardDto.builder().id(SECOND_BOARD_ID).title(SECOND_BOARD_TITLE).build()
    );

    @Mock
    private ModelMapper modelMapper;

    private ListDataConverterServiceImpl<BoardEntity, BoardDto> listDataConverterService;

    @Before
    public void setUp() {
        this.listDataConverterService = new ListDataConverterServiceImpl<>(BoardEntity.class, BoardDto.class, this.modelMapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertToSourcesShouldThrowIllegalArgumentExceptionWhenParamTargetObjectListIsNull() {
        // Given

        // When
        this.listDataConverterService.convertToSources(null);

        // Then
    }

    @Test
    public void convertToSourcesShouldReturnEmptySourceObjectListWhenParamTargetObjectListIsEmpty() {
        // Given

        // When
        final List<BoardEntity> result = this.listDataConverterService.convertToSources(Collections.emptyList());

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        verifyZeroInteractions(this.modelMapper);
    }

    @Test
    public void convertToSourcesShouldReturnSourceObjectListWithTwoElementsWhenParamTargetObjectListContainsTwoElements() {
        // Given
        given(this.modelMapper.map(boardDtos.get(0), BoardEntity.class)).willReturn(boardEntities.get(0));
        given(this.modelMapper.map(boardDtos.get(1), BoardEntity.class)).willReturn(boardEntities.get(1));

        // When
        final List<BoardEntity> result = this.listDataConverterService.convertToSources(boardDtos);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result, equalTo(boardEntities));

        then(this.modelMapper).should().map(boardDtos.get(0), BoardEntity.class);
        then(this.modelMapper).should().map(boardDtos.get(1), BoardEntity.class);
        verifyNoMoreInteractions(this.modelMapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertToTargetsShouldThrowIllegalArgumentExceptionWhenParamSourceObjectListIsNull() {
        // Given

        // When
        this.listDataConverterService.convertToTargets(null);

        // Then
    }

    @Test
    public void convertToTargetsShouldReturnEmptyTargetObjectListWhenParamSourceObjectListIsEmpty() {
        // Given

        // When
        final List<BoardDto> result = this.listDataConverterService.convertToTargets(Collections.emptyList());

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        verifyZeroInteractions(this.modelMapper);
    }

    @Test
    public void convertToTargetsShouldReturnTargetObjectListWithTwoElementsWhenParamSourceObjectListContainsTwoElements() {
        // Given
        given(this.modelMapper.map(boardEntities.get(0), BoardDto.class)).willReturn(boardDtos.get(0));
        given(this.modelMapper.map(boardEntities.get(1), BoardDto.class)).willReturn(boardDtos.get(1));

        // When
        final List<BoardDto> result = this.listDataConverterService.convertToTargets(boardEntities);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result, equalTo(boardDtos));

        then(this.modelMapper).should().map(boardEntities.get(0), BoardDto.class);
        then(this.modelMapper).should().map(boardEntities.get(1), BoardDto.class);
        verifyNoMoreInteractions(this.modelMapper);
    }

}
