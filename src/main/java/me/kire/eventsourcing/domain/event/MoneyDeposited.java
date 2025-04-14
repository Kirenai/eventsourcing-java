package me.kire.eventsourcing.domain.event;

import java.time.Instant;

import static me.kire.eventsourcing.domain.event.Event.EventType.MONEY_DEPOSITED;

public record MoneyDeposited(
        String accountId,
        Double amount
) implements Event {
    @Override
    public EventType type() {
        return MONEY_DEPOSITED;
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }
}
