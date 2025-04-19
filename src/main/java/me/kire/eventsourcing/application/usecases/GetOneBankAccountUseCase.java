package me.kire.eventsourcing.application.usecases;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.domain.model.BankAccountView;
import me.kire.eventsourcing.domain.port.in.GetOneBankAccountPort;
import me.kire.eventsourcing.domain.port.out.BankAccountViewPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetOneBankAccountUseCase implements GetOneBankAccountPort {
    private final BankAccountViewPort bankAccountViewPort;

    @Override
    public Mono<BankAccountView> getAccount(String accountId) {
        return this.bankAccountViewPort.findById(accountId);
    }
}
