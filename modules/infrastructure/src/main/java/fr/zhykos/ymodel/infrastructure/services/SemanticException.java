package fr.zhykos.ymodel.infrastructure.services;

/**
 * SemanticException is thrown when a semantic error is detected.
 * That means that the model is not valid.
 */
public final class SemanticException extends Exception {

    /**
     * New SemanticException with a message.
     *
     * @param message The message of the exception
     */
    public SemanticException(final String message) {
        super(message);
    }

}