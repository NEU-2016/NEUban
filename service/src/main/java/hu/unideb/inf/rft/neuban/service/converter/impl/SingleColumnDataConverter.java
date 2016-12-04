package hu.unideb.inf.rft.neuban.service.converter.impl;


import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;
import hu.unideb.inf.rft.neuban.service.converter.DataListConverter;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SingleColumnDataConverter implements SingleDataConverter<ColumnEntity, ColumnDto> {

    @Autowired
    @Qualifier("cardDataListConverter")
    private DataListConverter<CardEntity, CardDto> cardDataListConverter;

    @Override
    public Optional<ColumnEntity> convertToSource(final ColumnDto columnDto) {
        if (columnDto == null) {
            return Optional.empty();
        }
        return Optional.of(ColumnEntity.builder()
                .id(columnDto.getId())
                .title(columnDto.getTitle())
                .cards(this.cardDataListConverter.convertToSources(columnDto.getCards()))
                .build()
        );
    }

    @Override
    public Optional<ColumnDto> convertToTarget(final ColumnEntity columnEntity) {
        if (columnEntity == null) {
            return Optional.empty();
        }
        return Optional.of(ColumnDto.builder()
                .id(columnEntity.getId())
                .title(columnEntity.getTitle())
                .cards(this.cardDataListConverter.convertToTargets(columnEntity.getCards()))
                .build()
        );
    }
}
