package com.eljo.agileboard.exception;

import graphql.ErrorType;

/**
 * Created by Eljo.George on 10/7/2018.
 */
public class InvalidRecordExeption extends AgileBoardGraphQLException {
    public InvalidRecordExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRecordExeption(String message) {
        super(message);
    }

    public InvalidRecordExeption(Throwable rootCause) {
        super(rootCause);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
    }
}
