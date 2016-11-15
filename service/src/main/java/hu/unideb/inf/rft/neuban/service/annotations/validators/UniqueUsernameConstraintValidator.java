package hu.unideb.inf.rft.neuban.service.annotations.validators;

import hu.unideb.inf.rft.neuban.service.annotations.UniqueUsername;
import hu.unideb.inf.rft.neuban.service.interfaces.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameConstraintValidator implements ConstraintValidator<UniqueUsername, String>, ApplicationContextAware {

    private UserService userService;

    @Override
    public void initialize(final UniqueUsername uniqueUsername) {
    }

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext constraintValidatorContext) {
        if (username == null) {
            return false;
        }
        return !this.userService.getByUserName(username).isPresent();
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.userService = applicationContext.getBean(UserService.class);
    }
}
