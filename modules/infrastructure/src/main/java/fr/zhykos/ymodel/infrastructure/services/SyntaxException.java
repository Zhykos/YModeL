package fr.zhykos.ymodel.infrastructure.services;

/**
 * Syntax exception thrown when a syntax error is detected in a YML file
 */
public final class SyntaxException extends Exception {

    /**
     * Constructor
     *
     * @param cause Cause of the exception
     */
    public SyntaxException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor
     *
     * @param message Message of the exception
     */
    public SyntaxException(final String message) {
        super(message);
    }

}
