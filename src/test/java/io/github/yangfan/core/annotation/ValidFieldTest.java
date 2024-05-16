package io.github.yangfan.core.annotation;

import io.github.yangfan.core.common.annotation.ValidField;
import jakarta.validation.Validation;
import lombok.Value;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidFieldTest {

    @Value
    static class Name {
        @ValidField
        String value;
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            "@@"
    })
    void nonAlphaNumericInputShouldResultInViolation(String name) {
        val nameTest = new Name(name);

        val constraintViolationSet = Validation.buildDefaultValidatorFactory()
                .getValidator()
                .validate(nameTest);

        assertThat(constraintViolationSet).isNotEmpty();
    }

}
