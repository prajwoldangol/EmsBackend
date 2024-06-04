package com.prajwol.utility;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class FieldUtils {
    public static final Predicate<String> NOT_EMPTY_STRING = s -> s != null && !s.isEmpty();

    public static <T> void updateFieldIfPresent(T value, Consumer<T> updater, Predicate<T> condition) {
        if (value != null &&  condition.test(value)) {
            updater.accept(value);
        }
    }
}
