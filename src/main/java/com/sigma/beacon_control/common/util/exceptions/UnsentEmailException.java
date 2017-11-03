package com.sigma.beacon_control.common.util.exceptions;

/**
 * Created by Wilson on 9/16/17.
 */
public class UnsentEmailException extends BeaconControlException {

    {
        setExceptionLevel(ExceptionLevel.ERROR);
    }

    private final int code;

    public UnsentEmailException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
