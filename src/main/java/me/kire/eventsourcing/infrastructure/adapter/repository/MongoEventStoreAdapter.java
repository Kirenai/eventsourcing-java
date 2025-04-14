package me.kire.eventsourcing.infrastructure.adapter.repository;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.domain.event.AccountCreated;
import me.kire.eventsourcing.domain.event.Event;
import me.kire.eventsourcing.domain.event.Event.EventType;
import me.kire.eventsourcing.domain.event.MoneyDeposited;
import me.kire.eventsourcing.domain.event.MoneyWithdrawn;
import me.kire.eventsourcing.domain.port.out.EventStorePort;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MongoEventStoreAdapter implements EventStorePort {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Void> saveEvent(String aggregateId, List<Event> events) {
        return Flux.fromStream(events.stream()
                        .map(event -> {
                            Document document = new Document();
                            document.put("aggregateId", aggregateId);
                            document.put("type", event.type().name());
                            document.put("occurredOn", event.occurredOn().toString());
                            document.put("payload", event);
                            return document;
                        }))
                .flatMap(document -> this.mongoTemplate.insert(document, "events"))
                .then();
    }

    @Override
    public Flux<Event> getEvents(String aggregateId) {
        Query query = new Query(Criteria.where("aggregateId").is(aggregateId));
        return this.mongoTemplate.find(query, Document.class, "events")
                .map(this::deserialize);
    }

    private Event deserialize(Document document) {
        EventType type = EventType.valueOf(document.getString("type"));
        Document payload = document.get("payload", Document.class);
        return switch (type) {
            case ACCOUNT_CREATED -> new AccountCreated(payload.getString("accountId"));
            case MONEY_DEPOSITED -> new MoneyDeposited(payload.getString("accountId"), payload.getDouble("amount"));
            case MONEY_WITHDRAWN -> new MoneyWithdrawn(payload.getString("accountId"), payload.getDouble("amount"));
        };
    }
}
