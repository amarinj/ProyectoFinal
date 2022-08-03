package com.bootcamp26.webflux.purse.apirest.app.models.services;

import com.bootcamp26.webflux.purse.apirest.app.models.documents.OperationRate;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.PaymentMode;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.TransactionPurse;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionPurseService {

	public Flux<TransactionPurse> findAll();

	public Mono<TransactionPurse> findById(String id);

	public Mono<TransactionPurse> save(TransactionPurse transactionPurse);

	public Mono<Void> delete(TransactionPurse person);

	public Mono<TransactionPurse> findByNumber(String number);
	
	public Mono<TransactionPurse> findByTransactionNumber(String transactionNumber);

	
	
    public Flux<User> findAllUser();

    public Mono<User> findUserById(String id);

    public Mono<User> saveUser(User user);

    public Mono<User> findUserByDocumentNumber(String documentNumber);

    
    public Flux<OperationRate> findAllOperationRate();

    public Mono<OperationRate> findOperationRateById(String id);

    public Mono<OperationRate> saveOperationRate(OperationRate operationRate);

    public Mono<OperationRate> findOperationRateByDescription(String description);
    
    
    
    public Flux<PaymentMode> findAllPaymentMode();

    public Mono<PaymentMode> findPaymentModeById(String id);

    public Mono<PaymentMode> savePaymentMode(PaymentMode paymentMode);

    public Mono<PaymentMode> findPaymentModeByDescription(String description);
}
