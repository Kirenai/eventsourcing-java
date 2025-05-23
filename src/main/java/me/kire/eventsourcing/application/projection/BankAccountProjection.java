package me.kire.eventsourcing.application.projection;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.domain.model.BankAccountView;
import me.kire.eventsourcing.domain.port.in.GetOneBankAccountPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BankAccountProjection {
    private final GetOneBankAccountPort getOneBankAccountPort;

    public Mono<BankAccountView> getAccount(String id) {
        return getOneBankAccountPort.getAccount(id);
    }
}
