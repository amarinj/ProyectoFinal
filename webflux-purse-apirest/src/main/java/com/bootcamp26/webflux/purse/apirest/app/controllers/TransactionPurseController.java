package com.bootcamp26.webflux.purse.apirest.app.controllers;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.bootcamp26.webflux.purse.apirest.app.models.documents.TransactionPurse;
import com.bootcamp26.webflux.purse.apirest.app.models.services.TransactionPurseService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transactionPurses")
public class TransactionPurseController {

	 @Autowired
	    private TransactionPurseService service;

	    @GetMapping
	    public Mono<ResponseEntity<Flux<TransactionPurse>>> lista(){
	        return Mono.just(
	                ResponseEntity.ok()
	                        .contentType(MediaType.APPLICATION_JSON_UTF8)
	                        .body(service.findAll())
	        );
	    }

	    @GetMapping("/{id}")
	    public Mono<ResponseEntity<TransactionPurse>> ver(@PathVariable String id){
	        return service.findById(id).map(p -> ResponseEntity.ok()
	                        .contentType(MediaType.APPLICATION_JSON_UTF8)
	                        .body(p))
	                .defaultIfEmpty(ResponseEntity.notFound().build());
	    }
	    
	    @GetMapping("/doc/{transactionNumber}")
	    public Mono<ResponseEntity<TransactionPurse>> buscarporNumeroDocumento(@PathVariable String transactionNumber){
	        return service.findByTransactionNumber(transactionNumber).map(p -> ResponseEntity.ok()
	                        .contentType(MediaType.APPLICATION_JSON_UTF8)
	                        .body(p))
	                .defaultIfEmpty(ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    public Mono<ResponseEntity<Map<String, Object>>> crear(@Valid @RequestBody Mono<TransactionPurse> monoTransactionPurse){

	        Map<String, Object> respuesta = new HashMap<String, Object>();

	        return monoTransactionPurse.flatMap(transactionPurse -> {
	            /*if(person.getCreateAt()==null) {
	                person.setCreateAt(new Date());
	            }*/

	            return service.save(transactionPurse).map(p-> {
	                respuesta.put("Transaccion", p);
	                respuesta.put("message", "Transaccion creado con éxito");
	                respuesta.put("timestamp", new Date());
	                return ResponseEntity
	                        .created(URI.create("/api/transactionPurses/".concat(p.getId())))
	                        .contentType(MediaType.APPLICATION_JSON_UTF8)
	                        .body(respuesta);
	            });

	        }).onErrorResume(t -> {
	            return Mono.just(t).cast(WebExchangeBindException.class)
	                    .flatMap(e -> Mono.just(e.getFieldErrors()))
	                    .flatMapMany(Flux::fromIterable)
	                    .map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
	                    .collectList()
	                    .flatMap(list -> {
	                        respuesta.put("errors", list);
	                        respuesta.put("timestamp", new Date());
	                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
	                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
	                    });

	        });
	    }

	    @PutMapping("/{id}")
	    public Mono<ResponseEntity<TransactionPurse>> editar(@RequestBody TransactionPurse transactionPurse, @PathVariable String id){
	        return service.findById(id).flatMap(p -> {
	        			p.setOperationRate(transactionPurse.getOperationRate());        
	        			p.setTransactionNumber(transactionPurse.getTransactionNumber());
	                    p.setUserRequest(transactionPurse.getUserRequest());
	                    p.setPaymentMode(transactionPurse.getPaymentMode());
	                    p.setAmount(transactionPurse.getAmount());
	                    p.setUserAccept(transactionPurse.getUserAccept());
	                    p.setState(transactionPurse.getState());

	                    return service.save(p);
	                }).map(p->ResponseEntity.created(URI.create("/api/persons/".concat(p.getId())))
	                        .contentType(MediaType.APPLICATION_JSON_UTF8)
	                        .body(p))
	                .defaultIfEmpty(ResponseEntity.notFound().build());
	    }

	    @DeleteMapping("/{id}")
	    public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id){
	        return service.findById(id).flatMap(p ->{
	            return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
	        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	    }

	}