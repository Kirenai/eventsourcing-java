package me.kire.eventsourcing.domain.event;

import java.time.Instant;

public sealed interface Event permits
        AccountCreated,
        MoneyDeposited,
        MoneyWithdrawn {
    String getType();

    Instant occurredOn();
}
