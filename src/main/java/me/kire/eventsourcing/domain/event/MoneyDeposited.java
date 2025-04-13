package me.kire.eventsourcing.domain.event;

import java.time.Instant;

public record MoneyDeposited(
        String accountId,
        Double amount
) implements Event {
    @Override
    public String getType() {
        return "MoneyDeposited";
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }
}
