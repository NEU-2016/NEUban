package hu.unideb.inf.rft.neuban.service.impl.shared;

import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SingleDataGetServiceImplTest {

    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String BOARD_TITLE = "Board title";

    private SingleDataGetServiceImpl<UserEntity, UserDto, Long> singleDataGetService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private SingleDataConverter<UserEntity, UserDto> singleDataConverter;

    @Before
    public void setUp() {
        this.singleDataGetService = new SingleDataGetServiceImpl<>(this.userRepository, this.singleDataConverter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getShouldThrowIllegalArgumentExceptionWhenParameterIdIsNull() {
        // Given

        // When
        this.singleDataGetService.get(null);

        // Then
    }

    @Test
    public void getShouldReturnWithEmptyOptionalWhenUserDoesNotExist() {
        // Given
        final long id = -1L;

        given(this.userRepository.findOne(id)).willReturn(null);

        // When
        Optional<UserDto> result = this.singleDataGetService.get(id);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        then(this.userRepository).should().findOne(id);
        verifyNoMoreInteractions(this.userRepository);
        verifyZeroInteractions(this.singleDataConverter);
    }

    @Test
    public void getShouldReturnWithNonEmptyOptionalWhenUserDoesExist() {
        // Given
        final long id = 1L;
        final UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .userName(USERNAME)
                .password(PASSWORD)
                .role(Role.USER)
                .boards(Collections.singletonList(BoardEntity.builder().id(id).title(BOARD_TITLE).build()))
                .build();

        final UserDto expectedUserDto = UserDto.builder()
                .id(id)
                .userName(USERNAME)
                .password(PASSWORD)
                .role(Role.USER)
                .boards(Collections.singletonList(BoardDto.builder().id(id).title(BOARD_TITLE).build()))
                .build();

        given(this.userRepository.findOne(id)).willReturn(userEntity);
        given(this.singleDataConverter.convertToTarget(userEntity)).willReturn(Optional.of(expectedUserDto));

        // When
        Optional<UserDto> result = this.singleDataGetService.get(id);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(expectedUserDto));

        then(this.userRepository).should().findOne(id);
        then(this.singleDataConverter).should().convertToTarget(userEntity);
        verifyNoMoreInteractions(this.userRepository, this.singleDataConverter);
    }
}
