package com.eljo.agileboard.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

/**
 * Created by Eljo.George on 10/15/2018.
 */
public class AgileBoardRuntimeGraphQLException extends RuntimeException implements GraphQLError {
    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ExecutionAborted;
    }

    public AgileBoardRuntimeGraphQLException(String message) {
        super(message);
    }

    public AgileBoardRuntimeGraphQLException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgileBoardRuntimeGraphQLException(Throwable cause) {
        super(cause);
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }
}
