package com.group14.exception;

public class NotFoundException extends BankingException {
    public NotFoundException(String message) { super(message); }

    public static NotFoundException of(String entity, Object id) {
        return new NotFoundException(entity + " not found: " + id);
    }
}
