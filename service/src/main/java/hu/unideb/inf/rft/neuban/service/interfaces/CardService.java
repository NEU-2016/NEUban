package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.exceptions.*;

import java.util.List;
import java.util.Optional;

public interface CardService {

    Optional<CardDto> get(Long cardId);

    List<CardDto> getAllByColumnId(Long columnId);

    void save(Long columnId, CardDto cardDto) throws ColumnNotFoundException, CardAlreadyExistsException;

    void update(CardDto cardDto) throws CardNotFoundException;

    void remove(Long cardId) throws CardNotFoundException;

    void addUserToCard(Long userId, Long cardId) throws UserNotFoundException, CardNotFoundException, UserAlreadyExistsOnCardException;

    void removeUserFromCard(Long userId, Long cardId) throws UserNotFoundException, CardNotFoundException, UserNotFoundOnCardException;
}
