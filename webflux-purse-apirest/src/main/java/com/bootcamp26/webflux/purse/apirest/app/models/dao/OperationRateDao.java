package com.bootcamp26.webflux.purse.apirest.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp26.webflux.purse.apirest.app.models.documents.OperationRate;

import reactor.core.publisher.Mono;

public interface OperationRateDao  extends ReactiveMongoRepository<OperationRate, String> {

    public Mono<OperationRate> findByDescription(String description);

}
