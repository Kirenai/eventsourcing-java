package me.kire.eventsourcing.application.usecases;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.domain.model.BankAccount;
import me.kire.eventsourcing.domain.port.in.CreateBankAccount;
import me.kire.eventsourcing.domain.port.out.EventStorePort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreateBankAccountUseCase implements CreateBankAccount {
    private final EventStorePort eventStore;

    @Override
    public Mono<Void> createAccount(String accountId) {
        BankAccount account = BankAccount.create(accountId);
        return this.persist(account);
    }

    private Mono<Void> persist(BankAccount account) {
        return this.eventStore
                .saveEvent(account.getAccountId(), account.getUncommittedChanges())
                .doOnSuccess(ignored -> account.markCommitted());
    }
}
