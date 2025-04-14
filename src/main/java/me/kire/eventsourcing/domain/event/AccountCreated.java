package me.kire.eventsourcing.domain.event;

import java.time.Instant;

import static me.kire.eventsourcing.domain.event.Event.EventType.ACCOUNT_CREATED;

public record AccountCreated(
        String accountId
) implements Event {

    @Override
    public EventType type() {
        return ACCOUNT_CREATED;
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }
}
