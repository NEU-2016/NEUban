package hu.unideb.inf.rft.neuban.service.impl;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.converter.DataListConverter;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.NullFieldValueException;
import hu.unideb.inf.rft.neuban.service.exceptions.UserAlreadyExistsOnCardException;
import hu.unideb.inf.rft.neuban.service.exceptions.UserNotFoundOnCardException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.UserNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.CardService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataGetServiceBeanNameProvider.SINGLE_USER_DATA_GET_SERVICE;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataUpdateServiceBeanNameProvider.SINGLE_USER_DATA_UPDATE_SERVICE;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CardService cardService;

    @Autowired
    @Qualifier(SINGLE_USER_DATA_GET_SERVICE)
    private SingleDataGetService<UserDto, Long> singleUserDataGetService;

    @Autowired
    @Qualifier(SINGLE_USER_DATA_UPDATE_SERVICE)
    private SingleDataUpdateService<UserDto> singleUserDataUpdateService;

    @Autowired
    private DataListConverter<UserEntity, UserDto> userDataListConverter;

    @Transactional(readOnly = true)
    @Override
    public Optional<UserDto> get(final Long userId) {
        return this.singleUserDataGetService.get(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllByBoardId(final Long boardId) {
        Assert.notNull(boardId);

        return this.userDataListConverter.convertToTargets(this.userRepository.findAllByBoardId(boardId));
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
    public void create(final UserDto userDto) {
        Assert.notNull(userDto);

        final String password = Optional.ofNullable(userDto.getPassword()).orElseThrow(NullFieldValueException::new);
        final UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(password));

        this.userRepository.saveAndFlush(userEntity);
    }

    @Transactional
    @Override
    public void addUserToCard(final Long userId, final Long cardId) throws DataNotFoundException, UserAlreadyExistsOnCardException {
        final UserDto userDto = this.get(userId).orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        final CardDto cardDto = this.cardService.get(cardId).orElseThrow(() -> new CardNotFoundException(String.valueOf(cardId)));

        if (this.userExistsOnCard(userDto, cardDto)) {
            throw new UserAlreadyExistsOnCardException(String.valueOf(userId), String.valueOf(cardId));
        }

        userDto.getCards().add(cardDto);
        this.update(userDto);
    }

    @Transactional
    @Override
    public void removeUserFromCard(final Long userId, final Long cardId) throws DataNotFoundException, UserNotFoundOnCardException {
        final UserDto userDto = this.get(userId).orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        final CardDto cardDto = this.cardService.get(cardId).orElseThrow(() -> new CardNotFoundException(String.valueOf(cardId)));

        if (!this.userExistsOnCard(userDto, cardDto)) {
            throw new UserNotFoundOnCardException(String.valueOf(userId), String.valueOf(cardId));
        }

        userDto.getCards().remove(cardDto);
        this.update(userDto);
    }

    private boolean userExistsOnCard(final UserDto userDto, final CardDto cardDto) {
        return userDto.getCards().stream().anyMatch(actualCard -> actualCard.getId().equals(cardDto.getId()));
    }

    @Transactional
    @Override
    public void update(final UserDto userDto) throws DataNotFoundException {
        this.singleUserDataUpdateService.update(userDto);
    }
}