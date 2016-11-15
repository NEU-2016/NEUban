package hu.unideb.inf.rft.neuban.service.annotations.validators;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class UniqueUsernameConstraintValidatorTest {

    private static final String USERNAME = "username";

    @InjectMocks
    private UniqueUsernameConstraintValidator uniqueUsernameConstraintValidator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        given(this.applicationContext.getBean(UserService.class)).willReturn(this.userService);

        this.uniqueUsernameConstraintValidator.setApplicationContext(this.applicationContext);
    }

    @Test
    public void isValidShouldReturnFalseWhenParamUsernameIsNull() {
        // Given

        // When
        final boolean result = this.uniqueUsernameConstraintValidator.isValid(null, this.constraintValidatorContext);

        // Then
        assertThat(result, is(false));
        verifyZeroInteractions(this.userService);
    }

    @Test
    public void isValidShouldReturnFalseWhenParamUsernameAlreadyExists() {
        // Given
        final UserDto userDto = UserDto.builder().userName(USERNAME).build();

        given(this.userService.getByUserName(USERNAME)).willReturn(Optional.of(userDto));

        // When
        final boolean result = this.uniqueUsernameConstraintValidator.isValid(USERNAME, this.constraintValidatorContext);

        // Then
        assertThat(result, is(false));

        then(this.userService).should().getByUserName(USERNAME);

        verifyNoMoreInteractions(this.userService);
    }

    @Test
    public void isValidShouldReturnTrueWhenParamUsernameNotExists() {
        // Given
        given(this.userService.getByUserName(USERNAME)).willReturn(Optional.empty());

        // When
        final boolean result = this.uniqueUsernameConstraintValidator.isValid(USERNAME, this.constraintValidatorContext);

        // Then
        assertThat(result, is(true));

        then(this.userService).should().getByUserName(USERNAME);

        verifyNoMoreInteractions(this.userService);
    }
}
