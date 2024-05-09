package org.fonzhamilton.visualis.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
// Responsible for validating fields annotated with @FieldMatch
@Constraint(validatedBy = FieldMatchValidator.class)
// Specifies the element types which this annotation can be applied.
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
// Retention policy for this annotation
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldMatch {
    String message() default "";            //  Default error message to be used if validation fails.
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // Name of fields to be compared.
    String first();
    String second();

    //  Allows for multiple FieldMatch constraints to a single element.
    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        FieldMatch[] value();
    }
}
