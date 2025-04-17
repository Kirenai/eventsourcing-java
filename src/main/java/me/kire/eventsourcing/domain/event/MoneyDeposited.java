package me.kire.eventsourcing.domain.event;

import java.time.Instant;

public record MoneyDeposited(
        String accountId,
        Double amount,
        Instant occurredOn
) implements Event {
    public MoneyDeposited(String accountId, Double amount) {
        this(accountId, amount, Instant.now());
    }
}
