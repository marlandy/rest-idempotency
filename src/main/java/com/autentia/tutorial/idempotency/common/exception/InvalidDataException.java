package com.autentia.tutorial.idempotency.common.exception;

/**
 *
 * @author marlandy
 */
public class InvalidDataException extends RuntimeException {

    private static final long serialVersionUID = 8628525520554471663L;

    public InvalidDataException(String message) {
        super(message);
    }

}
