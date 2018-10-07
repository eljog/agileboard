package com.eljo.agileboard.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

/**
 * Created by Eljo.George on 10/7/2018.
 */
public abstract class AgileBoardGraphQLException extends AgileBoardException implements GraphQLError {
    public AgileBoardGraphQLException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgileBoardGraphQLException(String message) {
        super(message);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }
}
