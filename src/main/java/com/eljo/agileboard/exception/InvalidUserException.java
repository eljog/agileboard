package com.eljo.agileboard.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Eljo.George on 10/7/2018.
 */
public class InvalidUserException extends AgileBoardGraphQLException {
    public InvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
    }
}
