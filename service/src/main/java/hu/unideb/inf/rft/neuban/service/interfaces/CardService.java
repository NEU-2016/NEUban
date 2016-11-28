package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.exceptions.CardAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnNotFoundException;

import java.util.List;

public interface CardService {

    List<CardDto> getAllByColumnId(Long columnId);

    void save(Long columnId, CardDto cardDto) throws ColumnNotFoundException, CardAlreadyExistsException;

    void update(CardDto cardDto) throws CardNotFoundException;

    void remove(Long cardId) throws CardNotFoundException;
}
