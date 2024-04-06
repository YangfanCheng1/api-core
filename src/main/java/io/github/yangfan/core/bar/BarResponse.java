package io.github.yangfan.core.bar;

import lombok.Value;

@Value(staticConstructor = "of")
public class BarResponse {

    String fieldA;
}
