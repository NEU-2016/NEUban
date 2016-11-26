package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> get(Long userId);

    Optional<UserDto> getByUserName(String userName);

    void saveOrUpdate(UserDto userDto);
}
