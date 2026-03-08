package com.minkhant.patientservice.exception;

public class EmailAlreadyExistException extends Exception {
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
