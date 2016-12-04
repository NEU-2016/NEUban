package hu.unideb.inf.rft.neuban.service.converter.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import hu.unideb.inf.rft.neuban.service.converter.DataListConverter;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.CardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SingleUserDataConverterTest {

    private static final long USER_ID = 1L;
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";

    private static final long FIRST_ID = 1L;
    private static final long SECOND_ID = 2L;
    private static final long THIRD_ID = 3L;
    private static final String BOARD_TITLE = "Board title";
    private static final String CARD_TITLE = "Card title";
    private static final String CARD_DESCRIPTION = "Card description";


    private final List<BoardEntity> boardEntityList = Lists.newArrayList(
            BoardEntity.builder().id(FIRST_ID).title(BOARD_TITLE).build(),
            BoardEntity.builder().id(SECOND_ID).title(BOARD_TITLE).build(),
            BoardEntity.builder().id(THIRD_ID).title(BOARD_TITLE).build()
    );

    private final List<CardEntity> cardEntityList = Lists.newArrayList(
            CardEntity.builder().id(FIRST_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build(),
            CardEntity.builder().id(SECOND_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build(),
            CardEntity.builder().id(THIRD_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build()
    );

    private final List<BoardDto> boardDtoList = Lists.newArrayList(
            BoardDto.builder().id(FIRST_ID).title(BOARD_TITLE).build(),
            BoardDto.builder().id(SECOND_ID).title(BOARD_TITLE).build(),
            BoardDto.builder().id(THIRD_ID).title(BOARD_TITLE).build()
    );

    private final List<CardDto> cardDtoList = Lists.newArrayList(
            CardDto.builder().id(FIRST_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build(),
            CardDto.builder().id(SECOND_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build(),
            CardDto.builder().id(THIRD_ID).title(CARD_TITLE).description(CARD_DESCRIPTION).build()
    );

    private final UserEntity userEntity = UserEntity.builder()
            .id(USER_ID)
            .userName(USERNAME)
            .password(PASSWORD)
            .boards(boardEntityList)
            .cards(cardEntityList)
            .role(Role.USER)
            .build();

    private final UserDto userDto = UserDto.builder()
            .id(USER_ID)
            .userName(USERNAME)
            .password(PASSWORD)
            .boards(boardDtoList)
            .cards(cardDtoList)
            .role(Role.USER)
            .build();

    @InjectMocks
    private SingleUserDataConverter singleUserDataConverter;

    @Mock
    private DataListConverter<BoardEntity, BoardDto> boardDataListConverter;

    @Mock
    private DataListConverter<CardEntity, CardDto> cardDataListConverter;

    @Test
    public void convertToSourceShouldReturnEmptyOptionalWhenParameterUserDtoIsNull() {
        // Given

        // When
        final Optional<UserEntity> result = this.singleUserDataConverter.convertToSource(null);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        verifyZeroInteractions(this.boardDataListConverter, this.cardDataListConverter);
    }

    @Test
    public void convertToSourceShouldReturnOptionalWithValueWhenParameterUserDtoIsNonNull() {
        // Given
        given(this.boardDataListConverter.convertToSources(boardDtoList)).willReturn(boardEntityList);
        given(this.cardDataListConverter.convertToSources(cardDtoList)).willReturn(cardEntityList);

        // When
        final Optional<UserEntity> result = this.singleUserDataConverter.convertToSource(userDto);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(userEntity));

        then(this.boardDataListConverter).should().convertToSources(boardDtoList);
        then(this.cardDataListConverter).should().convertToSources(cardDtoList);
        verifyNoMoreInteractions(this.boardDataListConverter, this.cardDataListConverter);
    }

    @Test
    public void convertToTargetShouldReturnEmptyOptionalWhenParameterUserEntityIsNull() {
        // Given

        // When
        final Optional<UserDto> result = this.singleUserDataConverter.convertToTarget(null);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        verifyZeroInteractions(this.boardDataListConverter, this.cardDataListConverter);
    }

    @Test
    public void convertToTargetShouldReturnOptionalWithValueWhenParameterUserEntityIsNonNull() {
        // Given
        given(this.boardDataListConverter.convertToTargets(boardEntityList)).willReturn(boardDtoList);
        given(this.cardDataListConverter.convertToTargets(cardEntityList)).willReturn(cardDtoList);

        // When
        final Optional<UserDto> result = this.singleUserDataConverter.convertToTarget(userEntity);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(userDto));

        then(this.boardDataListConverter).should().convertToTargets(boardEntityList);
        then(this.cardDataListConverter).should().convertToTargets(cardEntityList);
        verifyNoMoreInteractions(this.boardDataListConverter, this.cardDataListConverter);
    }
}
