package hu.unideb.inf.rft.neuban.service.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.CardRepository;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.exceptions.*;
import hu.unideb.inf.rft.neuban.service.interfaces.ColumnService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

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
public class CardServiceImplTest {

    private static final String COLUMN_TITLE = "Column title";
    private static final long COLUMN_ID = 1L;
    private static final long FIRST_CARD_ID = 1L;
    private static final long SECOND_CARD_ID = 2L;
    private static final long THIRD_CARD_ID = 3L;
    private static final long FOURTH_CARD_ID = 4L;
    private static final String CARD_TITLE = "Card title";

    private final CardEntity firstCardEntity = CardEntity.builder()
            .id(FIRST_CARD_ID)
            .title(CARD_TITLE)
            .build();

    private final CardDto firstCardDto = CardDto.builder()
            .id(FIRST_CARD_ID)
            .title(CARD_TITLE)
            .build();

    private final CardDto secondCardDto = CardDto.builder()
            .id(SECOND_CARD_ID)
            .title(CARD_TITLE)
            .build();

    private final CardDto thirdCardDto = CardDto.builder()
            .id(THIRD_CARD_ID)
            .title(CARD_TITLE)
            .build();

    private final List<CardDto> cardDtoList = Lists.newArrayList(firstCardDto, secondCardDto, thirdCardDto);

    private final ColumnDto columnDto = ColumnDto.builder()
            .id(COLUMN_ID)
            .title(COLUMN_TITLE)
            .cards(cardDtoList)
            .build();

    @InjectMocks
    private CardServiceImpl cardService;

    @Mock
    private ColumnService columnService;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private ModelMapper modelMapper;


    @Test(expected = IllegalArgumentException.class)
    public void getAllByColumnIdShouldThrowIllegalArgumentExceptionWhenParamColumnIdIsNull() {
        // Given
        given(this.columnService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.cardService.getAllByColumnId(null);

        // Then
    }

    @Test
    public void getAllByColumnIdShouldReturnEmptyListWhenColumnDoesNotExist() {
        // Given
        given(this.columnService.get(COLUMN_ID)).willReturn(Optional.empty());

        // When
        final List<CardDto> result = this.cardService.getAllByColumnId(COLUMN_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));

        then(this.columnService).should().get(COLUMN_ID);
        verifyNoMoreInteractions(this.columnService);
    }

