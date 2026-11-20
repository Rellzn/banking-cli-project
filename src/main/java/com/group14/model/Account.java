package com.group14.model; 

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

import com.group14.exception.AccountStatusException;
import com.group14.exception.InsufficientFundsException;
import com.group14.exception.UnauthorizedException;
import com.group14.exception.ValidationException;
import com.group14.model.enums.AccountStatus;

public class Account {

    private static final int SCALE = 4;

    private final Long accountId;
    private BigDecimal balance;
    private AccountStatus status;
    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Account () {
        this(null, BigDecimal.ZERO.setScale(SCALE), AccountStatus.ACTIVE, OffsetDateTime.now(), OffsetDateTime.now());
    }
    public Account(Long accountId, BigDecimal balance, AccountStatus status,
                    OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        Objects.requireNonNull(balance, "balance must not be null");
        Objects.requireNonNull(status, "status must not be null");
        
        if (balance.signum() < 0) { 
            //can't open an account in overdraft
            throw new ValidationException("New Account balance cannot be negative");
        }
        if (status == AccountStatus.CLOSED && balance.signum() != 0) {
            //closed account must be empty, custom exception
            throw new ValidationException("Closed account must be empty");
        }
        this.accountId = accountId;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getters

    public Long getAccountId() { return accountId; }
    public BigDecimal getBalance() { return balance; }
    public AccountStatus getStatus() { return status; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public boolean isActive() { return status == AccountStatus.ACTIVE; }

    public void deposit(BigDecimal quantity) {
        requireActive();
        balance = balance.add(quantity);
        updatedAt = OffsetDateTime.now();
    }

    public void withdraw(BigDecimal quantity) {
        validateAmount(quantity);
        requireActive();
        if (balance.compareTo(quantity) < 0) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal of " + quantity + " from account " + accountId);
        }
        balance = balance.subtract(quantity);
        updatedAt = OffsetDateTime.now();
    }

    private void requireActive() {
        if (status != AccountStatus.ACTIVE) {
            throw new UnauthorizedException("Operation not allowed on non-active account " + accountId);
        }
    }

    private void validateAmount(BigDecimal quantity) {
        if (quantity.signum() <= 0) {
            throw new ValidationException("Amount must be greater than zero");
        }
        if (quantity.stripTrailingZeros().scale() > SCALE) {
            throw new ValidationException("Amount must not have more than " + SCALE + " decimal places");
        }
    }

    public void freeze() {
        requireActive();
        status = AccountStatus.FROZEN;
    }

    public void unfreeze() {
        if (status != AccountStatus.FROZEN) {
            throw new AccountStatusException("Non-frozen account " + accountId + " cannot be unfrozen");
        }

        status = AccountStatus.ACTIVE;
    }

    public void close() {
        if (status == AccountStatus.CLOSED) {
            throw new AccountStatusException("Account " + accountId + " is already closed");
        }
        if (balance.signum() != 0) {
            throw new AccountStatusException("Account " + accountId + " must be empty to close");
        }
        requireActive();
        status = AccountStatus.CLOSED;
    }

    //identity functions

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account other)) return false;
        return accountId != null && accountId.equals(other.accountId);
    }

    @Override
    public int hashCode() { return Objects.hashCode(accountId); }

    @Override
    public String toString() {
        return "Account{id=" + accountId + ", balance=" + balance + ", status=" + status + "}";
    }

}
