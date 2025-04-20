package me.kire.eventsourcing.infrastructure.rest.handler;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.application.projection.BankAccountProjection;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BankAccountQueryHandler {
    private final BankAccountProjection bankAccountProjection;

    public Mono<ServerResponse> getAccount(ServerRequest request) {
        String accountId = request.pathVariable("id");
        return this.bankAccountProjection.getAccount(accountId)
                .flatMap(bankAccountView -> ServerResponse.ok().bodyValue(bankAccountView));
    }
}
