package hu.unideb.inf.rft.neuban.service.annotations;

import hu.unideb.inf.rft.neuban.service.annotations.validators.FieldMatchConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldMatchConstraintValidator.class)
@Documented
public @interface FieldMatch {

    String message() default "fields.not.match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstFieldName();

    String secondFieldName();
}
