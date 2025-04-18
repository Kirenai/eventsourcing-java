package me.kire.eventsourcing.application.projector;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.domain.event.AccountCreated;
import me.kire.eventsourcing.domain.event.Event;
import me.kire.eventsourcing.domain.event.MoneyDeposited;
import me.kire.eventsourcing.domain.event.MoneyWithdrawn;
import me.kire.eventsourcing.domain.model.BankAccountView;
import me.kire.eventsourcing.domain.port.out.BankAccountViewPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BankAccountProjector {
    public static final double INIT_BALANCE = 0.0;

    private final BankAccountViewPort bankAccountView;

    public Mono<Void> project(Event event) {
        switch (event) {
            case AccountCreated accountCreated -> {
                BankAccountView bankAccountView = BankAccountView.builder()
                        .accountId(accountCreated.accountId())
                        .balance(INIT_BALANCE)
                        .build();
                return this.bankAccountView.save(bankAccountView);
            }
            case MoneyDeposited moneyDeposited -> {
                return this.bankAccountView.findById(moneyDeposited.accountId())
                        .flatMap(view -> {
                            view.setBalance(view.getBalance() + moneyDeposited.amount());
                            return this.bankAccountView.update(view);
                        });
            }
            case MoneyWithdrawn moneyWithdrawn -> {
                return this.bankAccountView.findById(moneyWithdrawn.accountId())
                        .flatMap(view -> {
                            view.setBalance(view.getBalance() - moneyWithdrawn.amount());
                            return this.bankAccountView.update(view);
                        });
            }
        }
    }
}
