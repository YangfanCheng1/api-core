package io.github.yangfan.core.foo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FooServiceTest {

    @Mock
    FooClient fooClient;

    @InjectMocks
    FooService fooService;

    @Test
    void getFoo() {
        given(fooClient.getFoo(any())).willReturn(new FooResponse("a", "b", null));

        var response = fooService.getFoo("coffee");

        assertThat(response.value()).isEqualTo("a, b");
    }
}