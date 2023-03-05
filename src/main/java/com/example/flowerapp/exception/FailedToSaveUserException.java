package com.example.flowerapp.exception;

import java.sql.SQLException;

public class FailedToSaveUserException extends SQLException {

    /**
     * 
     */
    public FailedToSaveUserException() {
    }

    /**
     * @param message
     */
    public FailedToSaveUserException(String message) {
        super(message);
    }

    /**
     * @param message
     */
    public FailedToSaveUserException(Throwable message) {
        super(message);
    }

    /**
     * @param message
     * @param arg1
     */
    public FailedToSaveUserException(String message, String arg1) {
        super(message, arg1);
    }

    /**
     * @param message
     * @param arg1
     */
    public FailedToSaveUserException(String message, Throwable arg1) {
        super(message, arg1);
    }

    /**
     * @param message
     * @param arg1
     * @param arg2
     */
    public FailedToSaveUserException(String message, String arg1, int arg2) {
        super(message, arg1, arg2);
    }

    /**
     * @param message
     * @param arg1
     * @param arg2
     */
    public FailedToSaveUserException(String message, String arg1, Throwable arg2) {
        super(message, arg1, arg2);
    }

    /**
     * @param message
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public FailedToSaveUserException(String message, String arg1, int arg2, Throwable arg3) {
        super(message, arg1, arg2, arg3);
    }

    
    
}
