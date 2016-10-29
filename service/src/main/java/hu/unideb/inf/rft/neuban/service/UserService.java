package hu.unideb.inf.rft.neuban.service;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;

public interface UserService extends BaseService<UserEntity, UserDto, Long> {

	UserDto getByUserName(String userName);

}
