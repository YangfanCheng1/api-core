package io.github.yangfan.core;

import io.github.yangfan.core.foo.FooBarResponse;
import io.github.yangfan.core.foo.FooService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class CoreController {

    private final FooService fooService;

    @GetMapping("users")
    public FooBarResponse getFooBar(@RequestParam String id) {
        return fooService.getFoo(id);
    }
}
