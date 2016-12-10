package hu.unideb.inf.rft.neuban.service.configuration;

import static hu.unideb.inf.rft.neuban.service.provider.beanname.JPARepositoryBeanNameProvider.BOARD_REPOSITORY;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.JPARepositoryBeanNameProvider.CARD_REPOSITORY;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.JPARepositoryBeanNameProvider.COLUMN_REPOSITORY;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.JPARepositoryBeanNameProvider.COMMENT_REPOSITORY;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.JPARepositoryBeanNameProvider.USER_REPOSITORY;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataConverterBeanNameProvider.SINGLE_BOARD_DATA_CONVERTER;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataConverterBeanNameProvider.SINGLE_CARD_DATA_CONVERTER;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataConverterBeanNameProvider.SINGLE_COLUMN_DATA_CONVERTER;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataConverterBeanNameProvider.SINGLE_COMMENT_DATA_CONVERTER;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataConverterBeanNameProvider.SINGLE_USER_DATA_CONVERTER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.context.WebApplicationContext;

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
import hu.unideb.inf.rft.neuban.service.exceptions.CommentNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.CardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.ColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.UserNotFoundException;
import hu.unideb.inf.rft.neuban.service.impl.shared.SingleDataUpdateServiceImpl;

@Configuration
public class SingleDataUpdateServiceConfiguration {

    @Autowired
    private WebApplicationContext context;

    @Bean
    public SingleDataUpdateServiceImpl<UserEntity, UserDto, Long, UserNotFoundException> singleUserDataUpdateService() {
        return new SingleDataUpdateServiceImpl<>(UserNotFoundException.class,
                this.context.getBean(USER_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_USER_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public SingleDataUpdateServiceImpl<BoardEntity, BoardDto, Long, BoardNotFoundException> singleBoardDataUpdateService() {
        return new SingleDataUpdateServiceImpl<>(BoardNotFoundException.class,
                this.context.getBean(BOARD_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_BOARD_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public SingleDataUpdateServiceImpl<ColumnEntity, ColumnDto, Long, ColumnNotFoundException> singleColumnDataUpdateService() {
        return new SingleDataUpdateServiceImpl<>(ColumnNotFoundException.class,
                this.context.getBean(COLUMN_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_COLUMN_DATA_CONVERTER, SingleDataConverter.class));
    }

    @Bean
    public SingleDataUpdateServiceImpl<CardEntity, CardDto, Long, CardNotFoundException> singleCardDataUpdateService() {
        return new SingleDataUpdateServiceImpl<>(CardNotFoundException.class,
                this.context.getBean(CARD_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_CARD_DATA_CONVERTER, SingleDataConverter.class));
    }
    
    @Bean
    public SingleDataUpdateServiceImpl<CommentEntity, CommentDto, Long, CommentNotFoundException> singleCommentDataUpdateService() {
        return new SingleDataUpdateServiceImpl<>(CommentNotFoundException.class,
                this.context.getBean(COMMENT_REPOSITORY, JpaRepository.class),
                this.context.getBean(SINGLE_COMMENT_DATA_CONVERTER, SingleDataConverter.class));
    }
}
