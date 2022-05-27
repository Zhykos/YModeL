package fr.zhykos.ymodel.business.service;

import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class Returns<O, E extends Exception> {

    private final Optional<O> object;

    private final Optional<E> exception;

    public O then() {
        return this.object.orElseThrow();
    }

    public void catchh() throws E {
        throw this.exception.orElseThrow();
    }

    public static <O, E extends Exception> Returns<O, E> resolve(final O object) {
        return new Returns<>(Optional.of(object), Optional.empty());
    }

    public static <O, E extends Exception> Returns<O, E> reject(final E exception) {
        return new Returns<>(Optional.empty(), Optional.of(exception));
    }

}
