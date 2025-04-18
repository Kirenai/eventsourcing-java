package me.kire.eventsourcing.infrastructure.adapter.repository;

import lombok.RequiredArgsConstructor;
import me.kire.eventsourcing.domain.model.BankAccountView;
import me.kire.eventsourcing.domain.port.out.BankAccountViewPort;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class BankAccountViewAdapter implements BankAccountViewPort {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Void> save(BankAccountView bankAccountView) {
        Document document = new Document();
        document.put("accountId", bankAccountView.getAccountId());
        document.put("balance", bankAccountView.getBalance());
        return this.mongoTemplate.insert(document, "bank_account_view").then();
    }

    @Override
    public Mono<BankAccountView> findById(String accountId) {
        Query query = new Query(Criteria.where("accountId").is(accountId));
        return this.mongoTemplate.findOne(query, Document.class, "bank_account_view")
                .map(document ->
                        new BankAccountView(document.getString("accountId"),
                                document.getDouble("balance"))
                );
    }

    @Override
    public Mono<Void> update(BankAccountView bankAccountView) {
        Query query = new Query(Criteria.where("accountId").is(bankAccountView.getAccountId()));
        Update update = new Update();
        update.set("balance", bankAccountView.getBalance());
        return this.mongoTemplate.updateFirst(query, update, "bank_account_view").then();
    }
}
