package com.group14.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

import com.group14.exception.ValidationException;
import com.group14.model.enums.TransactionType;


public final class Transaction {

    private static final int SCALE = 4;

    private final Long transactionId;
    private final Long accountId;
    private final TransactionType type;
    private final BigDecimal quantity;
    private final Long relatedAccountId;
    private final Long initiatedByEmployeeId;
    private final OffsetDateTime createdAt;

    public Transaction(Long transactionId, Long accountId, TransactionType type,
                       BigDecimal quantity, Long relatedAccountId,
                       Long initiatedByEmployeeId, OffsetDateTime createdAt) {
    
    
        this.accountId = accountId;
        this.type = type;
        Objects.requireNonNull(quantity, "quantity must not be null");

        if (quantity.signum() <= 0) { 
            //quantity must be greater than 0
            throw new ValidationException("Quantity must be greater than zero");
        }
        if (quantity.stripTrailingZeros().scale() > SCALE) {
            //enforce NUMERIC(19,4)
            throw new ValidationException("Quantity must not have more than " + SCALE + " decimal places");
        }
            
        if ((type == TransactionType.TRANSFER) != (relatedAccountId != null)) {
            //transfer has destination if and only if there is a related account
            throw new ValidationException("Transfer must have a related account, and only transfers can have a related account");
        }

        if (relatedAccountId.equals(accountId)) {
            //transfer not to self/same account
            throw new ValidationException("Transfer cannot be initiated to the same account");
        }


        this.transactionId = transactionId;
        this.quantity = quantity;
        this.relatedAccountId = relatedAccountId;
        this.initiatedByEmployeeId = initiatedByEmployeeId;
        this.createdAt = createdAt;
    }
    
    // ------- TRANSACTION FACTORIES, everything null until given by db

    public static Transaction deposit(Long accountId, BigDecimal quantity) {
        return new Transaction(null, accountId, TransactionType.DEPOSIT, quantity, null, null, null);
    }

    public static Transaction withdrawal(Long accountId, BigDecimal quantity) {
        return new Transaction(null, accountId, TransactionType.WITHDRAWAL, quantity, null, null, null);
    }

    public static Transaction transfer(Long fromAccountId, Long toAccountId, BigDecimal quantity) {
        return new Transaction(null, fromAccountId, TransactionType.TRANSFER, quantity, toAccountId, null, null);
    }

    //a wire is a transfer initiated by an employee.
    public static Transaction wire(Long fromAccountId, Long toAccountId,
                                   BigDecimal quantity, Long employeeId) {
        Objects.requireNonNull(employeeId, "employeeId");
        return new Transaction(null, fromAccountId, TransactionType.TRANSFER, quantity,
                               toAccountId, employeeId, null);
    }

    public static Transaction services(Long accountId, BigDecimal quantity, Long employeeId) {
        return new Transaction(null, accountId, TransactionType.SERVICES, quantity, null, employeeId, null);
    }

    //getters
    public Long getTransactionId() { return transactionId; }
    public Long getAccountId() { return accountId; }
    public TransactionType getType() { return type; }
    public BigDecimal getQuantity() { return quantity; }
    public OffsetDateTime getCreatedAt() { return createdAt; }

    //nullable columns optional
    public Optional<Long> getRelatedAccountId() { return Optional.ofNullable(relatedAccountId); }
    public Optional<Long> getInitiatedByEmployeeId() { return Optional.ofNullable(initiatedByEmployeeId); }

    public boolean isEmployeeInitiated() { return initiatedByEmployeeId != null; }


    //identity
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction other)) return false;
        return transactionId != null && transactionId.equals(other.transactionId);
    }

    @Override
    public int hashCode() { return Objects.hashCode(transactionId); }

    @Override
    public String toString() {
        return "Transaction{id=" + transactionId + ", account=" + accountId + ", type=" + type
                + ", quantity=" + quantity + ", related=" + relatedAccountId
                + ", employee=" + initiatedByEmployeeId + ", at=" + createdAt + "}";
    }

}
