package me.kire.eventsourcing.domain.port.in;

import reactor.core.publisher.Mono;

public interface WithdrawBankAccount {
    Mono<Void> withdraw(String accountId, Double amount);
}
