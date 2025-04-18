package me.kire.eventsourcing.domain.port.out;

import me.kire.eventsourcing.domain.model.BankAccountView;
import reactor.core.publisher.Mono;

public interface BankAccountViewPort {
    Mono<Void> save(BankAccountView bankAccountView);
    Mono<BankAccountView> findById(String accountId);
    Mono<Void> update(BankAccountView bankAccountView);
}
