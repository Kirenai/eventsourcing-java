package me.kire.eventsourcing.domain.port.in;

import reactor.core.publisher.Mono;

public interface CreateBankAccount {
    Mono<Void> createAccount(String accountId);
}
