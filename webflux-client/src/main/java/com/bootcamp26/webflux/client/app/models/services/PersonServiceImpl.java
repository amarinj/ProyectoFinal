package com.bootcamp26.webflux.client.app.models.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.MediaType.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp26.webflux.client.app.models.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private WebClient client;
	//private WebClient.Builder client;
	
	@Override
	public Flux<Person> findAll() {
		return client.get().accept(APPLICATION_JSON_UTF8)
				.exchange()
				.flatMapMany(response -> response.bodyToFlux(Person.class));
	}

	@Override
	public Mono<Person> findById(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return client.get().uri("/{id}", params)
				.accept(APPLICATION_JSON_UTF8)
				.retrieve()
				.bodyToMono(Person.class);
				//.exchange()
				//.flatMap(response -> response.bodyToMono(Person.class));
	}

	@Override
	public Mono<Person> save(Person person) {
		return client.post()
				.accept(APPLICATION_JSON_UTF8)
				.contentType(APPLICATION_JSON_UTF8)
				//.body(fromObject(person))
				.syncBody(person)
				.retrieve()
				.bodyToMono(Person.class);
	}

	@Override
	public Mono<Person> update(Person person, String id) {
		
		return client.put()
				.uri("/{id}", Collections.singletonMap("id", id))
				.accept(APPLICATION_JSON_UTF8)
				.contentType(APPLICATION_JSON_UTF8)
				.syncBody(person)
				.retrieve()
				.bodyToMono(Person.class);
	}

	@Override
	public Mono<Void> delete(String id) {
		return client.delete().uri("/{id}", Collections.singletonMap("id", id))
				.retrieve()
				.bodyToMono(Void.class);
	}

	
}
