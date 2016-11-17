package hu.unideb.inf.rft.neuban.web.validators;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Optional;

import static hu.unideb.inf.rft.neuban.web.validators.UserValidator.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

    private static final String USERNAME = "admin";

    @InjectMocks
    private UserValidator userValidator;

    @Mock
    private UserService userService;
    @Mock
    private Errors errors;

    private final UserDto userDto = UserDto.builder()
            .userName(USERNAME)
            .build();

    @Test(expected = IllegalArgumentException.class)
    public void validateShouldThrowIllegalArgumentExceptionWhenParamTargetIsNull() {
        // Given

        // When
        userValidator.validate(null, errors);

        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateShouldThrowIllegalArgumentExceptionWhenParamErrorsIsNull() {
        // Given

        // When
        userValidator.validate(userDto, null);

        // Then
    }

    @Test
    public void validateShouldNotRejectValueIntoErrorsWhenUsernameDoesNotExist() {
        // Given
        given(this.userService.getByUserName(USERNAME)).willReturn(Optional.empty());

        // When
        this.userValidator.validate(userDto, errors);

        // Then
        then(this.userService).should().getByUserName(USERNAME);
        verifyNoMoreInteractions(this.userService);
        verifyZeroInteractions(errors);
    }

    @Test
    public void validateShouldRejectValueIntoErrorsWhenUsernameAlreadyExists() {
        // Given
        final FieldError fieldError = mock(FieldError.class);

        given(this.userService.getByUserName(USERNAME)).willReturn(Optional.of(userDto));
        given(errors.getErrorCount()).willReturn(1);
        given(errors.getFieldError(FIELD_NAME_USERNAME)).willReturn(fieldError);
        given(fieldError.getField()).willReturn(FIELD_NAME_USERNAME);
        given(fieldError.getCode()).willReturn(UNIQUE_USERNAME_CONSTRAINT);
        given(fieldError.getDefaultMessage()).willReturn(USERNAME_ALREADY_EXISTS_ERROR_MESSAGE_KEY);

        doNothing().when(errors).rejectValue(FIELD_NAME_USERNAME, UNIQUE_USERNAME_CONSTRAINT, USERNAME_ALREADY_EXISTS_ERROR_MESSAGE_KEY);

        // When
        this.userValidator.validate(userDto, errors);

        // Then
        assertThat(errors, notNullValue());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError(FIELD_NAME_USERNAME), notNullValue());
        assertThat(errors.getFieldError(FIELD_NAME_USERNAME).getField(), equalTo(FIELD_NAME_USERNAME));
        assertThat(errors.getFieldError(FIELD_NAME_USERNAME).getCode(), equalTo(UNIQUE_USERNAME_CONSTRAINT));
        assertThat(errors.getFieldError(FIELD_NAME_USERNAME).getDefaultMessage(), equalTo(USERNAME_ALREADY_EXISTS_ERROR_MESSAGE_KEY));

        then(this.userService).should().getByUserName(USERNAME);
        then(errors).should().rejectValue(FIELD_NAME_USERNAME, UNIQUE_USERNAME_CONSTRAINT, USERNAME_ALREADY_EXISTS_ERROR_MESSAGE_KEY);
        verifyNoMoreInteractions(this.userService);
    }
}
