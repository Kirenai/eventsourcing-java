package me.kire.eventsourcing.domain.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AccountCreated.class, name = "ACCOUNT_CREATED"),
        @JsonSubTypes.Type(value = MoneyDeposited.class, name = "MONEY_DEPOSITED"),
        @JsonSubTypes.Type(value = MoneyWithdrawn.class, name = "MONEY_WITHDRAWN")
})
public sealed interface Event permits
        AccountCreated,
        MoneyDeposited,
        MoneyWithdrawn {
    Instant occurredOn();
}
