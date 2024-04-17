package io.github.yangfan.core.common.util;

import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.yangfan.core.common.util.CommonUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonUtilTest {

    @Test
    void customMapBuilderTest() {
        val customMap = new CustomImmutableMapBuilder<String, Object>()
                .putIfValNotNull("milk", "coffee")
                .putIfValNotNull("cream", null)
                .putIfValNotNull("sugar", null)
                .build();

        assertThat(customMap.get("milk")).isEqualTo("coffee");
        assertThat(customMap.get("cream")).isNull();
    }

    @Test
    void of() {
        assertThatCode(() -> ComparableObj.of("")).isNull();
    }

    @Test
    void ofNull() {
        assertThatCode(() -> ComparableObj.of(null))
                .isNotNull()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Arg cannot be null");
    }

    @ParameterizedTest(name = "{0} <= {1}")
    @CsvSource({
            "1, 2, true",
            "0, 1, true",
            "1, 1, true",
            "1, 0, false",
            "2, 1, false"
    })
    void isLessThanOrEqualTo(int left, int right, boolean expected) {
        assertThat(ComparableObj.of(left).isLessThanOrEqualTo(right))
                .isEqualTo(expected);
    }

    @ParameterizedTest(name = "{0} > {1}")
    @CsvSource({
            "1, 2, false",
            "0, 1, false",
            "1, 1, false",
            "1, 0, true",
            "2, 1, true"
    })
    void isGreaterThan(int left, int right, boolean expected) {
        assertThat(ComparableObj.of(left).isGreaterThan(right))
                .isEqualTo(expected);
    }


    @Disabled
    @Test
    void LRUCacheTesting() throws InterruptedException {
        int MAX_SIZE = 1;
        Map<String, String> cache = Collections.synchronizedMap(new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > MAX_SIZE;
            }
        });

        List<Runnable> tasks = Arrays.asList(
                () -> {
                    cache.put("k0", "v0");
                    cache.put("k1", "v1");
                },
                () -> {
                    cache.put("k0", "v0");
                    cache.put("k2", "v2");
                }
        );

        /** we have two threads running in parallel to put values into thread-safe map, however it's hard to simulate race conditions **/
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture[] futures = tasks.stream()
                .map(task -> CompletableFuture.runAsync(task, executorService))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        executorService.shutdown();

        assertAll("Checking Cache State",
                () -> assertTrue(cache.size() == 1),
                () -> assertFalse(cache.containsKey("k0")),
                () -> assertTrue((cache.containsKey("k1") || cache.containsKey("k2")))
        );
    }

}