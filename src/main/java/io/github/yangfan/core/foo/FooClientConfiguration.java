package io.github.yangfan.core.foo;

import feign.*;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

import static io.github.yangfan.core.common.util.CommonUtil.*;

@Configuration
public class FooClientConfiguration  {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("foo", "bar");
        };
    }

    // Alternatively, we could bind using application.yml/properties
    @Profile({"default", "local", "dev"})
    @Bean
    Logger.Level logLevel() {
        return Logger.Level.FULL;
    }

    public class CustomDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            String body = Optional.ofNullable(response.body()).map(Object::toString).orElse("NO BODY");
            if (ComparableObj.of(response.status()).isAnyOf(429, 500, 502, 503, 504)) {
                throw new RetryableException(
                        response.status(),
                        body,
                        response.request().httpMethod(),
                        null,
                        response.request()
                );
            }

            return new ErrorDecoder.Default().decode(methodKey, response);
        }
    }

    public class CustomRetryer {
        public Retryer retry() {
            return new Retryer.Default(100, 1000, 2);
        }
    }
}
