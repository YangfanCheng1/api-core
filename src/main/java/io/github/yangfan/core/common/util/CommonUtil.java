package io.github.yangfan.core.common.util;

import com.google.common.collect.ImmutableMap;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.function.Predicate;

public class CommonUtil {

    public static class CustomImmutableMapBuilder<K, V> extends ImmutableMap.Builder<K, V> {
        public CustomImmutableMapBuilder<K, V> putIfValNotNull(K key, V val) {
            if (val != null) super.put(key, val);
            return this;
        }

        @Override
        public CustomImmutableMapBuilder<K, V> put(@NonNull K key, @NonNull V val) {
            super.put(key, val);
            return this;
        }
    }

    public static class ComparableObj<T extends Comparable<T>> {
        private T val;

        private ComparableObj(T t) {this.val = t;}

        public static <T extends Comparable<T>> ComparableObj<T> of(T t) {
            Assert.notNull(t, "Arg cannot be null");
            return new ComparableObj<>(t);
        }

        @SafeVarargs
        public final boolean isAnyOf(T... values) {
            return Arrays.asList(values).contains(this.val);
        }

        public final boolean isLessThanOrEqualTo(T operand) {
            return isLessThan.or(isEqualTo).test(operand);
        }

        public final boolean isGreaterThan(T operand) {
            return isGreaterThan.test(operand);
        }

        public final boolean isEqualTo(T operand) {
            return isEqualTo.test(operand);
        }

        private final Predicate<T> isLessThan = arg -> this.val.compareTo(arg) < 0;

        private final Predicate<T> isEqualTo = arg -> this.val.compareTo(arg) == 0;

        private final Predicate<T> isGreaterThan = arg -> this.val.compareTo(arg) > 0;

    }
}
