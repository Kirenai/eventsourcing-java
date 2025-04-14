package me.kire.eventsourcing.domain.event;

import java.time.Instant;

public sealed interface Event permits
        AccountCreated,
        MoneyDeposited,
        MoneyWithdrawn {
    EventType type();

    Instant occurredOn();

    enum EventType {
        ACCOUNT_CREATED,
        MONEY_DEPOSITED,
        MONEY_WITHDRAWN
    }
}
