package hu.unideb.inf.rft.neuban.service.configuration;

import hu.unideb.inf.rft.neuban.persistence.configuration.PersistenceConfiguration;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

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
    public GenericDataListConverter<UserEntity, UserDto> userDataListConverter() {
        return new GenericDataListConverter(UserEntity.class, UserDto.class,
                this.context.getBean("singleUserDataConverter", SingleDataConverter.class));
    }

    @Bean
    public GenericDataListConverter<BoardEntity, BoardDto> boardDataListConverter() {
        return new GenericDataListConverter(BoardEntity.class, BoardDto.class,
                this.context.getBean("singleBoardDataConverter", SingleDataConverter.class));
    }

    @Bean
    public GenericDataListConverter<ColumnEntity, ColumnDto> columnDataListConverter() {
        return new GenericDataListConverter(ColumnEntity.class, ColumnDto.class,
                this.context.getBean("singleColumnDataConverter", SingleDataConverter.class));
    }

    @Bean
    public GenericDataListConverter<CardEntity, CardDto> cardDataListConverter() {
        return new GenericDataListConverter(CardEntity.class, CardDto.class,
                this.context.getBean("singleCardDataConverter", SingleDataConverter.class));
    }
}
