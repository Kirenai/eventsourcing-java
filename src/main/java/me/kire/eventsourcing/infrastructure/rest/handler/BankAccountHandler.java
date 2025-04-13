package me.kire.eventsourcing.infrastructure.rest.handler;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.application.service.BankAccountService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BankAccountHandler {
    private final BankAccountService bankAccountService;

    public Mono<ServerResponse> createAccount(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return this.bankAccountService.create(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> deposit(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Double amount = Double.parseDouble(serverRequest.queryParam("amount").orElse("0"));
        return this.bankAccountService.deposit(id, amount)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> withdraw(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Double amount = Double.parseDouble(serverRequest.queryParam("amount").orElse("0"));
        return this.bankAccountService.withdraw(id, amount)
                .then(ServerResponse.noContent().build());
    }
}
