package io.github.yangfan.core.bar;

import org.springframework.stereotype.Service;

@Service
public class BarService {

    public BarResponse getBar() {
        return BarResponse.of("todo");
    }
}