    @Test
    public void getAllByColumnIdShouldReturnAListWithThreeElementsWhenColumnExists() {
        // Given
        given(this.columnService.get(COLUMN_ID)).willReturn(Optional.of(columnDto));

        // When
        final List<CardDto> result = this.cardService.getAllByColumnId(COLUMN_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
        assertThat(result, equalTo(cardDtoList));

        then(this.columnService).should().get(COLUMN_ID);
        verifyNoMoreInteractions(this.columnService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionWhenParamColumnIdIsNull() throws CardAlreadyExistsException, ColumnNotFoundException {
        // Given
        given(this.columnService.get(null)).willThrow(IllegalArgumentException.class);

        // When
        this.cardService.save(null, firstCardDto);

        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgumentExceptionWhenParamCardDtoIsNull() throws CardAlreadyExistsException, ColumnNotFoundException {
        // Given

        // When
        this.cardService.save(COLUMN_ID, null);

        // Then
    }

    @Test(expected = ColumnNotFoundException.class)
    public void saveShouldThrowColumnNotFoundExceptionWhenColumnDoesNotExist() throws CardAlreadyExistsException, ColumnNotFoundException {
        // Given
        given(this.columnService.get(COLUMN_ID)).willReturn(Optional.empty());

        // When
        this.cardService.save(COLUMN_ID, firstCardDto);

        // Then
    }

    @Test(expected = CardAlreadyExistsException.class)
    public void saveShouldThrowCardAlreadyExistsExceptionWhenCardAlreadyExists() throws CardAlreadyExistsException, ColumnNotFoundException {
        // Given
        given(this.columnService.get(COLUMN_ID)).willReturn(Optional.of(columnDto));

        // When
        this.cardService.save(COLUMN_ID, firstCardDto);

        // Then
    }

    @Test
    public void saveShouldBeSuccessFulSavingWhenCardDtoIdIsNull() throws CardAlreadyExistsException, ColumnNotFoundException {
        // Given
        final CardDto cardDtoWithoutId = CardDto.builder()
                .id(null)
                .title(CARD_TITLE)
                .build();

        given(this.columnService.get(COLUMN_ID)).willReturn(Optional.of(columnDto));

        final ArgumentCaptor<ColumnDto> columnDtoArgumentCaptor = ArgumentCaptor.forClass(ColumnDto.class);

        // When
        this.cardService.save(COLUMN_ID, cardDtoWithoutId);

        verify(this.columnService).update(columnDtoArgumentCaptor.capture());

        // Then
        assertThat(columnDtoArgumentCaptor.getValue(), notNullValue());
        assertThat(columnDtoArgumentCaptor.getValue().getCards(), notNullValue());
        assertThat(columnDtoArgumentCaptor.getValue().getCards().size(), equalTo(4));
        assertTrue(columnDtoArgumentCaptor.getValue().getCards().contains(cardDtoWithoutId));

        then(this.columnService).should().get(COLUMN_ID);
        then(this.columnService).should().update(columnDto);
        verifyNoMoreInteractions(this.columnService);
    }

    @Test
    public void saveShouldBeSuccessFulSavingWhenCardDtoDoesNotExistOnTheColumn() throws CardAlreadyExistsException, ColumnNotFoundException {
        // Given
        final CardDto newCardDto = CardDto.builder()
                .id(FOURTH_CARD_ID)
                .title(COLUMN_TITLE)
                .build();

        given(this.columnService.get(COLUMN_ID)).willReturn(Optional.of(columnDto));

        final ArgumentCaptor<ColumnDto> columnDtoArgumentCaptor = ArgumentCaptor.forClass(ColumnDto.class);

        // When
        this.cardService.save(COLUMN_ID, newCardDto);

        verify(this.columnService).update(columnDtoArgumentCaptor.capture());

        // Then
        assertThat(columnDtoArgumentCaptor.getValue(), notNullValue());
        assertThat(columnDtoArgumentCaptor.getValue().getCards(), notNullValue());
        assertThat(columnDtoArgumentCaptor.getValue().getCards().size(), equalTo(4));
        assertTrue(columnDtoArgumentCaptor.getValue().getCards().contains(newCardDto));

        then(this.columnService).should().get(COLUMN_ID);
        then(this.columnService).should().update(columnDto);
        verifyNoMoreInteractions(this.columnService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowIllegalArgumentExceptionWhenParamCardDtoIsNull() throws CardNotFoundException {
        // Given

        // When
        this.cardService.update(null);

        // Then
    }

    @Test(expected = CardNotFoundException.class)
    public void updateShouldThrowCardNotFoundExceptionWhenCardIdIsNull() throws CardNotFoundException {
        // Given
        final CardDto cardDto = CardDto.builder().id(null).build();

        // When
        this.cardService.update(cardDto);

        // Then
    }

    @Test
    public void updateShouldBeSuccessFulUpdatingWhenCardExists() throws CardNotFoundException {
        // Given
        given(this.modelMapper.map(firstCardDto, CardEntity.class)).willReturn(firstCardEntity);

        // When
        this.cardService.update(firstCardDto);

        // Then
        then(this.cardRepository).should().saveAndFlush(firstCardEntity);
        then(this.modelMapper).should().map(firstCardDto, CardEntity.class);
        verifyNoMoreInteractions(this.cardRepository, this.modelMapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeShouldThrowIllegalArgumentExceptionWhenParamCardIdDoesNotExist() throws CardNotFoundException {
        // Given

        // When
        this.cardService.remove(null);

        // Then
    }

    @Test(expected = CardNotFoundException.class)
    public void removeShouldThrowColumnNotFoundExceptionWhenColumnDoesNotExist() throws CardNotFoundException {
        // Given
        given(this.cardRepository.findOne(FIRST_CARD_ID)).willReturn(null);

        // When
        this.cardService.remove(FIRST_CARD_ID);

        // Then
    }

    @Test
    public void removeShouldBeSuccessFulDeletingWhenCardExists() throws CardNotFoundException {
        // Given
        given(this.cardRepository.findOne(FIRST_CARD_ID)).willReturn(firstCardEntity);

        // When
        this.cardService.remove(FIRST_CARD_ID);

        // Then
        then(this.cardRepository).should().findOne(FIRST_CARD_ID);
        then(this.cardRepository).should().delete(FIRST_CARD_ID);
        verifyNoMoreInteractions(this.cardRepository);
    }
}
