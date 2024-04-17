package io.github.yangfan.core.foo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@Builder
public class FooRequest {

    @NotBlank
    @Size(min = 1, max = 32)
    String fieldA;

}
