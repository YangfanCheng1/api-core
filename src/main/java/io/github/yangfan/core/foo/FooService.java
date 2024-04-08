package io.github.yangfan.core.foo;

import io.github.yangfan.core.common.logging.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FooService {

    private final FooClient fooClient;

    @LogExecutionTime
    public FooBarResponse getFoo(String userId) {
        val fooResponse = fooClient.getFoo(userId);
        return new FooBarResponse(String.format("%s, %s", fooResponse.getFieldA(), fooResponse.getFieldB()));
    }
}
