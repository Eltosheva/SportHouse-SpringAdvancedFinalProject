package com.eltosheva.sporthouse.web;

public class DBInconsistentException extends RuntimeException {

    public DBInconsistentException(String message) {
        super(message);
    }
}
