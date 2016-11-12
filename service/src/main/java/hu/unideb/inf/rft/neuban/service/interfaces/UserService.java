package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;

import java.util.Optional;

public interface UserService extends BaseService<UserEntity, UserDto, Long> {

	Optional<UserDto> getByUserName(String userName);

}
