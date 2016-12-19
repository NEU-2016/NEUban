package hu.unideb.inf.rft.neuban.service.interfaces;

import java.util.List;

import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentBoardIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUserIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.RelationNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;

public interface BoardService extends SingleDataGetService<BoardDto, Long>, SingleDataUpdateService<BoardDto> {

    List<BoardDto> getAllByUserId(Long userId);

    void remove(Long boardId) throws DataNotFoundException;

    void removeUserFromBoardByUserIdAndByBoardId(Long userId, Long boardId)
            throws NonExistentBoardIdException, RelationNotFoundException, NonExistentUserIdException, DataNotFoundException;

    void createBoard(Long userId, String title) throws NonExistentUserIdException, DataNotFoundException;

    void addUserToBoardByUserIdAndByBoardId(Long userId, Long boardId)
            throws NonExistentBoardIdException, NonExistentUserIdException, DataNotFoundException;


}
