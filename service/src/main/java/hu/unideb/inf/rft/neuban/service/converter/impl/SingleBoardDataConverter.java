package hu.unideb.inf.rft.neuban.service.converter.impl;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;
import hu.unideb.inf.rft.neuban.service.converter.DataListConverter;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SingleBoardDataConverter implements SingleDataConverter<BoardEntity, BoardDto> {

    @Autowired
    @Qualifier("columnDataListConverter")
    private DataListConverter<ColumnEntity, ColumnDto> columnDataListConverter;

    @Override
    public Optional<BoardEntity> convertToSource(final BoardDto boardDto) {
        if (boardDto == null) {
            return Optional.empty();
        }
        return Optional.of(BoardEntity.builder()
                .id(boardDto.getId())
                .title(boardDto.getTitle())
                .columns(this.columnDataListConverter.convertToSources(boardDto.getColumns()))
                .build()
        );
    }

    @Override
    public Optional<BoardDto> convertToTarget(final BoardEntity boardEntity) {
        if (boardEntity == null) {
            return Optional.empty();
        }
        return Optional.of(BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .columns(this.columnDataListConverter.convertToTargets(boardEntity.getColumns()))
                .build()
        );
    }
}
