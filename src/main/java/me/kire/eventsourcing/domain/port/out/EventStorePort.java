package me.kire.eventsourcing.domain.port.out;

import me.kire.eventsourcing.domain.event.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EventStorePort {
    Mono<Void> saveEvent(String aggregateId, List<Event> events);
    Flux<Event> getEvents(String aggregateId);
}
