package com.bootcamp26.webflux.client.app.models.services;

import com.bootcamp26.webflux.client.app.models.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {

	public Flux<Person> findAll();
	
	public Mono<Person> findById(String id);
	
    public Mono<Person> save(Person person);
    
    public Mono<Person> update(Person person, String id);
    
    public Mono<Void> delete(String id);
    
    
}
