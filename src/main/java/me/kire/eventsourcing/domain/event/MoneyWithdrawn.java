package me.kire.eventsourcing.domain.event;

import java.time.Instant;

public record MoneyWithdrawn(
        String accountId,
        Double amount,
        Instant occurredOn
) implements Event {
    public MoneyWithdrawn(String accountId, Double amount) {
        this(accountId, amount, Instant.now());
    }
}
