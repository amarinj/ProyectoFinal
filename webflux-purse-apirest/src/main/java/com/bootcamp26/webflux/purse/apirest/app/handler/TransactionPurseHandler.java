package com.bootcamp26.webflux.purse.apirest.app.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bootcamp26.webflux.purse.apirest.app.models.documents.TransactionPurse;
import com.bootcamp26.webflux.purse.apirest.app.models.services.TransactionPurseService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TransactionPurseHandler {

	@Autowired
	private TransactionPurseService service;
		
	@Autowired
	private Validator validator;
	
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll(), TransactionPurse.class);
	}
	
	public Mono<ServerResponse> ver(ServerRequest request){
		
		String id = request.pathVariable("id");
		return service.findById(id).flatMap( p -> ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(fromObject(p)))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> buscarporNumeroTransacion(ServerRequest request){
		
		String transactionNumber = request.pathVariable("transactionNumber");
		return service.findByTransactionNumber(transactionNumber).flatMap( p -> ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(fromObject(p)))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> crear(ServerRequest request){
		Mono<TransactionPurse> transactionPurse = request.bodyToMono(TransactionPurse.class);
		
		return transactionPurse.flatMap(p -> {
			
			Errors errors = new BeanPropertyBindingResult(p, TransactionPurse.class.getName());
			validator.validate(p, errors);
			
			if(errors.hasErrors()) {
				return Flux.fromIterable(errors.getFieldErrors())
						.map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
						.collectList()
						.flatMap(list -> ServerResponse.badRequest().body(fromObject(list)));
			} else {
				//if(p.getCreateAt() ==null) {
				//	p.setCreateAt(new Date());
				//}
				return service.save(p).flatMap(pdb -> ServerResponse
						.created(URI.create("/api/v2/transactionPurses/".concat(pdb.getId())))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(fromObject(pdb)));
			}
			
		});
	}
	
	public Mono<ServerResponse> editar(ServerRequest request){
		Mono<TransactionPurse> transactionPurse = request.bodyToMono(TransactionPurse.class);
		String id = request.pathVariable("id");
		
		Mono<TransactionPurse> transactionPurseDb = service.findById(id);
		
		return transactionPurseDb.zipWith(transactionPurse, (db, req) ->{			
			db.setOperationRate(req.getOperationRate());
			db.setTransactionNumber(req.getTransactionNumber());
			db.setUserRequest(req.getUserRequest());
			db.setPaymentMode(req.getPaymentMode());
			db.setAmount(req.getAmount());
			db.setUserAccept(req.getUserAccept());
			db.setState(req.getState());
			 
			return db;
		}).flatMap(p -> ServerResponse.created(URI.create("/api/v2/transactionPurses/".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.save(p), TransactionPurse.class))
		.switchIfEmpty(ServerResponse.notFound().build());
		
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest request){
		String id = request.pathVariable("id");
		
		Mono<TransactionPurse> transactionPurseDb = service.findById(id);
		
		return transactionPurseDb.flatMap(p-> service.delete(p).then(ServerResponse.noContent().build()))
				.switchIfEmpty(ServerResponse.notFound().build());
		
	}
}
