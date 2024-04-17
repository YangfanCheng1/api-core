package io.github.yangfan.core.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotBlank
@Size(min = 1, max = 32)
@Pattern(regexp = "^[a-zA-Z0-9]+$")
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidField {
    String message() default "Value length must be between 1 and 32, inclusively";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}