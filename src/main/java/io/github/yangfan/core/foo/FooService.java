package io.github.yangfan.core.foo;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FooService {

    private final FooClient fooClient;

    public FooBarResponse getFoo(String userId) {
        val fooResponse = fooClient.getFoo(userId);
        return new FooBarResponse(String.format("%s, %s", fooResponse.getFieldA(), fooResponse.getFieldB()));
    }
}
