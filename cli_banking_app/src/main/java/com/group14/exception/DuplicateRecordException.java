package com.group14.exception;

public class DuplicateRecordException extends BankingException {
    public DuplicateRecordException(String message) { super(message); }
    public DuplicateRecordException(String message, Throwable cause) { super(message, cause); }
}

