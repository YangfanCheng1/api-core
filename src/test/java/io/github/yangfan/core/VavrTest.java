package io.github.yangfan.core;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VavrTest {

    public Either<String, Integer> divide(int divisor, int dividend) {
        if (dividend == 0) {
            return Either.left("Could not divide by 0");
        }

        return Either.right(divisor/dividend);
    }


    @Test
    void testEither() {
        var either = this.divide(1, 0);
        assertThat(either.isLeft()).isTrue();
    }

}
