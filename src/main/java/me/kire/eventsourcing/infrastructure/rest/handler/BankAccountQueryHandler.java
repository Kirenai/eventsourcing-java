package me.kire.eventsourcing.infrastructure.rest.handler;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.application.query.BankAccountQueryService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BankAccountQueryHandler {
    private final BankAccountQueryService bankAccountQueryService;

    public Mono<ServerResponse> getAccount(ServerRequest request) {
        String accountId = request.pathVariable("id");
        return this.bankAccountQueryService.getAccount(accountId)
                .flatMap(bankAccountView -> ServerResponse.ok().bodyValue(bankAccountView));
    }
}
