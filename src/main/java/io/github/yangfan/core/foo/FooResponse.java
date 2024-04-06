package io.github.yangfan.core.foo;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@Builder
public class FooResponse {

    String fieldA;
    String fieldB;
    NestedObject nestedObject;

    @Value
    @With
    @Builder
    public static class NestedObject {
        String nestedFieldA;
        String nestedFieldB;
    }
}
