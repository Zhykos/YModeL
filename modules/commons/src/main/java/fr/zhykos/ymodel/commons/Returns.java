package fr.zhykos.ymodel.commons;

import java.util.Optional;

import lombok.AllArgsConstructor;

/**
 * An object containing a result or an Exception. It's like a Javascript Promise
 * but without the async feature.
 *
 * @param <O> O: the type of the result
 * @param <E> E: the type of the Exception
 */
@AllArgsConstructor
public final class Returns<O, E extends Exception> {

    /**
     * The result
     */
    private final Optional<O> object;

    /**
     * The exception
     */
    private final Optional<E> exception;

    /**
     * @return The result or throw an exception if it exists
     * @throws E The Exception if it's present
     */
    public O then() throws E {
        catchh();
        return this.object.orElseThrow();
    }

    /**
     * @throws E The Exception if it's present
     */
    public void catchh() throws E {
        if (this.exception.isPresent()) {
            throw this.exception.orElseThrow();
        }
    }

    /**
     * Create a Returns with a Result
     *
     * @param <O>    O: the type of the result
     * @param <E>    E: the type of the Exception
     * @param object The result to wrap into the Returns object
     * @return The Returns object with the parameter object
     */
    public static <O, E extends Exception> Returns<O, E> resolve(final O object) {
        return new Returns<>(Optional.of(object), Optional.empty());
    }

    /**
     * Create a Returns with an Exception
     *
     * @param <O>       O: the type of the result
     * @param <E>       E: the type of the Exception
     * @param exception The Exception to keep and might throw later
     * @return The Returns object with the Exception
     */
    public static <O, E extends Exception> Returns<O, E> reject(final E exception) {
        return new Returns<>(Optional.empty(), Optional.of(exception));
    }

}
