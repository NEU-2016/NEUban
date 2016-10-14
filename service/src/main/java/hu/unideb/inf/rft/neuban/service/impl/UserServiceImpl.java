package hu.unideb.inf.rft.neuban.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.BaseServiceImpl;
import hu.unideb.inf.rft.neuban.service.UserService;
import hu.unideb.inf.rft.service.dto.UserDto;

/**
 * 
 * @author Headswitcher
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity, UserDto, Long> implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(Class<UserDto> dtoType, Class<UserEntity> entityType, ModelMapper modelMapper,
			UserRepository userRepository) {
		super(dtoType, entityType, modelMapper, userRepository);
		this.userRepository = userRepository;
	}

	@Override
	public UserDto getByUserName(String userName) throws Exception {
		Assert.notNull(userName);
		return modelMapper.map(userRepository.findUserByUserName(userName), UserDto.class);
	}
}
