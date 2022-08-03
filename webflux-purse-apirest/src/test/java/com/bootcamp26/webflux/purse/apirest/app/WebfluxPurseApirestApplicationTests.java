package com.bootcamp26.webflux.purse.apirest.app;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bootcamp26.webflux.purse.apirest.app.models.documents.OperationRate;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.PaymentMode;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.TransactionPurse;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.User;
import com.bootcamp26.webflux.purse.apirest.app.models.services.TransactionPurseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;


@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class WebfluxPurseApirestApplicationTests {

	@Autowired
	private WebTestClient client;
	
	@Autowired
	private TransactionPurseService service;

	@Value("${config.base.endpoint}")
	private String url;


	@Test
	public void listarTest() {
		
		client.get()
		.uri("/api/v2/transactionPurses")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBodyList(TransactionPurse.class)
		.consumeWith(response -> {
			List<TransactionPurse> transactionPurses = response.getResponseBody();
			transactionPurses.forEach(p -> {
				System.out.println(p.getNumber());
			});
			
			//Assertions.assertThat(persons.size()>0).isTrue();
		})
				.isEqualTo(null);
		//.hasSize(9);
	}
		

	@Test
	public void crearSolicitudCompra2Test() {
		
		OperationRate getoperationRate1 = service.findOperationRateByDescription("Compra").block();
		PaymentMode getPaymentMode1 = service.findPaymentModeByDescription("Yanki").block();
		
		User user1 = service.findUserByDocumentNumber("10000001").block();
		
		//User user1 = new User("Dni", "10000001","910000001","solicita1@gmail.com");
		
		TransactionPurse transactionPurse = new	TransactionPurse( "10010001", getoperationRate1, "0", user1, getPaymentMode1, 120.00, null, "SOLICITADO" );
		
	
		client.post().uri("/api/v2/transactionPurses")
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.body(Mono.just(transactionPurse), TransactionPurse.class)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBody()
		.jsonPath("$.transactionPurse.id").isNotEmpty()
		.jsonPath("$.transactionPurse.number").isEqualTo("10010001")
		.jsonPath("$.transactionPurse.state").isEqualTo("SOLICITADO")
		.jsonPath("$.transactionPurse.paymentMode.description").isEqualTo("Yanki")
		.jsonPath("$.transactionPurse.operationRate.description").isEqualTo("Compra");
	}

	@Test
	public void crearSolicitudCompraTest() {
		
		OperationRate getoperationRate2 = service.findOperationRateByDescription("Compra").block();		
		//User user1 = new User("Dni", "10000001","910000001","solicita1@gmail.com");	
		User user1 = service.findUserByDocumentNumber("10000001").block();
		PaymentMode getPaymentMode1 = service.findPaymentModeByDescription("Yanki").block();
		
		TransactionPurse transactionPurse = new	TransactionPurse( "10010002", getoperationRate2, "0", user1, getPaymentMode1, 120.00, null, "SOLICITADO" );
		
		
		client.post().uri("/api/v2/transactionPurses")
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.body(Mono.just(transactionPurse), TransactionPurse.class)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBody(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
		.consumeWith(response -> {
			Object o = response.getResponseBody().get("transactionPurse");
			TransactionPurse p = new ObjectMapper().convertValue(o, TransactionPurse.class);
			
			//System.out.println(p.getId() + " - " + p.getDocumentNumber() + " - " + p.getName());
			
			//Assertions.assertThat(p.getId()).isNotEmpty();
			//Assertions.assertThat(p.getNombre()).isEqualTo("Mesa comedor");
			//Assertions.assertThat(p.getCategoria().getNombre()).isEqualTo("Muebles");
		});
	}
	
	@Test
	public void editarTest() {
		
		 int length = 10;
	    boolean useLetters = false;
	    boolean useNumbers = true;
	    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
	    
		TransactionPurse transactionPurse = service.findByNumber("10080001").block();		
		User userAccept = service.findUserByDocumentNumber("40000020").block();
		
		User user8 = service.findUserByDocumentNumber("10000008").block();
		OperationRate getoperationRate2 = service.findOperationRateByDescription("Venta").block();
		PaymentMode getPaymentMode2 = service.findPaymentModeByDescription("Transferencia").block();
		
		
		TransactionPurse transactionPurseEdit = new	TransactionPurse( "10080001", getoperationRate2, generatedString, user8, getPaymentMode2, 20.00, userAccept, "ACEPTADO" );
		
		client.put().uri("/api/v2/transactionPurses" + "/{id}", Collections.singletonMap("id", transactionPurse.getId()))
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.body(Mono.just(transactionPurseEdit), TransactionPurse.class)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBody()		
		.jsonPath("$.id").isNotEmpty()
		.jsonPath("$.number").isEqualTo("10080001")
		.jsonPath("$.operationRate.description").isEqualTo("Venta")
		.jsonPath("$.paymentMode.description").isEqualTo("Transferencia");
			
	}

	@Test
	public void eliminarTest() {
		TransactionPurse transactionPurse = service.findByNumber("10040001").block();
		client.delete()
		.uri("/api/v2/transactionPurses" + "/{id}", Collections.singletonMap("id", transactionPurse.getId()))
		.exchange()
		.expectStatus().isNoContent()
		.expectBody()
		.isEmpty();
		
		/*
		client.get()
		.uri(url + "/{id}", Collections.singletonMap("id", transactionPurse.getId()))
		.exchange()
		.expectStatus().isNotFound()
		.expectBody()
		.isEmpty();
		*/
	}
}
