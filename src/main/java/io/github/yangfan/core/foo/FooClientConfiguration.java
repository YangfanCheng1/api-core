package io.github.yangfan.core.foo;

import feign.RequestInterceptor;
import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import io.github.yangfan.core.common.util.CommonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
