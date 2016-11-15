package hu.unideb.inf.rft.neuban.service.impl;

import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import hu.unideb.inf.rft.neuban.persistence.repositories.UserRepository;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final long ADMIN_ID = 1L;
    private static final String ADMIN_USER_NAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String USER_NAME_NON_EXISTENT = "non-existent username";
    private static final String PASSWORD_HASH = "$GDSJDT6464EGDFG5353";

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserServiceImpl userService;

    final UserDto userDto = UserDto.builder()
            .id(ADMIN_ID)
            .userName(ADMIN_USER_NAME)
            .password(ADMIN_PASSWORD)
            .role(Role.ADMIN)
            .build();
    final UserEntity userEntity = UserEntity.builder().
            id(ADMIN_ID)
            .userName(ADMIN_USER_NAME)
            .password(ADMIN_PASSWORD)
            .role(Role.ADMIN)
            .build();

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
        given(this.userRepository.findByUserName(USER_NAME_NON_EXISTENT)).willReturn(null);

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
        given(this.userRepository.findByUserName(ADMIN_USER_NAME)).willReturn(userEntity);
        given(this.modelMapper.map(userEntity, UserDto.class)).willReturn(userDto);

        // When
        final UserDto actualUserDto = this.userService.getByUserName(ADMIN_USER_NAME).orElse(null);

        // Then
        assertThat(actualUserDto, notNullValue());
        assertThat(actualUserDto, equalTo(userDto));

        then(this.userRepository).should().findByUserName(ADMIN_USER_NAME);
        then(this.modelMapper).should().map(userEntity, UserDto.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateShouldThrowIllegalArgumentExceptionWhenParamUserDtoIsNull() {
        // Given

        // When
        this.userService.saveOrUpdate(null);

        // Then
    }

    @Test(expected = IllegalStateException.class)
    public void saveOrUpdateShouldThrowIllegalStateExceptionWhenParamUserDtoPasswordFieldExistentWithNullValue() {
        // Given
        final UserDto userDto = UserDto.builder().password(null).build();

        // When
        this.userService.saveOrUpdate(userDto);

        // Then
    }

    @Test
    public void saveOrUpdateShouldBeSuccessfulSaveWhenParamUserDtoIsValid() {
        // Given
        final ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);

        given(this.modelMapper.map(userDto, UserEntity.class)).willReturn(userEntity);
        given(this.bCryptPasswordEncoder.encode(ADMIN_PASSWORD)).willReturn(PASSWORD_HASH);
        given(this.userRepository.saveAndFlush(any(UserEntity.class))).willReturn(userEntity);

        // When
        final Long savedId = this.userService.saveOrUpdate(userDto);

        // Then
        verify(this.userRepository).saveAndFlush(captor.capture());

        assertThat(savedId, notNullValue());
        assertThat(savedId, equalTo(ADMIN_ID));
        assertThat(userEntity.getPassword(), notNullValue());
        assertThat(userEntity.getPassword(), equalTo(PASSWORD_HASH));

        then(this.modelMapper).should().map(userDto, UserEntity.class);
        then(this.bCryptPasswordEncoder).should().encode(ADMIN_PASSWORD);
        then(this.userRepository).should().saveAndFlush(userEntity);
        verifyNoMoreInteractions(this.modelMapper, this.bCryptPasswordEncoder, this.userRepository);
    }
}