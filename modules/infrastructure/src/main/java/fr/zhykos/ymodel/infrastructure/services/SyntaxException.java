package fr.zhykos.ymodel.infrastructure.services;

public final class SyntaxException extends Exception {

    public SyntaxException(final Throwable cause) {
        super(cause);
    }

    public SyntaxException(final String message) {
        super(message);
    }

}
