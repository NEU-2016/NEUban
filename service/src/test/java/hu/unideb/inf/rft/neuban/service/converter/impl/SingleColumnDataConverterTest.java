package hu.unideb.inf.rft.neuban.service.converter.impl;


import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;
import hu.unideb.inf.rft.neuban.service.converter.DataListConverter;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SingleColumnDataConverterTest {

    private static final long COLUMN_ID = 1L;
    private static final String COLUMN_TITLE = "Column title";

    private static final long FIRST_ID = 1L;
    private static final long SECOND_ID = 2L;
    private static final long THIRD_ID = 3L;
    private static final String CARD_TITLE = "Card title";
    private static final String CARD_DESCRIPTION = "Card description";

    private final List<CardEntity> cardEntityList = Lists.newArrayList(
            CardEntity.builder().id(FIRST_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build(),
            CardEntity.builder().id(SECOND_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build(),
            CardEntity.builder().id(THIRD_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build()
    );

    private final List<CardDto> cardDtoList = Lists.newArrayList(
            CardDto.builder().id(FIRST_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build(),
            CardDto.builder().id(SECOND_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build(),
            CardDto.builder().id(THIRD_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build()
    );

    private final ColumnEntity columnEntity = ColumnEntity.builder()
            .id(COLUMN_ID)
            .title(COLUMN_TITLE)
            .cards(cardEntityList)
            .build();

    private final ColumnDto columnDto = ColumnDto.builder()
            .id(COLUMN_ID)
            .title(COLUMN_TITLE)
            .cards(cardDtoList)
            .build();

    @InjectMocks
    private SingleColumnDataConverter singleColumnDataConverter;

    @Mock
    private DataListConverter<CardEntity, CardDto> cardDataListConverter;

    @Test
    public void convertToSourceShouldReturnEmptyOptionalWhenParameterColumnDtoIsNull() {
        // Given

        // When
        final Optional<ColumnEntity> result = this.singleColumnDataConverter.convertToSource(null);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        verifyZeroInteractions(this.cardDataListConverter);
    }

    @Test
    public void convertToSourceShouldReturnOptionalWithValueWhenParameterColumnDtoIsNonNull() {
        // Given
        given(this.cardDataListConverter.convertToSources(cardDtoList)).willReturn(cardEntityList);

        // When
        final Optional<ColumnEntity> result = this.singleColumnDataConverter.convertToSource(columnDto);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(columnEntity));

        then(this.cardDataListConverter).should().convertToSources(cardDtoList);
        verifyNoMoreInteractions(this.cardDataListConverter);
    }

    @Test
    public void convertToTargetShouldReturnEmptyOptionalWhenParameterColumnEntityIsNull() {
        // Given

        // When
        final Optional<ColumnDto> result = this.singleColumnDataConverter.convertToTarget(null);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        verifyZeroInteractions(this.cardDataListConverter);
    }

    @Test
    public void convertToTargetShouldReturnOptionalWithValueWhenParameterColumnEntityIsNonNull() {
        // Given
        given(this.cardDataListConverter.convertToTargets(cardEntityList)).willReturn(cardDtoList);

        // When
        final Optional<ColumnDto> result = this.singleColumnDataConverter.convertToTarget(columnEntity);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(columnDto));

        then(this.cardDataListConverter).should().convertToTargets(cardEntityList);
        verifyNoMoreInteractions(this.cardDataListConverter);
    }
}
