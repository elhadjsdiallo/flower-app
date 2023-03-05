package com.example.flowerapp.exception;

public class ServiceBaseException extends Exception {

    /**
     * 
     */
    public ServiceBaseException() {
    }

    /**
     * @param arg0
     */
    public ServiceBaseException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public ServiceBaseException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ServiceBaseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public ServiceBaseException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    
    
}
