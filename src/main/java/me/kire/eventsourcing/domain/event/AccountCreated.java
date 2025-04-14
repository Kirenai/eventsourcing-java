package me.kire.eventsourcing.domain.event;

import java.time.Instant;

public record AccountCreated(
        String accountId
) implements Event {

    @Override
    public String type() {
        return "AccountCreated";
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }
}
