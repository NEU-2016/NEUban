package hu.unideb.inf.rft.neuban.service.impl;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final long ADMIN_ID = 1L;
    private static final String ADMIN_USER_NAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String USER_NAME_NON_EXISTENT = "non-existent username";

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserServiceImpl userService;

    @Before
    public void setUp() {
        this.userService = new UserServiceImpl(this.userRepository, this.modelMapper, this.bCryptPasswordEncoder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getByUserNameShouldThrowIllegalArgumentExceptionWhenParamUserNameIsNull() {
        // Given

        // When
        this.userService.getByUserName(null);

        // Then
    }

    @Test
    public void getByUserNameShouldReturnNullWhenUserWithTheParamUserNameDoesNotExist() {
        // Given
        given(this.userRepository.findByUserName(USER_NAME_NON_EXISTENT)).willReturn(Optional.empty());

        // When
        final UserDto actualUserDto = this.userService.getByUserName(USER_NAME_NON_EXISTENT).orElse(null);

        // Then
        assertThat(actualUserDto, nullValue());

        then(this.userRepository).should().findByUserName(USER_NAME_NON_EXISTENT);
        verifyZeroInteractions(this.modelMapper);
    }

    @Test
    public void getByUserNameShouldReturnAnExistingUserDtoWhenParamUserNameExists() {
        // Given
        final UserDto expectedUserDto = UserDto.builder().id(ADMIN_ID).userName(ADMIN_USER_NAME).password(ADMIN_PASSWORD).role(Role.ADMIN).build();
        final UserEntity userEntity = UserEntity.builder().id(ADMIN_ID).userName(ADMIN_USER_NAME).password(ADMIN_PASSWORD).role(Role.ADMIN).build();

        given(this.userRepository.findByUserName(ADMIN_USER_NAME)).willReturn(Optional.of(userEntity));
        given(this.modelMapper.map(userEntity, UserDto.class)).willReturn(expectedUserDto);

        // When
        final UserDto actualUserDto = this.userService.getByUserName(ADMIN_USER_NAME).orElse(null);

        // Then
        assertThat(actualUserDto, notNullValue());
        assertThat(actualUserDto, equalTo(expectedUserDto));

        then(this.userRepository).should().findByUserName(ADMIN_USER_NAME);
        then(this.modelMapper).should().map(userEntity, UserDto.class);
    }
}