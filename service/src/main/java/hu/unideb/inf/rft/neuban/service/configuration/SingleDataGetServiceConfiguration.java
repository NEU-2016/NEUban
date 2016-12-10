package hu.unideb.inf.rft.neuban.service.configuration;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.CommentEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.domain.CommentDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.impl.shared.SingleDataGetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.context.WebApplicationContext;

import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataConverterBeanNameProvider.*;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.JPARepositoryBeanNameProvider.*;

@Configuration
public class SingleDataGetServiceConfiguration {

    @Autowired
    private WebApplicationContext context;

    @Bean
    public SingleDataGetServiceImpl<UserEntity, UserDto, Long> singleUserDataGetService() {
        return new SingleDataGetServiceImpl<>(this.context.getBean(USER_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_USER_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public SingleDataGetServiceImpl<BoardEntity, BoardDto, Long> singleBoardDataGetService() {
        return new SingleDataGetServiceImpl<>(this.context.getBean(BOARD_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_BOARD_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public SingleDataGetServiceImpl<ColumnEntity, ColumnDto, Long> singleColumnDataGetService() {
        return new SingleDataGetServiceImpl<>(this.context.getBean(COLUMN_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_COLUMN_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public SingleDataGetServiceImpl<CardEntity, CardDto, Long> singleCardDataGetService() {
        return new SingleDataGetServiceImpl<>(this.context.getBean(CARD_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_CARD_DATA_CONVERTER, SingleDataConverter.class));
    }
    
    @Bean
    public SingleDataGetServiceImpl<CommentEntity, CommentDto, Long> singleCommentDataGetService() {
        return new SingleDataGetServiceImpl<>(this.context.getBean(COMMENT_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_COMMENT_DATA_CONVERTER, SingleDataConverter.class));
    }
}
