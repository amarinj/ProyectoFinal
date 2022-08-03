package com.bootcamp26.webflux.purse.apirest.app;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bootcamp26.webflux.purse.apirest.app.handler.TransactionPurseHandler;


@Configuration
public class RouterFunctionConfig {

	@Bean
	public RouterFunction<ServerResponse> routes(TransactionPurseHandler handler){
		
		return route(GET("/api/v2/transactionPurses").or(GET("/api/purse/transactionPurses")), handler::listar)
				.andRoute(GET("/api/v2/transactionPurses/{id}").or(GET("/api/purse/transactionPurses/{id}")), handler::ver)
				.andRoute(GET("/api/v2/transactionPurses/doc/{transactionNumber}").or(GET("/api/purse/transactionPurses/doc/{transactionNumber}")), handler::buscarporNumeroTransacion)
				.andRoute(POST("/api/v2/transactionPurses").or(POST("/api/purse/transactionPurses")), handler::crear)
				.andRoute(PUT("/api/v2/transactionPurses/{id}").or(PUT("/api/purse/transactionPurses/{id}")), handler::editar)
				.andRoute(DELETE("/api/v2/transactionPurses/{id}").or(DELETE("/api/purse/transactionPurses/{id}")), handler::eliminar);
	}

	
}
