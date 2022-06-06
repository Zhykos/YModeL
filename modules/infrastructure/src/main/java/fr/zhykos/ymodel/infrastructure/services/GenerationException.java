package fr.zhykos.ymodel.infrastructure.services;

/**
 * Exception while generating a new Class
 */
public final class GenerationException extends Exception {

    /**
     * New Exception caused by...
     *
     * @param cause The cause
     */
    public GenerationException(final Throwable cause) {
        super(cause);
    }

}
