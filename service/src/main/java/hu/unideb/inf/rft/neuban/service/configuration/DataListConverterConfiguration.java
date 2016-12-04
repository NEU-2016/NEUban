package hu.unideb.inf.rft.neuban.service.configuration;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.converter.impl.GenericDataListConverter;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataConverterBeanNameProvider.*;

@Configuration
public class DataListConverterConfiguration {

    @Autowired
    private WebApplicationContext context;

    @Bean
    public GenericDataListConverter<UserEntity, UserDto> userDataListConverter() {
        return new GenericDataListConverter(UserEntity.class, UserDto.class,
                this.context.getBean(SINGLE_USER_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public GenericDataListConverter<BoardEntity, BoardDto> boardDataListConverter() {
        return new GenericDataListConverter(BoardEntity.class, BoardDto.class,
                this.context.getBean(SINGLE_BOARD_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public GenericDataListConverter<ColumnEntity, ColumnDto> columnDataListConverter() {
        return new GenericDataListConverter(ColumnEntity.class, ColumnDto.class,
                this.context.getBean(SINGLE_COLUMN_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public GenericDataListConverter<CardEntity, CardDto> cardDataListConverter() {
        return new GenericDataListConverter(CardEntity.class, CardDto.class,
                this.context.getBean(SINGLE_CARD_DATA_CONVERTER, SingleDataConverter.class));
    }
}
