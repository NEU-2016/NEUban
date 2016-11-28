package hu.unideb.inf.rft.neuban.service.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.CardRepository;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.exceptions.CardAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.CardService;
import hu.unideb.inf.rft.neuban.service.interfaces.ColumnService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private ColumnService columnService;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ModelMapper modelMapper;

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
}
