package com.group14.exception;

public class AuthenticationException extends BankingException {
    public AuthenticationException(String message) { super(message); }
}
