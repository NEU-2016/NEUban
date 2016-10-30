package hu.unideb.inf.rft.neuban.service.validator;

import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.UserNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

    private static final String ADMIN_USER_NAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String WRONG_USER_NAME = "wrong username";
    private static final String WRONG_PASSWORD = "wrong password";

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserValidator userValidator;

    @Test(expected = IllegalArgumentException.class)
    public void isValidLoginShouldThrowIllegalArgumentExceptionWhenUserNameIsNull() throws UserNotFoundException {
        // Given

        // When
        this.userValidator.isValidLogin(null, ADMIN_PASSWORD);

        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidLoginShouldThrowIllegalArgumentExceptionWhenPasswordIsNull() throws UserNotFoundException {
        // Given

        // When
        this.userValidator.isValidLogin(ADMIN_USER_NAME, null);

        // Then
    }

    @Test
    public void isValidLoginShouldThrowUserNotFoundExceptionWhenUserNameDoesNotExist() throws UserNotFoundException {
        // Given
        given(this.userService.getByUserName(WRONG_USER_NAME)).willReturn(null);

        expectedException.expect(UserNotFoundException.class);

        // When
        this.userValidator.isValidLogin(WRONG_USER_NAME, ADMIN_PASSWORD);

        // Then
        then(this.userService).should().getByUserName(WRONG_USER_NAME);
    }

    @Test
    public void isValidLoginShouldReturnFalseWhenParamPasswordAndUserDtoPasswordAreDifferent() throws UserNotFoundException {
        // Given
        final UserDto expectedUserDto = UserDto.builder().userName(ADMIN_USER_NAME).password(ADMIN_PASSWORD).build();

        given(this.userService.getByUserName(ADMIN_USER_NAME)).willReturn(expectedUserDto);

        // When
        final boolean result = this.userValidator.isValidLogin(ADMIN_USER_NAME, WRONG_PASSWORD);

        // Then
        assertThat(result, is(false));

        then(this.userService).should().getByUserName(ADMIN_USER_NAME);
    }

    @Test
    public void isValidLoginShouldReturnTrueWhenParamPasswordAndUserDtoPasswordAreTheSame() throws UserNotFoundException {
        // Given
        final UserDto expectedUserDto = UserDto.builder().userName(ADMIN_USER_NAME).password(ADMIN_PASSWORD).build();

        given(this.userService.getByUserName(ADMIN_USER_NAME)).willReturn(expectedUserDto);

        // When
        final boolean result = this.userValidator.isValidLogin(ADMIN_USER_NAME, ADMIN_PASSWORD);

        // Then
        assertThat(result, is(true));

        then(this.userService).should().getByUserName(ADMIN_USER_NAME);
    }
}