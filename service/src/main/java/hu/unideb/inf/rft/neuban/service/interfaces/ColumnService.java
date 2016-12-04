package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;

import java.util.List;

public interface ColumnService extends SingleDataGetService<ColumnDto, Long> {

    List<ColumnDto> getAllByBoardId(Long boardId);

    void save(Long boardId, ColumnDto columnDto) throws BoardNotFoundException, ColumnAlreadyExistsException;

    void update(ColumnDto columnDto) throws ColumnNotFoundException;

    void remove(Long columnId) throws ColumnNotFoundException;
}
