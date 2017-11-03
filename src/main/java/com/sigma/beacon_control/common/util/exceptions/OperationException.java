package com.sigma.beacon_control.common.util.exceptions;

/**
 * Created by Wilson on 4/16/17.
 */
public class OperationException extends BeaconControlException {

    {
        setExceptionLevel(ExceptionLevel.ERROR);
    }

    private final int code;

    public OperationException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
