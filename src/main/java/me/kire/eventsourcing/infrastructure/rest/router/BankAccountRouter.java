package me.kire.eventsourcing.infrastructure.rest.router;

import me.kire.eventsourcing.infrastructure.rest.handler.BankAccountHandler;
import me.kire.eventsourcing.infrastructure.rest.handler.BankAccountQueryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BankAccountRouter {
    @Bean
    public RouterFunction<ServerResponse> router(BankAccountHandler bankAccountHandler,
                                                 BankAccountQueryHandler bankAccountQueryHandler) {
        return RouterFunctions
                .route()
                .GET("/accounts/{id}", bankAccountQueryHandler::getAccount)
                .POST("/accounts/{id}", bankAccountHandler::createAccount)
                .POST("/accounts/{id}/deposit", bankAccountHandler::deposit)
                .POST("/accounts/{id}/withdraw", bankAccountHandler::withdraw)
                .build();
    }
}
