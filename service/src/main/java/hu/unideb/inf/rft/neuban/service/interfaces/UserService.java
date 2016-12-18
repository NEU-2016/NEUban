package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.UserAlreadyExistsOnCardException;
import hu.unideb.inf.rft.neuban.service.exceptions.UserNotFoundOnCardException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;

import java.util.List;
import java.util.Optional;

public interface UserService extends SingleDataGetService<UserDto, Long>, SingleDataUpdateService<UserDto> {

    List<UserDto> getAllByBoardId(Long boardId);

    Optional<UserDto> getByUserName(String userName);

    void create(UserDto userDto);

    void addUserToCard(Long userId, Long cardId) throws DataNotFoundException, UserAlreadyExistsOnCardException;

    void removeUserFromCard(Long userId, Long cardId) throws DataNotFoundException, UserNotFoundOnCardException;
}
