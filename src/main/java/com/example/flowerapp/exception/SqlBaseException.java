package com.example.flowerapp.exception;

import java.sql.SQLException;

public class SqlBaseException extends SQLException {

    /**
     * 
     */
    public SqlBaseException() {
    }

    /**
     * @param arg0
     */
    public SqlBaseException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public SqlBaseException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public SqlBaseException(String arg0, String arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public SqlBaseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public SqlBaseException(String arg0, String arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public SqlBaseException(String arg0, String arg1, Throwable arg2) {
        super(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public SqlBaseException(String arg0, String arg1, int arg2, Throwable arg3) {
        super(arg0, arg1, arg2, arg3);
    }
    
    
}
