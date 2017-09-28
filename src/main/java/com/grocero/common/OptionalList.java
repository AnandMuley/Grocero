package com.grocero.common;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class OptionalList<T extends Collection> {

    private static final OptionalList<?> EMPTY = new OptionalList<>();

    private final T value;

    private OptionalList() {
        this.value = null;
    }

    public static <T extends Collection> OptionalList<T> empty() {
        @SuppressWarnings("unchecked")
        OptionalList<T> t = (OptionalList<T>) EMPTY;
        return t;
    }

    private OptionalList(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T extends Collection> OptionalList<T> of(T value) {
        return new OptionalList<>(value);
    }

    public static <T extends Collection> OptionalList<T> ofNullable(T value) {
        return value == null || value.size() == 0 ? empty() : of(value);
    }


    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public boolean isPresent() {
        return value != null && value.size() > 0;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null)
            consumer.accept(value);
    }

    public OptionalList<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent())
            return this;
        else
            return predicate.test(value) ? this : empty();
    }


    public <U extends Collection> OptionalList<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return OptionalList.ofNullable(mapper.apply(value));
        }
    }

    public <U extends Collection> OptionalList<U> flatMap(Function<? super T, OptionalList<U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Objects.requireNonNull(mapper.apply(value));
        }
    }

    public T orElse(T other) {
        return value != null ? value : other;
    }

    public T orElseGet(Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OptionalList)) {
            return false;
        }

        OptionalList<?> other = (OptionalList<?>) obj;
        return Objects.equals(value, other.value);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("OptionalList[%s]", value)
                : "OptionalList.empty";
    }
}
