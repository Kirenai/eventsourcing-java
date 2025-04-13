package me.kire.eventsourcing.application.usecases;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.domain.model.BankAccount;
import me.kire.eventsourcing.domain.port.in.DepositBankAccount;
import me.kire.eventsourcing.domain.port.out.EventStorePort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DepositBankAccountUseCase implements DepositBankAccount {
    private final EventStorePort eventStore;

    @Override
    public Mono<Void> deposit(String accountId, Double amount) {
        return this.rehydrate(accountId)
                .flatMap(account -> this.persist(amount, account))
                .doOnSuccess(BankAccount::markCommitted)
                .then();
    }

    private Mono<BankAccount> rehydrate(String accountId) {
        BankAccount account = new BankAccount();
        return this.eventStore.getEvents(accountId)
                .doOnNext(account::onEvent)
                .then(Mono.just(account));
    }

    private Mono<BankAccount> persist(Double amount, BankAccount account) {
        account.deposit(amount);
        return this.eventStore.saveEvent(account.getAccountId(), account.getUncommittedChanges())
                .thenReturn(account);
    }
}
