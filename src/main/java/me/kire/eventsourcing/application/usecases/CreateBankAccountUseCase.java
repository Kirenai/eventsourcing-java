package me.kire.eventsourcing.application.usecases;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.application.projector.BankAccountProjector;
import me.kire.eventsourcing.domain.event.Event;
import me.kire.eventsourcing.domain.model.BankAccount;
import me.kire.eventsourcing.domain.port.in.CreateBankAccount;
import me.kire.eventsourcing.domain.port.out.EventStorePort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateBankAccountUseCase implements CreateBankAccount {
    private final EventStorePort eventStore;
    private final BankAccountProjector bankAccountProjector;

    @Override
    public Mono<Void> createAccount(String accountId) {
        BankAccount account = BankAccount.create(accountId);
        return this.persist(account);
    }

    private Mono<Void> persist(BankAccount account) {
        List<Event> events = account.getUncommittedChanges();
        return this.eventStore
                .saveEvent(account.getAccountId(), events)
                .thenMany(Flux.fromIterable(events))
                .flatMap(this.bankAccountProjector::project)
                .then(Mono.fromRunnable(account::markCommitted));
    }
}
