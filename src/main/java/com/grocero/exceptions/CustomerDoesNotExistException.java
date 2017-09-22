package com.grocero.exceptions;

public class CustomerDoesNotExistException extends CustomerServiceException {

    public static final String DEFAULT_MSG = "Customer does not exist";

    public CustomerDoesNotExistException() {
        super(DEFAULT_MSG);
    }
}
