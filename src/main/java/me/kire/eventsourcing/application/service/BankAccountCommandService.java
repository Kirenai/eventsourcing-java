package me.kire.eventsourcing.application.service;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.domain.port.in.CreateBankAccount;
import me.kire.eventsourcing.domain.port.in.DepositBankAccount;
import me.kire.eventsourcing.domain.port.in.WithdrawBankAccount;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BankAccountCommandService {
    private final DepositBankAccount depositBankAccount;
    private final CreateBankAccount createBankAccount;
    private final WithdrawBankAccount withdrawBankAccount;

    public Mono<Void> create(String accountId) {
        return this.createBankAccount.createAccount(accountId);

    }

    public Mono<Void> deposit(String accountId, Double amount) {
        return this.depositBankAccount.deposit(accountId, amount);
    }

    public Mono<Void> withdraw(String accountId, Double amount) {
        return this.withdrawBankAccount.withdraw(accountId, amount);
    }
}
