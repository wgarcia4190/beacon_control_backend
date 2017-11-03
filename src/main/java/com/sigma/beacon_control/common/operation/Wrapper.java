package com.sigma.beacon_control.common.operation;

import com.sigma.beacon_control.common.util.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javalite.activejdbc.DBException;

import static spark.Spark.halt;

/**
 * Created by Wilson on 4/16/17.
 */
public abstract class Wrapper<T> {
    private static final Logger log = LogManager.getLogger(Wrapper.class);

    protected HttpStatus UNPROCESSABLE_ENTITY = HttpStatus.UNPROCESSABLE_ENTITY;
    protected HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    protected abstract T wrap(Operation<T> operation);

    protected void createConstraintError(String message, String contraintErrorMessage) {
        int constraintIndex = message.lastIndexOf(contraintErrorMessage);
        String constraintName = message.substring(constraintIndex + contraintErrorMessage.length() + 1);
        constraintIndex = constraintName.indexOf('\n');
        constraintName = constraintName.substring(0, constraintIndex);
        halt(UNPROCESSABLE_ENTITY.getCode(), "constraint error: " + constraintName.replace('"', ' '));
    }

    protected void handleDBException(DBException ex) {
        log.error("Error: "+ex.getMessage(), ex);
        Throwable error = ex.getCause();
        if (error != null) {
            String message = error.getMessage();
            if (message.contains("ERROR: duplicate key")) {
                halt(UNPROCESSABLE_ENTITY.getCode(), "Entity already exists");
            }
            if (message.contains("violates check constraint")) {
                createConstraintError(message, "violates check constraint");
            }
            if (message.contains("violates foreign key constraint")) {
                createConstraintError(message, "violates foreign key constraint");
            }
            if (message.contains("ERROR: null value in column")) {
                createConstraintError(message, "ERROR: null value in column");
            }
            if (message.contains("ERROR: value too long for type character(1)")) {
                halt(UNPROCESSABLE_ENTITY.getCode(), "constraint error: value too long for a field");
            }
            halt(INTERNAL_SERVER_ERROR.getCode(), message);
        }
        halt(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getDesc());
    }
}
