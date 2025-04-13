package me.kire.eventsourcing.domain.model;

import lombok.Getter;
import me.kire.eventsourcing.domain.event.AccountCreated;
import me.kire.eventsourcing.domain.event.Event;
import me.kire.eventsourcing.domain.event.MoneyDeposited;
import me.kire.eventsourcing.domain.event.MoneyWithdrawn;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    @Getter
    private String accountId;
    @Getter
    private Double balance = 0.0;

    private final List<Event> changes = new ArrayList<>();

    public static BankAccount create(String accountId) {
        BankAccount account = new BankAccount();
        account.applyChange(new AccountCreated(accountId));
        return account;
    }

    public void deposit(Double amount) {
        this.applyChange(new MoneyDeposited(this.accountId, amount));
    }

    public void withdraw(Double amount) {
        if (this.balance < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        this.applyChange(new MoneyWithdrawn(this.accountId, amount));
    }

    public void applyChange(Event event) {
        this.onEvent(event);
        this.changes.add(event);
    }

    public void onEvent(Event event) {
        if (event instanceof AccountCreated(String accountIdCreated)) {
            this.accountId = accountIdCreated;
        } else if (event instanceof MoneyDeposited(String ignored, Double amount)) {
            this.balance += amount;
        } else if (event instanceof MoneyWithdrawn(String ignored, Double amount)) {
            this.balance -= amount;
        }
    }

    public List<Event> getUncommittedChanges() {
        return this.changes;
    }

    public void markCommitted() {
        this.changes.clear();
    }
}
