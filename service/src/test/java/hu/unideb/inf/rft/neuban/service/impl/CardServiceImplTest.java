package hu.unideb.inf.rft.neuban.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.BoardRepository;
import hu.unideb.inf.rft.neuban.persistence.repositories.CardRepository;
import hu.unideb.inf.rft.neuban.persistence.repositories.ColumnRepository;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.CardAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.ColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.ParentBoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.ParentColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.impl.shared.SingleDataUpdateServiceImpl;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.ColumnService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceImplTest {

	private static final long BOARD_ID = 1L;
	private static final long SECOND_BOARD_ID = 2L;
	private static final String BOARD_TITLE = "Test title";

	private static final String COLUMN_TITLE = "Column title";
	private static final long COLUMN_ID = 1L;
	private static final long SECOND_COLUMN_ID = 2L;
	private static final long FIRST_CARD_ID = 1L;
	private static final long SECOND_CARD_ID = 2L;
	private static final long THIRD_CARD_ID = 3L;
	private static final long FOURTH_CARD_ID = 4L;
	private static final String CARD_TITLE = "Card title";

	private static final long FIRST_USER_ID = 1L;
	private static final long SECOND_USER_ID = 2L;
	private static final long THIRD_USER_ID = 3L;
	private static final long FOURTH_USER_ID = 4L;
	private static final String USERNAME = "Username";
	private static final String PASSWORD_HASH = "PasswordHash";

	private final CardEntity firstCardEntity = CardEntity.builder().id(FIRST_CARD_ID).title(CARD_TITLE).build();

	private final CardDto firstCardDto = CardDto.builder().id(FIRST_CARD_ID).title(CARD_TITLE).build();

	private final CardDto secondCardDto = CardDto.builder().id(SECOND_CARD_ID).title(CARD_TITLE).build();

	private final CardDto thirdCardDto = CardDto.builder().id(THIRD_CARD_ID).title(CARD_TITLE).build();

	private final CardDto fourthCardDto = CardDto.builder().id(FOURTH_CARD_ID).title(CARD_TITLE).build();

	private final List<CardDto> cardDtoList = Lists.newArrayList(firstCardDto, secondCardDto, thirdCardDto);

	private final List<CardDto> cardDtoListAfterSave = Lists.newArrayList(firstCardDto, secondCardDto, thirdCardDto,
			fourthCardDto);

	private final ColumnDto columnDto = ColumnDto.builder().id(COLUMN_ID).title(COLUMN_TITLE).cards(cardDtoList)
			.build();

	private final UserDto firstUserDto = UserDto.builder().id(FIRST_USER_ID).userName(USERNAME).password(PASSWORD_HASH)
			.build();

	private final UserDto secondUserDto = UserDto.builder().id(SECOND_USER_ID).userName(USERNAME)
			.password(PASSWORD_HASH).build();

	private final UserDto thirdUserDto = UserDto.builder().id(THIRD_USER_ID).userName(USERNAME).password(PASSWORD_HASH)
			.build();

	private final UserDto fourthUserDto = UserDto.builder().id(FOURTH_USER_ID).userName(USERNAME)
			.password(PASSWORD_HASH).build();

	private final CardDto actualCardDto = CardDto.builder().id(FIRST_CARD_ID).title(CARD_TITLE).build();
	
	private final BoardDto boardDtoWithoutColumn = BoardDto.builder().id(BOARD_ID).title(BOARD_TITLE)
			.columns(Collections.emptyList()).build();

	private final BoardDto boardDtoWithColumn = BoardDto.builder().id(BOARD_ID).title(BOARD_TITLE)
			.columns(Arrays.asList(columnDto)).build();

	private final CardEntity cardEntity = CardEntity.builder().id(FIRST_CARD_ID).title(CARD_TITLE)
			.comments(Collections.emptyList()).build();

	private final ColumnEntity originColumnEntity = ColumnEntity.builder().id(SECOND_COLUMN_ID).title(COLUMN_TITLE)
			.cards(Arrays.asList(cardEntity)).build();


	private final ColumnEntity columnEntityParentOfCardEntity = ColumnEntity.builder().id(COLUMN_ID).title(COLUMN_TITLE)
			.cards(Arrays.asList(cardEntity)).build();

	private final ColumnDto targetColumnDto = ColumnDto.builder().id(COLUMN_ID).title(COLUMN_TITLE)
			.cards(Collections.emptyList()).build();

	private final ColumnDto originColumnDto = ColumnDto.builder().id(COLUMN_ID).title(COLUMN_TITLE)
			.cards(Arrays.asList(actualCardDto)).build();
	
	private final ColumnEntity targetColumnEntity = ColumnEntity.builder().id(SECOND_COLUMN_ID).title(COLUMN_TITLE)
			.cards(Collections.emptyList()).build();

	private final BoardEntity targetBoard = BoardEntity.builder().id(BOARD_ID).title(BOARD_TITLE)
			.columns(Arrays.asList(targetColumnEntity,originColumnEntity)).build();

	private final BoardEntity originBoard = BoardEntity.builder().id(BOARD_ID).title(BOARD_TITLE)
			.columns(Arrays.asList(targetColumnEntity,originColumnEntity)).build();
	

	@InjectMocks
	private CardServiceImpl cardService;

	@Mock
	private BoardService boardService;
	@Mock
	private UserService userService;
	@Mock
	private ColumnService columnService;
	@Mock
	private CardRepository cardRepository;
	@Mock
	private BoardRepository boardRepository;
	@Mock
	private ColumnRepository columnRepository;
	@Mock
	private SingleDataGetService<CardDto, Long> singleCardDataGetService;
	@Mock
	private SingleDataUpdateServiceImpl<CardEntity, CardDto, Long, CardNotFoundException> singleCardDataUpdateService;

	@Test(expected = IllegalArgumentException.class)
	public void getShouldThrowIllegalArgumentExceptionWhenParamUserIdIsNull() {
		// Given
		given(this.singleCardDataGetService.get(null)).willThrow(IllegalArgumentException.class);

		// When
		this.cardService.get(null);

		// Then
	}

	@Test
	public void getShouldReturnWithEmptyOptionalWhenCardDoesNotExist() {
		// Given
		given(this.singleCardDataGetService.get(FIRST_CARD_ID)).willReturn(Optional.empty());

		// When
		final Optional<CardDto> result = this.cardService.get(FIRST_CARD_ID);

		// Then
		assertThat(result, notNullValue());
		assertThat(result.isPresent(), is(false));

		then(this.singleCardDataGetService).should().get(FIRST_CARD_ID);
		verifyNoMoreInteractions(this.singleCardDataGetService);
	}

	@Test
	public void getShouldReturnWithNonEmptyOptionalWhenCardDoesExist() {
		// Given
		given(this.singleCardDataGetService.get(FIRST_CARD_ID)).willReturn(Optional.of(firstCardDto));

		// When
		final Optional<CardDto> result = this.cardService.get(FIRST_CARD_ID);

		// Then
		assertThat(result, notNullValue());
		assertThat(result.isPresent(), is(true));
		assertThat(result.get(), equalTo(firstCardDto));

		then(this.singleCardDataGetService).should().get(FIRST_CARD_ID);
		verifyNoMoreInteractions(this.singleCardDataGetService);
	}

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
	public void saveShouldThrowIllegalArgumentExceptionWhenParamColumnIdIsNull()
			throws CardAlreadyExistsException, DataNotFoundException {
		// Given
		given(this.columnService.get(null)).willThrow(IllegalArgumentException.class);

		// When
		this.cardService.save(null, firstCardDto);

		// Then
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveShouldThrowIllegalArgumentExceptionWhenParamCardDtoIsNull()
			throws CardAlreadyExistsException, DataNotFoundException {
		// Given

		// When
		this.cardService.save(COLUMN_ID, null);

		// Then
	}

	@Test(expected = ColumnNotFoundException.class)
	public void saveShouldThrowColumnNotFoundExceptionWhenColumnDoesNotExist()
			throws CardAlreadyExistsException, DataNotFoundException {
		// Given
		given(this.columnService.get(COLUMN_ID)).willReturn(Optional.empty());

		// When
		this.cardService.save(COLUMN_ID, firstCardDto);

		// Then
	}

	@Test(expected = CardAlreadyExistsException.class)
	public void saveShouldThrowCardAlreadyExistsExceptionWhenCardAlreadyExists()
			throws CardAlreadyExistsException, DataNotFoundException {
		// Given
		given(this.columnService.get(COLUMN_ID)).willReturn(Optional.of(columnDto));

		// When
		this.cardService.save(COLUMN_ID, firstCardDto);

		// Then
	}

	@Test
	public void saveShouldBeSuccessfulSavingWhenCardDtoIdIsNull()
			throws CardAlreadyExistsException, DataNotFoundException {
		// Given
		final CardDto cardDtoWithoutId = CardDto.builder().id(null).title(CARD_TITLE).build();

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
	public void saveShouldBeSuccessfulSavingWhenCardDtoDoesNotExistOnTheColumn()
			throws CardAlreadyExistsException, DataNotFoundException {
		// Given
		final CardDto newCardDto = CardDto.builder().id(FOURTH_CARD_ID).title(COLUMN_TITLE).build();

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
	public void updateShouldThrowIllegalArgumentExceptionWhenParamCardDtoIsNull() throws DataNotFoundException {
		// Given
		doThrow(IllegalArgumentException.class).when(this.singleCardDataUpdateService).update(null);

		// When
		this.cardService.update(null);

		// Then
	}

	@Test(expected = CardNotFoundException.class)
	public void updateShouldThrowCardNotFoundExceptionWhenCardIdIsNull() throws DataNotFoundException {
		// Given
		final CardDto cardDto = CardDto.builder().id(null).build();

		doThrow(CardNotFoundException.class).when(this.singleCardDataUpdateService).update(cardDto);

		// When
		this.cardService.update(cardDto);

		// Then
	}

	@Test
	public void updateShouldBeSuccessfulUpdatingWhenCardExists() throws DataNotFoundException {
		// Given
		doNothing().when(this.singleCardDataUpdateService).update(firstCardDto);

		// When
		this.cardService.update(firstCardDto);

		// Then
		then(this.singleCardDataUpdateService).should().update(firstCardDto);
		verifyNoMoreInteractions(this.singleCardDataUpdateService);
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
	public void removeShouldBeSuccessfulDeletingWhenCardExists() throws CardNotFoundException {
		// Given
		given(this.cardRepository.findOne(FIRST_CARD_ID)).willReturn(firstCardEntity);

		// When
		this.cardService.remove(FIRST_CARD_ID);

		// Then
		then(this.cardRepository).should().findOne(FIRST_CARD_ID);
		then(this.cardRepository).should().delete(FIRST_CARD_ID);
		verifyNoMoreInteractions(this.cardRepository);
	}

	@Test(expected = IllegalArgumentException.class)
	public void moveCardToAnotherColumnShouldThrowIllegalArgumentExceptionWhenParamColumnIddDoesNotExist()
			throws DataNotFoundException, ColumnAlreadyExistsException {
		// Given

		// When
		this.cardService.moveCardToAnotherColumn(null, FIRST_CARD_ID);

		// Then
	}

	@Test(expected = IllegalArgumentException.class)
	public void moveCardToAnotherColumnShouldThrowIllegalArgumentExceptionWhenParamCardIdDoesNotExist()
			throws DataNotFoundException, ColumnAlreadyExistsException {
		// Given

		// When
		this.cardService.moveCardToAnotherColumn(COLUMN_ID, null);

		// Then
	}

	@Test(expected = ParentBoardNotFoundException.class)
	public void moveCardToAnotherColumnShouldThrowParentBoardNotFoundExceptionWhenParentBoardDoesNotExist()
			throws DataNotFoundException, ColumnAlreadyExistsException {
		// Given
		given(this.boardRepository.findParentBoardbyColumnId(COLUMN_ID))
		.willReturn(null);
		// When
		this.cardService.moveCardToAnotherColumn(COLUMN_ID, FIRST_CARD_ID);

		// Then
	}

	@Test(expected = ParentColumnNotFoundException.class)
	public void moveCardToAnotherColumnShouldThrowParentColumnNotFoundExceptionWhenParentColumnDoesNotExist()
			throws DataNotFoundException, ColumnAlreadyExistsException {
		// Given
		given(this.boardRepository.findParentBoardbyColumnId(COLUMN_ID))
		.willReturn(targetBoard);
		given(this.columnRepository.findParentColumnByCardId(FIRST_CARD_ID)).willReturn(null);
		// When
		this.cardService.moveCardToAnotherColumn(COLUMN_ID, FIRST_CARD_ID);

		// Then
	}
	
	@Test(expected = ParentBoardNotFoundException.class)
	public void moveCardToAnotherColumnShouldThrowParentBoardNotFoundExceptionWhenParentBoardEntityOfParentColumnEntityOfCardDoesNotExist()
			throws DataNotFoundException, ColumnAlreadyExistsException {
		// Given
		given(this.boardRepository.findParentBoardbyColumnId(COLUMN_ID))
		.willReturn(targetBoard);
		given(this.columnRepository.findParentColumnByCardId(FIRST_CARD_ID)).willReturn(originColumnEntity);
		given(this.boardRepository.findParentBoardbyColumnId(originColumnEntity.getId()))
		.willReturn(null);
		// When
		this.cardService.moveCardToAnotherColumn(COLUMN_ID, FIRST_CARD_ID);

		// Then
	}
	
	@Test(expected = ColumnNotFoundException.class)
	public void moveCardToAnotherColumnShouldThrowColumnNotFoundExceptionWhenNewColumnDtoDoesNotExist()
			throws DataNotFoundException, ColumnAlreadyExistsException {
		// Given
		given(this.boardRepository.findParentBoardbyColumnId(COLUMN_ID))
		.willReturn(targetBoard);
		given(this.columnRepository.findParentColumnByCardId(FIRST_CARD_ID)).willReturn(originColumnEntity);
		given(this.boardRepository.findParentBoardbyColumnId(originColumnEntity.getId()))
		.willReturn(originBoard);
		given(this.columnService.get(COLUMN_ID)).willReturn(Optional.empty());
		
		// When
		this.cardService.moveCardToAnotherColumn(COLUMN_ID, FIRST_CARD_ID);

		// Then
	}
	
	@Test(expected = ColumnNotFoundException.class)
	public void moveCardToAnotherColumnShouldThrowColumnNotFoundExceptionWhenOldColumnDtoDoesNotExist()
			throws DataNotFoundException, ColumnAlreadyExistsException {
		// Given
		given(this.boardRepository.findParentBoardbyColumnId(COLUMN_ID))
		.willReturn(targetBoard);
		given(this.columnRepository.findParentColumnByCardId(FIRST_CARD_ID)).willReturn(originColumnEntity);
		given(this.boardRepository.findParentBoardbyColumnId(originColumnEntity.getId()))
		.willReturn(originBoard);
		given(this.columnService.get(COLUMN_ID)).willReturn(Optional.of(targetColumnDto));
		
		given(this.columnService.get(COLUMN_ID)).willReturn(Optional.of(originColumnDto));	
		
		given(this.cardService.get(FIRST_CARD_ID)).willReturn(Optional.empty());
		// When
		this.cardService.moveCardToAnotherColumn(COLUMN_ID, FIRST_CARD_ID);

		// Then
	}

	@Test
	public void moveCardToAnotherColumnShouldColumnNotInSameBoardExceptionWhenBoardDoesNotHaveColumn()
			throws DataNotFoundException, ColumnAlreadyExistsException {
		// Given
		given(this.boardRepository.findParentBoardbyColumnId(COLUMN_ID))
				.willReturn(targetBoard);
		
		given(this.columnRepository.findParentColumnByCardId(FIRST_CARD_ID)).willReturn(originColumnEntity);
		
		given(this.boardRepository.findParentBoardbyColumnId(originColumnEntity.getId()))
				.willReturn(originBoard);
		
		given(this.columnService.get(COLUMN_ID)).willReturn(Optional.of(targetColumnDto));
		
		given(this.columnService.get(originColumnEntity.getId())).willReturn(Optional.of(originColumnDto));
		
		given(this.cardService.get(FIRST_CARD_ID)).willReturn(Optional.of(actualCardDto));
		
		// When
		this.cardService.moveCardToAnotherColumn(COLUMN_ID, FIRST_CARD_ID);
		
		
	}

}