package com.group14.exception;

public class DataAccessException extends BankingException {
    public DataAccessException(String message, Throwable cause) { super(message, cause); }
    public DataAccessException(String message) { super(message); }
}
