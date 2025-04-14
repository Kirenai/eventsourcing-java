package me.kire.eventsourcing.domain.event;

import java.time.Instant;

import static me.kire.eventsourcing.domain.event.Event.EventType.MONEY_WITHDRAWN;

public record MoneyWithdrawn(
        String accountId,
        Double amount,
        Instant occurredOn
) implements Event {
    public MoneyWithdrawn(String accountId, Double amount) {
        this(accountId, amount, Instant.now());
    }

    @Override
    public EventType type() {
        return MONEY_WITHDRAWN;
    }
}
