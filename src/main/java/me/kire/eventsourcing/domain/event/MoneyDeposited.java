package me.kire.eventsourcing.domain.event;

import java.time.Instant;

import static me.kire.eventsourcing.domain.event.Event.EventType.MONEY_DEPOSITED;

public record MoneyDeposited(
        String accountId,
        Double amount,
        Instant occurredOn
) implements Event {
    public MoneyDeposited(String accountId, Double amount) {
        this(accountId, amount, Instant.now());
    }

    @Override
    public EventType type() {
        return MONEY_DEPOSITED;
    }
}
