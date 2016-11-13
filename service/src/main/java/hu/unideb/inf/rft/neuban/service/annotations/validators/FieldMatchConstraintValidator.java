package hu.unideb.inf.rft.neuban.service.annotations.validators;

import hu.unideb.inf.rft.neuban.service.annotations.FieldMatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class FieldMatchConstraintValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch fieldMatch) {
        firstFieldName = fieldMatch.firstFieldName();
        secondFieldName = fieldMatch.secondFieldName();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext constraintValidatorContext) {
        try {
            final Object firstObject = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObject = BeanUtils.getProperty(value, secondFieldName);
            return firstObject == null && secondObject == null || firstObject != null && firstObject.equals(secondObject);
        } catch (final Exception e) {
            log.debug(FieldMatchConstraintValidator.class.getName(), e.getMessage());
        }
        return true;
    }
}
