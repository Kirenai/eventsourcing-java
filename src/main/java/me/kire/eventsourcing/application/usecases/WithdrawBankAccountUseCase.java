package me.kire.eventsourcing.application.usecases;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.application.projector.BankAccountProjector;
import me.kire.eventsourcing.domain.event.Event;
import me.kire.eventsourcing.domain.model.BankAccount;
import me.kire.eventsourcing.domain.port.in.WithdrawBankAccount;
import me.kire.eventsourcing.domain.port.out.EventStorePort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WithdrawBankAccountUseCase implements WithdrawBankAccount {
    private final EventStorePort eventStore;
    private final BankAccountProjector bankAccountProjector;

    @Override
    public Mono<Void> withdraw(String accountId, Double amount) {
        return this.rehydrate(accountId)
                .flatMap(account -> this.persist(amount, account))
                .then();
    }

    private Mono<BankAccount> rehydrate(String accountId) {
        BankAccount account = new BankAccount();
        return this.eventStore.getEvents(accountId)
                .doOnNext(account::onEvent)
                .then(Mono.just(account));
    }

    private Mono<BankAccount> persist(Double amount, BankAccount account) {
        account.withdraw(amount);
        List<Event> events = account.getUncommittedChanges();
        return this.eventStore
                .saveEvent(account.getAccountId(), events)
                .thenMany(Flux.fromIterable(events))
                .flatMap(this.bankAccountProjector::project)
                .then(Mono.fromRunnable(account::markCommitted));
    }
}
