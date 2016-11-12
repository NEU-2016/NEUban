package hu.unideb.inf.rft.neuban.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;

import java.util.Optional;

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
	public Optional<UserDto> getByUserName(String userName) {
		Assert.notNull(userName);
		UserEntity userEntity = this.userRepository.findByUserName(userName).orElse(null);
		if (userEntity != null) {
			return Optional.of(this.modelMapper.map(userEntity, UserDto.class));
		}
		return Optional.empty();
	}
}