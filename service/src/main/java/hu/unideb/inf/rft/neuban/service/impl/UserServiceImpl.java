package hu.unideb.inf.rft.neuban.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.UserService;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;

/**
 *
 * @author Headswitcher
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity, UserDto, Long> implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		super(UserDto.class, UserEntity.class, modelMapper, userRepository);
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public UserDto getByUserName(String userName) {
		Assert.notNull(userName);
		UserEntity userEntity = this.userRepository.findByUserName(userName);
		if (userEntity != null) {
			return this.modelMapper.map(userEntity, UserDto.class);
		}
		return null;
	}
}