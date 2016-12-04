package hu.unideb.inf.rft.neuban.service.converter.impl;

import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SingleCardDataConverterTest {

    private static final long CARD_ID = 1L;
    private static final String CARD_TITLE = "Card title";
    private static final String CARD_DESCRIPTION = "Card description";

    private final CardEntity cardEntity = CardEntity.builder()
            .id(CARD_ID)
            .title(CARD_TITLE)
            .description(CARD_DESCRIPTION)
            .build();

    private final CardDto cardDto = CardDto.builder()
            .id(CARD_ID)
            .title(CARD_TITLE)
            .description(CARD_DESCRIPTION)
            .build();

    @InjectMocks
    private SingleCardDataConverter singleCardDataConverter;

    @Test
    public void convertToSourceShouldReturnEmptyOptionalWhenParameterCardDtoIsNull() {
        // Given

        // When
        final Optional<CardEntity> result = this.singleCardDataConverter.convertToSource(null);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void convertToSourceShouldReturnOptionalWithValueWhenParameterCardDtoIsNonNull() {
        // Given

        // When
        final Optional<CardEntity> result = this.singleCardDataConverter.convertToSource(cardDto);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(cardEntity));
    }

    @Test
    public void convertToTargetShouldReturnEmptyOptionalWhenParameterCardEntityIsNull() {
        // Given

        // When
        final Optional<CardDto> result = this.singleCardDataConverter.convertToTarget(null);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void convertToTargetShouldReturnOptionalWithValueWhenParameterCardEntityIsNonNull() {
        // Given

        // When
        final Optional<CardDto> result = this.singleCardDataConverter.convertToTarget(cardEntity);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(cardDto));
    }
}
