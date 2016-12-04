package hu.unideb.inf.rft.neuban.service.interfaces;

import java.util.List;

import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentBoardIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUserIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.RelationNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;

public interface BoardService extends SingleDataGetService<BoardDto, Long> {

	List<BoardDto> getAllByUserId(Long userId);
	
	void remove(Long boardId) throws BoardNotFoundException;

	void update(BoardDto boardDto) throws BoardNotFoundException;

	void removeUserFromBoardByUserIdAndByBoardId(Long userId, Long boardId)
			throws NonExistentBoardIdException, RelationNotFoundException, NonExistentUserIdException;

	void createBoard(Long userId, String title) throws NonExistentUserIdException;

	void addUserToBoardByUserIdAndByBoardId(Long userId, Long boardId)
			throws NonExistentBoardIdException, NonExistentUserIdException;
	
	

}
