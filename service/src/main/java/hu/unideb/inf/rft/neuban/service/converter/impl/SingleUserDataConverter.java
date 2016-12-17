package hu.unideb.inf.rft.neuban.service.converter.impl;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.service.converter.DataListConverter;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SingleUserDataConverter implements SingleDataConverter<UserEntity, UserDto> {

    @Autowired
    @Qualifier("boardDataListConverter")
    private DataListConverter<BoardEntity, BoardDto> boardDataListConverter;

    @Autowired
    @Qualifier("cardDataListConverter")
    private DataListConverter<CardEntity, CardDto> cardDataListConverter;

    @Override
    public Optional<UserEntity> convertToSource(final UserDto userDto) {
        if (userDto == null) {
            return Optional.empty();
        }
        return Optional.of(UserEntity.builder()
                .id(userDto.getId())
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .boards(this.boardDataListConverter.convertToSources(userDto.getBoards()))
                .cards(this.cardDataListConverter.convertToSources(userDto.getCards()))
                .build()
        );
    }

    @Override
    public Optional<UserDto> convertToTarget(final UserEntity userEntity) {
        if (userEntity == null) {
            return Optional.empty();
        }
        return Optional.of(UserDto.builder()
                .id(userEntity.getId())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .boards(this.boardDataListConverter.convertToTargets(userEntity.getBoards()))
                .cards(this.cardDataListConverter.convertToTargets(userEntity.getCards()))
                .build()
        );
    }
}
