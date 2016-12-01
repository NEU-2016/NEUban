package hu.unideb.inf.rft.neuban.service.converter.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SingleDataConverterServiceImplTest {

    private static final long USER_ID = 1L;
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";

    private static final long FIRST_BOARD_ID = 1L;
    private static final long SECOND_BOARD_ID = 2L;

    private static final String FIRST_BOARD_TITLE = "First board title";
    private static final String SECOND_BOARD_TITLE = "Second board title";

    private final List<BoardEntity> boardEntities = Lists.newArrayList(
            BoardEntity.builder().id(FIRST_BOARD_ID).title(FIRST_BOARD_TITLE).build(),
            BoardEntity.builder().id(SECOND_BOARD_ID).title(SECOND_BOARD_TITLE).build()
    );

    private final List<BoardDto> boardDtos = Lists.newArrayList(
            BoardDto.builder().id(FIRST_BOARD_ID).title(FIRST_BOARD_TITLE).build(),
            BoardDto.builder().id(SECOND_BOARD_ID).title(SECOND_BOARD_TITLE).build()
    );

    private final UserEntity userEntity = UserEntity.builder()
            .id(USER_ID)
            .userName(USERNAME)
            .password(PASSWORD)
            .role(Role.USER)
            .boards(boardEntities)
            .build();

    private final UserDto userDto = UserDto.builder()
            .id(USER_ID)
            .userName(USERNAME)
            .password(PASSWORD)
            .role(Role.USER)
            .boards(boardDtos)
            .build();

    @Mock
    private ModelMapper modelMapper;

    private SingleDataConverterServiceImpl<UserEntity, UserDto> singleDataConverterService;

    @Before
    public void setUp() {
        this.singleDataConverterService = new SingleDataConverterServiceImpl<>(UserEntity.class, UserDto.class, this.modelMapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertToSourceShouldThrowIllegalArgumentExceptionWhenParamTargetObjectIsNull() {
        // Given

        // When
        this.singleDataConverterService.convertToSource(null);

        // Then
    }

    @Test
    public void convertToSourceShouldReturnSourceObjectAsOptionalWhenParamTargetObjectIsValid() {
        // Given
        given(this.modelMapper.map(userDto, UserEntity.class)).willReturn(userEntity);

        // When
        final Optional<UserEntity> result = this.singleDataConverterService.convertToSource(userDto);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(userEntity));

        then(this.modelMapper).should().map(userDto, UserEntity.class);
        verifyNoMoreInteractions(this.modelMapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertToTargetShouldThrowIllegalArgumentExceptionWhenParamSourceObjectIsNull() {
        // Given

        // When
        this.singleDataConverterService.convertToTarget(null);

        // Then
    }

    @Test
    public void convertToTargetShouldReturnSourceObjectAsOptionalWhenParamSourceObjectIsValid() {
        // Given
        given(this.modelMapper.map(userEntity, UserDto.class)).willReturn(userDto);

        // When
        final Optional<UserDto> result = this.singleDataConverterService.convertToTarget(userEntity);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(userDto));

        then(this.modelMapper).should().map(userEntity, UserDto.class);
        verifyNoMoreInteractions(this.modelMapper);
    }
}
