package hu.unideb.inf.rft.neuban.service.impl.shared;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SingleDataUpdateServiceImplTest {

    private static final long ID = 1L;
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";

    private SingleDataUpdateServiceImpl<UserEntity, UserDto, Long, UserNotFoundException> singleDataUpdateService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private SingleDataConverter<UserEntity, UserDto> singleDataConverter;

    @Before
    public void setUp() {
        this.singleDataUpdateService = new SingleDataUpdateServiceImpl(
                UserNotFoundException.class, this.userRepository, this.singleDataConverter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowIllegalArgumentExceptionWhenParameterDtoIsNull() throws DataNotFoundException {
        // Given

        // When
        this.singleDataUpdateService.update(null);

        // Then
    }

    @Test(expected = DataNotFoundException.class)
    public void updateShouldThrowDataNotFoundExceptionWhenParameterDtoIdIsNull() throws DataNotFoundException {
        // Given

        // When
        this.singleDataUpdateService.update(UserDto.builder().id(null).build());

        // Then
    }

    @Test
    public void updateShouldBeSuccessfulUpdatingWhenParameterDtoDoesExist() throws DataNotFoundException {
        // Given
        final UserDto userDto = UserDto.builder()
                .id(ID)
                .userName(USERNAME)
                .password(PASSWORD)
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .id(ID)
                .userName(USERNAME)
                .password(PASSWORD)
                .build();

        given(this.singleDataConverter.convertToSource(userDto)).willReturn(Optional.of(userEntity));

        // When
        this.singleDataUpdateService.update(userDto);

        // Then
        then(this.singleDataConverter).should().convertToSource(userDto);
        then(this.userRepository).should().saveAndFlush(userEntity);
        verifyNoMoreInteractions(this.singleDataConverter, this.userRepository);
    }
}