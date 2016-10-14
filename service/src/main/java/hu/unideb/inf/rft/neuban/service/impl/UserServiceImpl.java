package hu.unideb.inf.rft.neuban.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.UserService;
import hu.unideb.inf.rft.service.dto.UserDto;

/**
 * Service impl for userEntity TODO modelMapper needs to be singetlon
 * 
 * @author Headswitcher
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserDto> getAll() throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		Type listType = new TypeToken<List<UserDto>>() {
		}.getType();
		return modelMapper.map(userRepository.findAll(), listType);
	}

	@Override
	public UserDto getById(Long id) throws Exception {
		UserEntity userEntity = this.userRepository.findUserById(id);
		if (userEntity == null) {
		return null;
		}
		return modelMapper.map(userEntity, UserDto.class);
	}

	@Override
	public void removeById(Long id) throws Exception {
		userRepository.delete(id);
	}

	@Override
	public UserDto getByUserName(String userName) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userRepository.findUserByUserName(userName), UserDto.class);
	}
}
