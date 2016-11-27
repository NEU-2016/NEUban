package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ColumnService {

    Optional<ColumnDto> get(Long columnId);

    List<ColumnDto> getAllByBoardId(Long boardId);

    void save(Long boardId, ColumnDto columnDto) throws BoardNotFoundException, ColumnAlreadyExistsException;

    void update(ColumnDto columnDto) throws ColumnNotFoundException;

    void remove(Long columnId) throws ColumnNotFoundException;
}
