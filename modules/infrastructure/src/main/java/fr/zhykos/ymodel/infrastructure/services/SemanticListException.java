package fr.zhykos.ymodel.infrastructure.services;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SemanticException is thrown when a semantic error is detected.
 * That means that the model is not valid.
 */
public final class SemanticListException extends Exception {

    /**
     * Origin file of the error.
     */
    private final File originFile;

    /**
     * List of exceptions.
     */
    private final List<String> subExceptions;

    /**
     * New exception with a list of specific exceptions.
     *
     * @param file       Origin file of the error.
     * @param exceptions The list of exceptions
     */
    public SemanticListException(final File file, final List<String> exceptions) {
        if (exceptions.isEmpty()) {
            throw new IllegalArgumentException("The list of exceptions cannot be empty");
        }
        this.originFile = file;
        this.subExceptions = exceptions;
    }

    @Override
    public String getMessage() {
        return "Transformation error for file '%s':%n%s."
                .formatted(this.originFile.getName(),
                        this.subExceptions.stream().collect(Collectors.joining(System.lineSeparator())));
    }

}
