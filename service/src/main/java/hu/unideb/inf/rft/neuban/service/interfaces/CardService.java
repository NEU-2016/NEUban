package hu.unideb.inf.rft.neuban.service.interfaces;

import java.util.List;

import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.exceptions.CardAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.ParentBoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.ParentColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;

public interface CardService extends SingleDataGetService<CardDto, Long>, SingleDataUpdateService<CardDto> {

	List<CardDto> getAllByColumnId(Long columnId);

	void save(Long columnId, CardDto cardDto) throws DataNotFoundException, CardAlreadyExistsException;

	void remove(Long cardId) throws CardNotFoundException;

	void moveCardToAnotherColumn(Long columnId, Long cardId) throws DataNotFoundException, ColumnAlreadyExistsException;

	public void moveCardToAnotherColumnByDirection(final Long cardId, Boolean direction)
			throws ParentColumnNotFoundException, ParentBoardNotFoundException;

}