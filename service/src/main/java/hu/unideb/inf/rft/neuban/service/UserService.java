package hu.unideb.inf.rft.neuban.service;

import hu.unideb.inf.rft.service.dto.UserDto;

public interface UserService extends BaseService<UserDto> {
	
	public UserDto getByUserName(String userName) throws Exception;
	
}
