package com.eljo.agileboard.aspect;

import com.eljo.agileboard.exception.AgileBoardRuntimeGraphQLException;
import graphql.GraphQLError;
import lombok.extern.java.Log;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

/**
 * Created by Eljo.George on 10/17/2018.
 */

@Component
@Log
@Aspect
public class ExceptionAspect {

    @AfterThrowing(value = "execution(* com.eljo.agileboard.graphql.*.*(..))", throwing = "throwable")
    public void exceptionRethrowForGraphQLComponents(Exception throwable) throws Throwable {
        log.log(Level.SEVERE, "ExceptionAspect::exceptionRethrowForGraphQLComponents", throwable);

        if (throwable instanceof GraphQLError) {
            throw throwable;
        }
        throw new AgileBoardRuntimeGraphQLException(ExceptionUtils.getRootCause(throwable) != null ? ExceptionUtils.getRootCause(throwable) : throwable);
    }
}
