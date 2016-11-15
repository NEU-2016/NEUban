package hu.unideb.inf.rft.neuban.service.annotations;

import hu.unideb.inf.rft.neuban.service.annotations.validators.UniqueUsernameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameConstraintValidator.class)
@Documented
public @interface UniqueUsername {
    String message() default "not.unique.username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
