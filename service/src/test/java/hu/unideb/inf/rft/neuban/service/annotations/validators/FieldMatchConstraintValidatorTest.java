package hu.unideb.inf.rft.neuban.service.annotations.validators;

import hu.unideb.inf.rft.neuban.service.annotations.FieldMatch;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class FieldMatchConstraintValidatorTest {

    private static final String PASSWORD = "password";
    private static final String PASSWORD_CONFIRMATION = "password confirmation";

    @InjectMocks
    private FieldMatchConstraintValidator fieldMatchConstraintValidator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private FieldMatch fieldMatch;


    @Before
    public void setUp() {
        given(fieldMatch.firstFieldName()).willReturn("password");
        given(fieldMatch.secondFieldName()).willReturn("passwordConfirmation");

        fieldMatchConstraintValidator.initialize(fieldMatch);
    }

    @Test
    public void isValidShouldReturnTrueWhenPasswordAndPasswordConfirmationFieldsAreNull() throws InvocationTargetException, IllegalAccessException {
        // Given
        final UserDto userDto = UserDto.builder()
                .password(null)
                .passwordConfirmation(null)
                .build();

        // When
        final boolean result = this.fieldMatchConstraintValidator.isValid(userDto, this.constraintValidatorContext);

        // Then
        assertThat(result, is(true));
    }

    @Test
    public void isValidShouldReturnFalseWhenPasswordFieldIsNullAndPasswordConfirmationFieldIsNotNull() {
        // Given
        final UserDto userDto = UserDto.builder()
                .password(null)
                .passwordConfirmation(PASSWORD_CONFIRMATION)
                .build();

        // When
        final boolean result = this.fieldMatchConstraintValidator.isValid(userDto, this.constraintValidatorContext);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void isValidShouldReturnFalseWhenPasswordFieldIsNotNullAndPasswordConfirmationFieldIsNull() {
        // Given
        final UserDto userDto = UserDto.builder()
                .password(PASSWORD)
                .passwordConfirmation(null)
                .build();

        // When
        final boolean result = this.fieldMatchConstraintValidator.isValid(userDto, this.constraintValidatorContext);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void isValidShouldReturnFalseWhenPasswordFieldAndPasswordConfirmationFieldContainsDifferentValues() {
        // Given
        final UserDto userDto = UserDto.builder()
                .password(PASSWORD)
                .passwordConfirmation(PASSWORD_CONFIRMATION)
                .build();

        // When
        final boolean result = this.fieldMatchConstraintValidator.isValid(userDto, this.constraintValidatorContext);

        // Then
        assertThat(result, is(false));
    }

    @Test
    public void isValidShouldReturnTrueWhenPasswordFieldAndPasswordConfirmationFieldContainsTheSameValues() {
        // Given
        final UserDto userDto = UserDto.builder()
                .password(PASSWORD)
                .passwordConfirmation(PASSWORD)
                .build();

        // When
        final boolean result = this.fieldMatchConstraintValidator.isValid(userDto, this.constraintValidatorContext);

        // Then
        assertThat(result, is(true));
    }
}
