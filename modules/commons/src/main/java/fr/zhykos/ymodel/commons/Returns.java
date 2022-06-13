/*
 * Copyright 2022 Thomas "Zhykos" Cicognani.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
