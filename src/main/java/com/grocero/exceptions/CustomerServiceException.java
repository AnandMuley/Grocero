package com.grocero.exceptions;

public class CustomerServiceException extends Exception{

    public CustomerServiceException() {
        super();
    }

    public CustomerServiceException(String message) {
        super(message);
    }

    public CustomerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerServiceException(Throwable cause) {
        super(cause);
    }

    protected CustomerServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
