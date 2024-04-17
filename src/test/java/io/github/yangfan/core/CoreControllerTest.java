package io.github.yangfan.core;

import io.github.yangfan.core.foo.FooBarResponse;
import io.github.yangfan.core.foo.FooService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoreController.class)
class CoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FooService fooService;

    @SneakyThrows
    @Test
    void getFooBar() {
        given(fooService.getFoo(any())).willReturn(new FooBarResponse("cherubim"));

        mockMvc.perform(get("/api/foos").queryParam("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("cherubim"));
    }
}