package hu.unideb.inf.rft.neuban.web.validators;

import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    public static final String FIELD_NAME_USERNAME = "userName";
    public static final String USERNAME_ALREADY_EXISTS_ERROR_MESSAGE_KEY = "username.already.exists";
    public static final String UNIQUE_USERNAME_CONSTRAINT = "Username";

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(final Class<?> aClass) {
        return UserDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        Assert.notNull(target);
        Assert.notNull(errors);

        final UserDto userDto = (UserDto) target;

        if (this.userService.getByUserName(userDto.getUserName()).isPresent()) {
            errors.rejectValue(FIELD_NAME_USERNAME, UNIQUE_USERNAME_CONSTRAINT, USERNAME_ALREADY_EXISTS_ERROR_MESSAGE_KEY);
        }
    }
}
