package com.sigma.beacon_control.common.operation;

import com.sigma.beacon_control.common.util.exceptions.OperationException;

import java.sql.SQLException;

/**
 * Created by Wilson on 4/16/17.
 */
public interface Operation<T> {
    T operate() throws OperationException;
}
