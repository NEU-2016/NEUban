package hu.unideb.inf.rft.neuban.service.impl;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.NullFieldValueException;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataGetServiceBeanNameProvider.SINGLE_USER_DATA_GET_SERVICE;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Qualifier(SINGLE_USER_DATA_GET_SERVICE)
    private SingleDataGetService<UserDto, Long> singleUserDataGetService;

    @Transactional(readOnly = true)
    @Override
    public Optional<UserDto> get(final Long userId) {
        return this.singleUserDataGetService.get(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        // TODO After baseservice refactor this is not necessary
        final Type listType = new TypeToken<List<UserDto>>() {
        }.getType();
        return modelMapper.map(userRepository.findAll(), listType);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserDto> getByUserName(final String userName) {
        Assert.notNull(userName);
        Optional<UserEntity> userEntity = Optional.ofNullable(this.userRepository.findByUserName(userName));
        if (userEntity.isPresent()) {
            return Optional.of(this.modelMapper.map(userEntity.get(), UserDto.class));
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void saveOrUpdate(final UserDto userDto) {
        Assert.notNull(userDto);

        final String password = Optional.ofNullable(userDto.getPassword()).orElseThrow(NullFieldValueException::new);
        final UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(password));

        this.userRepository.saveAndFlush(userEntity);
    }
}