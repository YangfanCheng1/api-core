package io.github.yangfan.core.common.util;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.github.yangfan.core.common.util.CommonUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CommonUtilTest {

    void customMapBuilderTest() {
        val customMap = new CustomImmutableMapBuilder<String, Object>()
                .putIfValNotNull("milk", "coffee")
                .putIfValNotNull("cream", null)
                .putIfValNotNull("sugar", null)
                .build();

        assertThat(customMap.get("milk")).isEqualTo("coffee");
        assertThat(customMap.get("cream")).isNull();
    }

    @Test
    void of() {
        assertThatCode(() -> ComparableObj.of("")).isNull();
    }

    @Test
    void ofNull() {
        assertThatCode(() -> ComparableObj.of(null))
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Arg cannot be null");
    }

    @ParameterizedTest(name = "{0} <= {1}")
    @CsvSource({
            "1, 2, true",
            "0, 1, true",
            "1, 1, true",
            "1, 0, false",
            "2, 1, false"
    })
    void isLessThanOrEqualTo(int left, int right, boolean expected) {
        assertThat(ComparableObj.of(left).isLessThanOrEqualTo(right))
                .isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0} > {1}")
    @CsvSource({
            "1, 2, false",
            "0, 1, false",
            "1, 1, false",
            "1, 0, true",
            "2, 1, true"
    })
    void isGreaterThan(int left, int right, boolean expected) {
        assertThat(ComparableObj.of(left).isGreaterThan(right))
                .isEqualTo(expected);
    }
}