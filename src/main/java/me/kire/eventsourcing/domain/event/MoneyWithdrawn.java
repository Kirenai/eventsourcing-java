package me.kire.eventsourcing.domain.event;

import java.time.Instant;

import static me.kire.eventsourcing.domain.event.Event.EventType.MONEY_WITHDRAWN;

public record MoneyWithdrawn(
        String accountId,
        Double amount
) implements Event {
    @Override
    public EventType type() {
        return MONEY_WITHDRAWN;
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }
}
