package org.fonzhamilton.visualis.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    // Extracts the field names and error message from the FieldMatch
    // annotation and stores them in the instance variables.
    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    //  Uses reflection to obtain the values of the specified fields from the object being validated.
    //  It then compares the values of these fields and sets the valid flag.
    //  Checks if two fields of a bean have the same value
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
            final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

            // Check if both fields are not null, then check that they are equal
            valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore) {
            // ignore
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)        // Specifies the property (field) associated with the constraint violation
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();   // disables the default constraint violation
        }

        return valid;
    }
}
