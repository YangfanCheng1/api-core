package io.github.yangfan.core;

import io.github.yangfan.core.foo.FooBarResponse;
import io.github.yangfan.core.foo.FooRequest;
import io.github.yangfan.core.foo.FooService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class CoreController {

    private final FooService fooService;

    @GetMapping("foos")
    public FooBarResponse getFooBar(@RequestParam String id) {
        return fooService.getFoo(id);
    }

    @PostMapping("foos")
    public FooBarResponse addFoo(@RequestBody @Validated FooRequest fooRequest) {
        return fooService.addFoo(fooRequest);
    }
}
