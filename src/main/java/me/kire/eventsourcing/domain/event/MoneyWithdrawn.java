package me.kire.eventsourcing.domain.event;

import java.time.Instant;

public record MoneyWithdrawn(
        String accountId,
        Double amount
) implements Event {
    @Override
    public String type() {
        return "MoneyWithdrawn";
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }
}
