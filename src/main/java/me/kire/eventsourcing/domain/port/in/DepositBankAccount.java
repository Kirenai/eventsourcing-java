package me.kire.eventsourcing.domain.port.in;

import reactor.core.publisher.Mono;

public interface DepositBankAccount {
    Mono<Void> deposit(String accountId, Double amount);
}
