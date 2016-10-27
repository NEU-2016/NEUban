package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.service.dto.UserDto;

public interface UserService extends BaseService<UserEntity, UserDto, Long> {

	UserDto getByUserName(String userName) throws Exception;

}
