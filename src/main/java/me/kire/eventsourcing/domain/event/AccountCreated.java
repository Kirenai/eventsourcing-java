package me.kire.eventsourcing.domain.event;

import java.time.Instant;

public record AccountCreated(
        String accountId,
        Instant occurredOn
) implements Event {
    public AccountCreated(String accountId) {
        this(accountId, Instant.now());
    }
}
