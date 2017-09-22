package com.grocero.exceptions;

public class DuplicateMasterListException extends CustomerServiceException {

    private static String DEFAULT_MSG = "Master list already exists";

    public DuplicateMasterListException() {
        super(DEFAULT_MSG);
    }
}
