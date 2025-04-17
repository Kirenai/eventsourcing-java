package me.kire.eventsourcing.infrastructure.adapter.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.domain.event.Event;
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
    private final ObjectMapper mapper;

    @Override
    public Mono<Void> saveEvent(String aggregateId, List<Event> events) {
        return Flux.fromStream(events.stream()
                        .map(event -> {
                            Document map = this.mapper.convertValue(event, Document.class);
                            map.put("aggregateId", aggregateId);
                            map.put("occurredOn", event.occurredOn().toString());
                            return new Document(map);
                        }))
                .flatMap(document -> this.mongoTemplate.insert(document, "events"))
                .then();
    }

    @Override
    public Flux<Event> getEvents(String aggregateId) {
        Query query = new Query(Criteria.where("aggregateId").is(aggregateId));
        return this.mongoTemplate.find(query, Document.class, "events")
                .map(doc -> this.mapper.convertValue(doc, Event.class));
    }
}
