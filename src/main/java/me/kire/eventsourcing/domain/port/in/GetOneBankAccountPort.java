package me.kire.eventsourcing.domain.port.in;

import me.kire.eventsourcing.domain.model.BankAccountView;
import reactor.core.publisher.Mono;

public interface GetOneBankAccountPort {
    Mono<BankAccountView> getAccount(String accountId);
}
