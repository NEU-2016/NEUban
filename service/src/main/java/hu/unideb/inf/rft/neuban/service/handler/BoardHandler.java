package hu.unideb.inf.rft.neuban.service.handler;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import hu.unideb.inf.rft.neuban.service.BoardService;
import hu.unideb.inf.rft.neuban.service.UserService;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.RelationNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.UserNotFoundException;

@Service
public class BoardHandler {

	@Autowired
	private UserService userService;

	@Autowired
	private BoardService boardService;

	void removeUserFromBoardByUserIdAndByBoardId(Long userId, Long boardId)
			throws UserNotFoundException, BoardNotFoundException, RelationNotFoundException {

		Assert.notNull(userId);
		Assert.notNull(boardId);

		UserDto userDto = userService.getById(userId);

		if (userDto == null) {
			throw new UserNotFoundException();
		}

		BoardDto boardDto = boardService.getById(boardId);

		if (boardDto == null) {
			throw new BoardNotFoundException();
		}
		if (userDto.getBoards() == null
				|| !userDto.getBoards().removeIf(userBoards -> userBoards.getId().equals((boardDto.getId())))) {
			throw new RelationNotFoundException();
		}
		userService.saveOrUpdate(userDto);
	}

	void addUserToBoardByUserIdAndByBoardId(Long userId, Long boardId)
			throws UserNotFoundException, BoardNotFoundException {

		Assert.notNull(userId);
		Assert.notNull(boardId);
		UserDto userDto = userService.getById(userId);

		if (userDto == null) {
			throw new UserNotFoundException();
		}

		BoardDto boardDto = boardService.getById(boardId);

		if (boardDto == null) {
			throw new BoardNotFoundException();
		}

		if (userDto.getBoards() != null) {
			userDto.getBoards().add(boardDto);
		} else {
			Collection<BoardDto> boardCollection = new ArrayList<>();
			boardCollection.add(boardDto);
			userDto.setBoards(boardCollection);
		}

		userService.saveOrUpdate(userDto);

	}

	void createBoardByDefaultUserIdAndByTitle(Long userId, String title) throws UserNotFoundException {

		Assert.notNull(userId);
		Assert.notNull(title);

		UserDto userDto = userService.getById(userId);

		if (userDto == null) {
			throw new UserNotFoundException();
		}

		BoardDto boardDto = BoardDto.builder().title(title).build();

		boardService.saveOrUpdate(boardDto);

		if (userDto.getBoards() != null) {
			userDto.getBoards().add(boardDto);
		} else {
			Collection<BoardDto> boardCollection = new ArrayList<>();
			boardCollection.add(boardDto);
			userDto.setBoards(boardCollection);
		}
		userService.saveOrUpdate(userDto);

	}

}
