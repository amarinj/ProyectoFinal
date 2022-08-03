package com.bootcamp26.webflux.purse.apirest.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp26.webflux.purse.apirest.app.models.documents.User;

import reactor.core.publisher.Mono;

public interface UserDao extends ReactiveMongoRepository<User, String> {

    public Mono<User> findByDocumentNumber(String documentNumber);
}
