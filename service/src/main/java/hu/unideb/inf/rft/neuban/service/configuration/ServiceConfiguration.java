package hu.unideb.inf.rft.neuban.service.configuration;

import hu.unideb.inf.rft.neuban.persistence.configuration.PersistenceConfiguration;
import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.converter.impl.GenericDataListConverter;
import hu.unideb.inf.rft.neuban.service.impl.shared.SingleDataGetServiceImpl;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

import static hu.unideb.inf.rft.neuban.service.configuration.ConverterBeanNameProvider.*;
import static hu.unideb.inf.rft.neuban.service.configuration.RepositoryBeanNameProvider.*;

@Configuration
@ComponentScan(basePackages = "hu.unideb.inf.rft.neuban.service")
@Import(PersistenceConfiguration.class)
public class ServiceConfiguration {

    @Autowired
    private WebApplicationContext context;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SingleDataGetServiceImpl<UserEntity, UserDto, Long> singleUserDataGetService() {
        return new SingleDataGetServiceImpl<>(UserEntity.class, UserDto.class,
                this.context.getBean(USER_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_USER_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public SingleDataGetServiceImpl<BoardEntity, BoardDto, Long> singleBoardDataGetService() {
        return new SingleDataGetServiceImpl<>(BoardEntity.class, BoardDto.class,
                this.context.getBean(BOARD_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_BOARD_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public SingleDataGetServiceImpl<ColumnEntity, ColumnDto, Long> singleColumnDataGetService() {
        return new SingleDataGetServiceImpl<>(ColumnEntity.class, ColumnDto.class,
                this.context.getBean(COLUMN_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_COLUMN_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public SingleDataGetServiceImpl<CardEntity, CardDto, Long> singleCardDataGetService() {
        return new SingleDataGetServiceImpl<>(CardEntity.class, CardDto.class,
                this.context.getBean(CARD_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_CARD_DATA_CONVERTER, SingleDataConverter.class));
    }

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
