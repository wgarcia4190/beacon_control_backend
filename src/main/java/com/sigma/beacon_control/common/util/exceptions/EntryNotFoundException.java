package com.sigma.beacon_control.common.util.exceptions;

/**
 * Created by Wilson on 4/16/17.
 */
public class EntryNotFoundException extends BeaconControlException {

    {
        setExceptionLevel(ExceptionLevel.WARN);
    }

    public EntryNotFoundException(String message) {
        super(message);
    }

}
