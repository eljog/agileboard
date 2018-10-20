package com.eljo.agileboard.exception;

/**
 * Created by Eljo.George on 10/7/2018.
 */
public class AgileBoardException extends Exception {
    public AgileBoardException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgileBoardException(String message) {
        super(message);
    }

    public AgileBoardException(Throwable rootCause) {
        super(rootCause);
    }
}
