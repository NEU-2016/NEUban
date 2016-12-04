package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.exceptions.data.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.ColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;

import java.util.List;

public interface ColumnService extends SingleDataGetService<ColumnDto, Long>, SingleDataUpdateService<ColumnDto> {

    List<ColumnDto> getAllByBoardId(Long boardId);

    void save(Long boardId, ColumnDto columnDto) throws DataNotFoundException, ColumnAlreadyExistsException;

    void remove(Long columnId) throws ColumnNotFoundException;
}
