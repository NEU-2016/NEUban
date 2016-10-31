package hu.unideb.inf.rft.neuban.service.validator;

import hu.unideb.inf.rft.neuban.service.UserService;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.exceptions.UserNotFoundException;
import org.junit.Before;
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

    private UserDto adminUserDto;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserValidator userValidator;

    @Before
    public void setUp() {
        adminUserDto = UserDto.builder()
                .userName(ADMIN_USER_NAME)
                .password(ADMIN_PASSWORD)
                .build();
    }

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
        given(this.userService.getByUserName(ADMIN_USER_NAME)).willReturn(adminUserDto);

        // When
        final boolean result = this.userValidator.isValidLogin(ADMIN_USER_NAME, WRONG_PASSWORD);

        // Then
        assertThat(result, is(false));

        then(this.userService).should().getByUserName(ADMIN_USER_NAME);
    }

    @Test
    public void isValidLoginShouldReturnTrueWhenParamPasswordAndUserDtoPasswordAreTheSame() throws UserNotFoundException {
        // Given
        given(this.userService.getByUserName(ADMIN_USER_NAME)).willReturn(adminUserDto);

        // When
        final boolean result = this.userValidator.isValidLogin(ADMIN_USER_NAME, ADMIN_PASSWORD);

        // Then
        assertThat(result, is(true));

        then(this.userService).should().getByUserName(ADMIN_USER_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkUsernameExistsShouldThrowIllegalArgumentExceptionWhenUsernameIsNull() {
        // Given

        // When
        this.userValidator.checkUsernameExists(null);

        // Then
    }

    @Test
    public void checkUsernameExistsShouldReturnFalseWhenParamUsernameDoesNotExist() {
        // Given
        given(this.userService.getByUserName(ADMIN_USER_NAME)).willReturn(null);

        // When
        final boolean result = this.userValidator.checkUsernameExists(ADMIN_USER_NAME);

        // Then
        assertThat(result, is(false));

        then(this.userService).should().getByUserName(ADMIN_USER_NAME);
    }

    @Test
    public void checkUsernameExistsShouldReturnTrueWhenParamUsernameDoesExist() {
        // Given
        given(this.userService.getByUserName(ADMIN_USER_NAME)).willReturn(adminUserDto);

        // When
        final boolean result = this.userValidator.checkUsernameExists(ADMIN_USER_NAME);

        // Then
        assertThat(result, is(true));

        then(this.userService).should().getByUserName(ADMIN_USER_NAME);
    }
}