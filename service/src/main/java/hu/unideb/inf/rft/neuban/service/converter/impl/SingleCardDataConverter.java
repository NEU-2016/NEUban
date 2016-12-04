package hu.unideb.inf.rft.neuban.service.converter.impl;


import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SingleCardDataConverter implements SingleDataConverter<CardEntity, CardDto> {

    @Override
    public Optional<CardEntity> convertToSource(final CardDto cardDto) {
        if (cardDto == null) {
            return Optional.empty();
        }
        return Optional.of(CardEntity.builder()
                .id(cardDto.getId())
                .title(cardDto.getTitle())
                .description(cardDto.getDescription())
                .build()
        );
    }

    @Override
    public Optional<CardDto> convertToTarget(final CardEntity cardEntity) {
        if (cardEntity == null) {
            return Optional.empty();
        }
        return Optional.of(CardDto.builder()
                .id(cardEntity.getId())
                .title(cardEntity.getTitle())
                .description(cardEntity.getDescription())
                .build()
        );
    }
}
