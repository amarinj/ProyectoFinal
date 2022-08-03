package com.bootcamp26.webflux.purse.apirest.app;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.bootcamp26.webflux.purse.apirest.app.models.documents.OperationRate;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.PaymentMode;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.TransactionPurse;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.User;
import com.bootcamp26.webflux.purse.apirest.app.models.services.TransactionPurseService;

import reactor.core.publisher.Flux;

@EnableEurekaClient
@SpringBootApplication
public class WebfluxPurseApirestApplication implements CommandLineRunner {

	@Autowired
	private TransactionPurseService service;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	private static final Logger log = LoggerFactory.getLogger(WebfluxPurseApirestApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(WebfluxPurseApirestApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("operationRates").subscribe();
		mongoTemplate.dropCollection("paymentModes").subscribe();
		mongoTemplate.dropCollection("users").subscribe();		
		mongoTemplate.dropCollection("transactionPurses").subscribe();
		
		OperationRate operationRate1 = new OperationRate("Compra", 4.50, 10.00);
		OperationRate operationRate2 = new OperationRate("Venta", 4.50, 10.00);
		
		Flux.just(operationRate1, operationRate2)
		.flatMap(service::saveOperationRate)
		.doOnNext(a -> { log.info("Tasa de Operacón creada: " + a.getDescription() + ", Id: " + a.getId()); })
		.subscribe(reg -> log.info("Insert: " + reg.getId() + " " + reg.getDescription()));

				
		PaymentMode PaymentMode1 = new PaymentMode("Yanki");
		PaymentMode PaymentMode2 = new PaymentMode("Transferencia");

		Flux.just(PaymentMode1, PaymentMode2)
		.flatMap(service::savePaymentMode)
		.doOnNext(a -> { log.info("Modo de Pago creada: " + a.getDescription() + ", Id: " + a.getId()); })
		.subscribe(reg -> log.info("Insert: " + reg.getId() + " " + reg.getDescription()));

		
		
		OperationRate getoperationRate1 = service.findOperationRateByDescription("Compra").block();
		OperationRate getoperationRate2 = service.findOperationRateByDescription("Venta").block();
		
		PaymentMode getPaymentMode1 = service.findPaymentModeByDescription("Yanki").block();
		PaymentMode getPaymentMode2 = service.findPaymentModeByDescription("Transferencia").block();
		
		User user1 = new User("Dni", "10000001","910000001","solicita1@gmail.com");
		User user2 = new User("Dni", "10000002","910000002","solicita2@gmail.com");
		User user3 = new User("Dni", "10000003","910000003","solicita3@gmail.com");
		User user4 = new User("Dni", "10000004","910000004","solicita4@gmail.com");
		User user5 = new User("Dni", "10000005","910000005","solicita5@gmail.com");
		User user6 = new User("Dni", "10000006","910000006","solicita6@gmail.com");
		User user7 = new User("Dni", "10000007","910000007","solicita7@gmail.com");
		User user8 = new User("Dni", "10000008","910000008","solicita8@gmail.com");
		User user9 = new User("Dni", "10000009","910000009","solicita9@gmail.com");
		User user10 = new User("Dni", "10000010","910000010","solicita10@gmail.com");
		
		User user20 = new User("Dni", "40000020","940000020","acepta20@gmail.com");
		User user21 = new User("Dni", "40000021","940000021","acepta21@gmail.com");
		User user22 = new User("Dni", "40000022","940000022","acepta22@gmail.com");
		User user23 = new User("Dni", "40000023","940000023","acepta23@gmail.com");
		
		
		Flux.just(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user20, user21, user22, user23)
		.flatMap(service::saveUser)
		.doOnNext(c ->{
			log.info("Usuario creada: " + c.getDocumentNumber() + ", Id: " + c.getId());
		}).thenMany(
				Flux.just(new TransactionPurse( "10040001", getoperationRate1, getRandom(), user4, getPaymentMode1, 100.00, user20, "ACEPTADO" ),
							new	TransactionPurse( "10060001", getoperationRate1, "0", user6, getPaymentMode1, 80.00, null, "SOLICITADO" ),
							new	TransactionPurse( "10080001", getoperationRate2, "0", user8, getPaymentMode2, 20.00, null, "SOLICITADO" ),
							new	TransactionPurse( "10020001", getoperationRate2, getRandom(), user2, getPaymentMode2, 50.00, user20, "ACEPTADO" )
						)
						.flatMap(transactionPurse -> {
							return service.save(transactionPurse);
						})
		)
		.subscribe(transactionPurse -> log.info("Insert: " + transactionPurse.getId() + " " + transactionPurse.getTransactionNumber()));		
	}
	
	public String getRandom() {
	
	    int length = 10;
	    boolean useLetters = false;
	    boolean useNumbers = true;
	    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
	    
	    return generatedString;
	}
}