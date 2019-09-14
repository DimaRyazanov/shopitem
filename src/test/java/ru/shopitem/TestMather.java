package ru.shopitem;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMather {
    private TestMather(){}

    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
