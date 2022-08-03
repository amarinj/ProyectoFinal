package com.bootcamp26.webflux.purse.apirest.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp26.webflux.purse.apirest.app.models.documents.TransactionPurse;

import reactor.core.publisher.Mono;

public interface TransactionPurseDao extends ReactiveMongoRepository<TransactionPurse, String> {

    public Mono<TransactionPurse> findByTransactionNumber(String transactionNumber);
    
    public Mono<TransactionPurse> findByNumber(String number);

}
