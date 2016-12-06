package hu.unideb.inf.rft.neuban.service.interfaces;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;

import java.util.List;
import java.util.Optional;

public interface UserService extends SingleDataGetService<UserDto, Long> {

	List<UserDto> getAll();

	Optional<UserDto> getByUserName(String userName);

	void saveOrUpdate(UserDto userDto);
}
