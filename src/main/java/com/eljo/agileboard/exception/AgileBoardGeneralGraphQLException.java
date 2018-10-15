package com.eljo.agileboard.exception;

import graphql.ErrorType;

/**
 * Created by Eljo.George on 10/14/2018.
 */
public class AgileBoardGeneralGraphQLException extends AgileBoardGraphQLException {

    public AgileBoardGeneralGraphQLException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgileBoardGeneralGraphQLException(String message) {
        super(message);
    }
    public AgileBoardGeneralGraphQLException() {
        super("Unknown GraphQL Exception");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ExecutionAborted;
    }
}
