package hu.unideb.inf.rft.neuban.service.impl;

import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataGetServiceBeanNameProvider.SINGLE_CARD_DATA_GET_SERVICE;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataUpdateServiceBeanNameProvider.SINGLE_CARD_DATA_UPDATE_SERVICE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

import hu.unideb.inf.rft.neuban.persistence.repositories.CardRepository;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.exceptions.CardAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.ColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.ColumnNotInSameBoardException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.CardService;
import hu.unideb.inf.rft.neuban.service.interfaces.ColumnService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private BoardService boardService;
	@Autowired
	private ColumnService columnService;
	@Autowired
	private CardRepository cardRepository;

	@Autowired
	@Qualifier(SINGLE_CARD_DATA_GET_SERVICE)
	private SingleDataGetService<CardDto, Long> singleCardDataGetService;

	@Autowired
	@Qualifier(SINGLE_CARD_DATA_UPDATE_SERVICE)
	private SingleDataUpdateService<CardDto> singleCardDataUpdateService;

	@Transactional(readOnly = true)
	@Override
	public Optional<CardDto> get(final Long cardId) {
		return this.singleCardDataGetService.get(cardId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<CardDto> getAllByColumnId(final Long columnId) {
		final Optional<ColumnDto> columnDtoOptional = this.columnService.get(columnId);

		if (columnDtoOptional.isPresent()) {
			return columnDtoOptional.get().getCards();
		}
		return Lists.newArrayList();
	}

	@Transactional
	@Override
	public void save(final Long columnId, final CardDto cardDto)
			throws DataNotFoundException, CardAlreadyExistsException {
		Assert.notNull(cardDto);
		final ColumnDto columnDto = this.columnService.get(columnId)
				.orElseThrow(() -> new ColumnNotFoundException(String.valueOf(columnId)));

		if (cardDto.getId() == null
				|| columnDto.getCards().stream().noneMatch(c -> c.getId().equals(cardDto.getId()))) {
			columnDto.getCards().add(cardDto);
		} else {
			throw new CardAlreadyExistsException(String.valueOf(cardDto.getId()));
		}
		this.columnService.update(columnDto);
	}

	@Transactional
	@Override
	public void update(final CardDto cardDto) throws DataNotFoundException {
		this.singleCardDataUpdateService.update(cardDto);
	}

	@Transactional
	@Override
	public void remove(final Long cardId) throws CardNotFoundException {
		Assert.notNull(cardId);

		Optional.ofNullable(this.cardRepository.findOne(cardId))
				.orElseThrow(() -> new CardNotFoundException(String.valueOf(cardId)));

		this.cardRepository.delete(cardId);
	}

	@Transactional
	@Override
	public void moveCardToAnotherColumn(final Long columnId, final Long cardId, final Long boardId)
			throws DataNotFoundException, ColumnAlreadyExistsException {

		Assert.notNull(columnId);
		Assert.notNull(cardId);
		Assert.notNull(boardId);

		final Optional<BoardDto> boardDto = Optional.ofNullable(this.boardService.get(boardId))
				.orElseThrow(() -> new BoardNotFoundException(String.valueOf(boardId)));

		final Optional<ColumnDto> columnDto = Optional.ofNullable(this.columnService.get(columnId))
				.orElseThrow(() -> new ColumnNotFoundException(String.valueOf(columnId)));

		final Optional<CardDto> cardDto = Optional.ofNullable(get(cardId))
				.orElseThrow(() -> new CardNotFoundException(String.valueOf(cardId)));

		final boolean isBoardContainsColumn = boardDto.get().getColumns().stream()
				.anyMatch(id -> id.getId().equals(columnDto.get().getId()));

		if (isBoardContainsColumn) {
			columnDto.get().getCards().add(cardDto.get());
			columnService.save(boardId, columnDto.get());
		} else {
			throw new ColumnNotInSameBoardException(columnId.toString());
		}

	}
}
