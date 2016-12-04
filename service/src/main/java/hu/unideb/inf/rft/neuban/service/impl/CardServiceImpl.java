package hu.unideb.inf.rft.neuban.service.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.CardRepository;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.exceptions.*;
import hu.unideb.inf.rft.neuban.service.interfaces.CardService;
import hu.unideb.inf.rft.neuban.service.interfaces.ColumnService;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static hu.unideb.inf.rft.neuban.service.configuration.CrudServiceBeanNameProvider.SINGLE_CARD_DATA_GET_SERVICE;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private UserService userService;
    @Autowired
    private ColumnService columnService;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @Qualifier(SINGLE_CARD_DATA_GET_SERVICE)
    private SingleDataGetService<CardDto, Long> singleCardDataGetService;

    @Transactional(readOnly = true)
    @Override
    public Optional<CardDto> get(final Long cardId) {
        return this.singleCardDataGetService.get(cardId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CardDto> getAllByColumnId(final Long columnId) {
        final Optional<ColumnDto> columnDtoOptional = this.columnService.get(columnId);

        if (columnDtoOptional.isPresent()) {
            return columnDtoOptional.get().getCards();
        }
        return Lists.newArrayList();
    }

    @Transactional
    @Override
    public void save(final Long columnId, final CardDto cardDto) throws ColumnNotFoundException, CardAlreadyExistsException {
        Assert.notNull(cardDto);
        final ColumnDto columnDto = this.columnService.get(columnId).orElseThrow(() -> new ColumnNotFoundException(String.valueOf(columnId)));

        if (cardDto.getId() == null || columnDto.getCards().stream().noneMatch(c -> c.getId().equals(cardDto.getId()))) {
            columnDto.getCards().add(cardDto);
        } else {
            throw new CardAlreadyExistsException(String.valueOf(cardDto.getId()));
        }
        this.columnService.update(columnDto);
    }

    @Transactional
    @Override
    public void update(final CardDto cardDto) throws CardNotFoundException {
        Assert.notNull(cardDto);

        if (cardDto.getId() == null) {
            throw new CardNotFoundException(String.valueOf(cardDto.getId()));
        }
        this.cardRepository.saveAndFlush(this.modelMapper.map(cardDto, CardEntity.class));
    }

    @Transactional
    @Override
    public void remove(final Long cardId) throws CardNotFoundException {
        Assert.notNull(cardId);

        Optional.ofNullable(this.cardRepository.findOne(cardId))
                .orElseThrow(() -> new CardNotFoundException(String.valueOf(cardId)));

        this.cardRepository.delete(cardId);
    }

    @Override
    public void addUserToCard(Long userId, Long cardId) throws UserNotFoundException, CardNotFoundException, UserAlreadyExistsOnCardException {

    }

    @Override
    public void removeUserFromCard(Long userId, Long cardId) throws UserNotFoundException, CardNotFoundException, UserNotFoundOnCardException {

    }

    /*@Transactional
    @Override
    public void addUserToCard(final Long userId, final Long cardId) throws UserNotFoundException, CardNotFoundException, UserAlreadyExistsOnCardException {
        final UserDto userDto = this.userService.get(userId).orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        final CardDto cardDto = this.get(cardId).orElseThrow(() -> new CardNotFoundException(String.valueOf(cardId)));

        if (cardDto.getUsers().stream().anyMatch(actualUser -> actualUser.getId().equals(userDto.getId()))) {
            throw new UserAlreadyExistsOnCardException(String.valueOf(userId), String.valueOf(cardId));
        }
        cardDto.getUsers().add(userDto);
        this.update(cardDto);
    }

    @Transactional
    @Override
    public void removeUserFromCard(final Long userId, final Long cardId) throws UserNotFoundException, CardNotFoundException, UserNotFoundOnCardException {
        final UserDto userDto = this.userService.get(userId).orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        final CardDto cardDto = this.get(cardId).orElseThrow(() -> new CardNotFoundException(String.valueOf(cardId)));

        if (cardDto.getUsers().stream().noneMatch(actualUser -> actualUser.getId().equals(userDto.getId()))) {
            throw new UserNotFoundOnCardException(String.valueOf(userId), String.valueOf(cardId));
        }
        cardDto.getUsers().removeIf(actualUser -> actualUser.getId().equals(userDto.getId()));
        this.update(cardDto);
    }*/
}
