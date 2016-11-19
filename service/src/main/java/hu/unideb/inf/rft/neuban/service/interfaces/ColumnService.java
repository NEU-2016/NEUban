package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnNotFoundException;

import java.util.List;

public interface ColumnService {

    List<ColumnDto> findAllByBoardId(Long boardId);

    void saveOrUpdate(Long boardId, ColumnDto columnDto) throws BoardNotFoundException;

    void remove(Long columnId) throws ColumnNotFoundException;
}
