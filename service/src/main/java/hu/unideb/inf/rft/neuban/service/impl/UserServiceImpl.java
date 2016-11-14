package hu.unideb.inf.rft.neuban.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super(UserDto.class, UserEntity.class, modelMapper, userRepository);
		this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

	@Transactional
	@Override
	public Long saveOrUpdate(final UserDto userDto) {
		Assert.notNull(userDto);

        final String password = Optional.ofNullable(userDto.getPassword()).orElseThrow(IllegalStateException::new);
		final UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(password));

        return this.userRepository.saveAndFlush(userEntity).getId();
	}
}