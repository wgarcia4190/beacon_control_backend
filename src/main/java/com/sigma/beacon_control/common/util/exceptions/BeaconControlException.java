package com.sigma.beacon_control.common.util.exceptions;

/**
 * Created by Wilson on 4/16/17.
 */
public class BeaconControlException extends RuntimeException {

    public enum ExceptionLevel {
        FATAL,
        ERROR,
        WARN
    }

    private Object requestParams;
    private ExceptionLevel exceptionLevel = ExceptionLevel.ERROR;

    public BeaconControlException(){}

    public BeaconControlException(String message){
        super(message);
    }

    public BeaconControlException(String message, Throwable cause, Object requestParams) {
        super(message, cause);
        this.requestParams = requestParams;
    }

    public BeaconControlException(Throwable cause, Object requestParams) {
        super(cause);
        this.requestParams = requestParams;
    }


    public BeaconControlException(Throwable cause) {
        super(cause);
    }


    public BeaconControlException(Object requestParams){
        this.requestParams = requestParams;
    }

    public BeaconControlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public Object getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Object requestParams) {
        this.requestParams = requestParams;
    }

    public ExceptionLevel getExceptionLevel() {
        return exceptionLevel;
    }

    public void setExceptionLevel(ExceptionLevel exceptionLevel) {
        this.exceptionLevel = exceptionLevel;
    }

}
