package fr.zhykos.ymodel.infrastructure.services;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SemanticException is thrown when a semantic error is detected.
 * That means that the model is not valid.
 */
public final class SemanticListException extends Exception {

    /**
     * List of exceptions.
     */
    private final List<SemanticException> subExceptions;

    /**
     * New exception with a list of specific exceptions.
     *
     * @param exceptions The list of exceptions
     */
    public SemanticListException(final List<SemanticException> exceptions) {
        if (exceptions.isEmpty()) {
            throw new IllegalArgumentException("The list of exceptions cannot be empty");
        }
        this.subExceptions = exceptions;
    }

    @Override
    public String getMessage() {
        return this.subExceptions.stream().map(SemanticException::getMessage).collect(Collectors.joining("\n"));
    }

}
