package com.group14.exception;

public class UnauthorizedException extends BankingException{
    public UnauthorizedException(String message) { super(message); }
}
