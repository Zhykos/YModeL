package fr.zhykos.ymodel.infrastructure.services;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * SemanticException is thrown when a semantic error is detected.
 * That means that the model is not valid.
 */
public final class SemanticListException extends Exception {

    @Getter
    private final List<SemanticException> exceptions;

    public SemanticListException(final List<SemanticException> exceptions) {
        if (exceptions.isEmpty()) {
            throw new IllegalArgumentException("The list of exceptions cannot be empty");
        }
        this.exceptions = exceptions;
    }

    @Override
    public String getMessage() {
        return this.exceptions.stream().map(SemanticException::getMessage).collect(Collectors.joining("\n"));
    }

}
