package hu.unideb.inf.rft.neuban.service.handler;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentBoardIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.NonExistentUserIdException;
import hu.unideb.inf.rft.neuban.service.exceptions.RelationNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class BoardHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    void removeUserFromBoardByUserIdAndByBoardId(Long userId, Long boardId)
            throws NonExistentBoardIdException, RelationNotFoundException, NonExistentUserIdException {

        Assert.notNull(userId);
        Assert.notNull(boardId);

        UserDto userDto = Optional.ofNullable(this.userService.getById(userId))
                .orElseThrow(() -> new NonExistentUserIdException(userId));

        BoardDto boardDto = this.boardService.get(boardId)
                .orElseThrow(() -> new NonExistentBoardIdException(boardId));

        if (userDto.getBoards() == null
                || !userDto.getBoards().removeIf(userBoards -> userBoards.getId().equals((boardDto.getId())))) {
            throw new RelationNotFoundException();
        }
        userService.saveOrUpdate(userDto);
    }

    void addUserToBoardByUserIdAndByBoardId(Long userId, Long boardId) throws NonExistentBoardIdException, NonExistentUserIdException {

        Assert.notNull(userId);
        Assert.notNull(boardId);

        UserDto userDto = Optional.ofNullable(this.userService.getById(userId))
                .orElseThrow(() -> new NonExistentUserIdException(userId));

        BoardDto boardDto = this.boardService.get(boardId)
                .orElseThrow(() -> new NonExistentBoardIdException(boardId));

        if (userDto.getBoards() != null) {
            userDto.getBoards().add(boardDto);
        } else {
            List<BoardDto> boardList = Lists.newArrayList();
            boardList.add(boardDto);
            userDto.setBoards(boardList);
        }

        userService.saveOrUpdate(userDto);

    }

    void createBoardByDefaultUserIdAndByTitle(Long userId, String title) throws NonExistentUserIdException {

        Assert.notNull(userId);
        Assert.notNull(title);

        UserDto userDto = Optional.ofNullable(this.userService.getById(userId))
                .orElseThrow(() -> new NonExistentUserIdException(userId));

        BoardDto boardDto = BoardDto.builder().title(title).build();

        //boardService.saveOrUpdate(boardDto);

        if (userDto.getBoards() != null) {
            userDto.getBoards().add(boardDto);
        } else {
            List<BoardDto> boardList = Lists.newArrayList();
            boardList.add(boardDto);
            userDto.setBoards(boardList);
        }
        userService.saveOrUpdate(userDto);
    }

}
