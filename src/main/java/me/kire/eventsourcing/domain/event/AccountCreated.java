package me.kire.eventsourcing.domain.event;

import java.time.Instant;

import static me.kire.eventsourcing.domain.event.Event.EventType.ACCOUNT_CREATED;

public record AccountCreated(
        String accountId,
        Instant occurredOn
) implements Event {
    public AccountCreated(String accountId) {
        this(accountId, Instant.now());
    }

    @Override
    public EventType type() {
        return ACCOUNT_CREATED;
    }
}
