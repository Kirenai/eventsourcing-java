package me.kire.eventsourcing.domain.event;

import java.time.Instant;

public record MoneyDeposited(
        String accountId,
        Double amount
) implements Event {
    @Override
    public String type() {
        return "MoneyDeposited";
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }
}
