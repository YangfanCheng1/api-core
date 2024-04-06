package io.github.yangfan.core.foo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "foo-api",
        url = "${foo-api:http://localhost:8090}",
        configuration = FooClientConfiguration.class,
        fallbackFactory = FooClient.FooClientFallBackFactory.class
)
public interface FooClient {

    @GetMapping("/users/{userId}")
    FooResponse getFoo(@PathVariable String userId);

    @Slf4j
    @Component
    class FooClientFallBackFactory implements FallbackFactory<FooClient> {
        @Override
        public FooClient create(Throwable cause) {
            log.info("Couldn't get foo response '{}'", cause.getLocalizedMessage());
            return (userId) -> FooResponse.builder()
                    .fieldA("a")
                    .fieldB("b")
                    .build();
        }
    }
}
