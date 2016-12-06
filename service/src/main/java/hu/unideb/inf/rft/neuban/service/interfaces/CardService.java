package hu.unideb.inf.rft.neuban.service.interfaces;

import java.util.List;

import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.exceptions.CardAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.UserAlreadyExistsOnCardException;
import hu.unideb.inf.rft.neuban.service.exceptions.UserNotFoundOnCardException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.UserNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;

public interface CardService extends SingleDataGetService<CardDto, Long>, SingleDataUpdateService<CardDto> {

    List<CardDto> getAllByColumnId(Long columnId);

    void save(Long columnId, CardDto cardDto) throws DataNotFoundException, CardAlreadyExistsException;

    void remove(Long cardId) throws CardNotFoundException;

    void addUserToCard(Long userId, Long cardId) throws UserNotFoundException, CardNotFoundException, UserAlreadyExistsOnCardException;

    void removeUserFromCard(Long userId, Long cardId) throws UserNotFoundException, CardNotFoundException, UserNotFoundOnCardException;
}
