package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.UserAlreadyExistsOnCardException;
import hu.unideb.inf.rft.neuban.service.exceptions.UserNotFoundOnCardException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.UserNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;

import java.util.List;
import java.util.Optional;

public interface UserService extends SingleDataGetService<UserDto, Long> {

    List<UserDto> getAll();

    Optional<UserDto> getByUserName(String userName);

    void saveOrUpdate(UserDto userDto);

    void addUserToCard(Long userId, Long cardId) throws DataNotFoundException, UserAlreadyExistsOnCardException;

    void removeUserFromCard(Long userId, Long cardId) throws DataNotFoundException, UserNotFoundOnCardException;
}
