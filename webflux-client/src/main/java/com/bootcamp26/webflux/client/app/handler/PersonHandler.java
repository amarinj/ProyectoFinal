package com.bootcamp26.webflux.client.app.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.springframework.http.MediaType.*;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bootcamp26.webflux.client.app.models.Person;
import com.bootcamp26.webflux.client.app.models.services.PersonService;

import reactor.core.publisher.Mono;


@Component
public class PersonHandler {

	
	@Autowired
	private PersonService service;

	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8)
				.body(service.findAll(), Person.class);
	}
		
	public Mono<ServerResponse> crear(ServerRequest request){
		Mono<Person> person = request.bodyToMono(Person.class);
		
		return person.flatMap(p-> {
			//if(p.getCreateAt()==null) {
			//	p.setCreateAt(new Date());
			//}
			return service.save(p);
			}).flatMap(p -> ServerResponse.created(URI.create("/api/client/".concat(p.getId())))
					.contentType(APPLICATION_JSON_UTF8)
					.bodyValue(p))
				.onErrorResume(error -> {
					WebClientResponseException errorResponse = (WebClientResponseException) error;
					if(errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
						return ServerResponse.badRequest()
								.contentType(APPLICATION_JSON_UTF8)
								.bodyValue(errorResponse.getResponseBodyAsString());
					}
					return Mono.error(errorResponse);
				});
	}
	
	public Mono<ServerResponse> editar(ServerRequest request){
		Mono<Person> person = request.bodyToMono(Person.class);
		String id = request.pathVariable("id");
		
		return errorHandler(
				person
				.flatMap(p -> service.update(p, id))
				.flatMap(p-> ServerResponse.created(URI.create("/api/client/".concat(p.getId())))
				.contentType(APPLICATION_JSON_UTF8)
				.bodyValue(p))
				);
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest request){
		String id = request.pathVariable("id");
		return errorHandler(
				service.delete(id).then(ServerResponse.noContent().build())
				);
	}
		
	private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response){
		return response.onErrorResume(error -> {
			WebClientResponseException errorResponse = (WebClientResponseException) error;
			if(errorResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
				Map<String, Object> body = new HashMap<>();
				body.put("error", "No existe la Persona: ".concat(errorResponse.getMessage()));
				body.put("timestamp", new Date());
				body.put("status", errorResponse.getStatusCode().value());
				return ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(body);
			}
			return Mono.error(errorResponse);
		});
	}
}
