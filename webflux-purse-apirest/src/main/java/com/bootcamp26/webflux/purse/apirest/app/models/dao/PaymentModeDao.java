package com.bootcamp26.webflux.purse.apirest.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp26.webflux.purse.apirest.app.models.documents.PaymentMode;

import reactor.core.publisher.Mono;

public interface PaymentModeDao  extends ReactiveMongoRepository<PaymentMode, String> {

    public Mono<PaymentMode> findByDescription(String description);

}
