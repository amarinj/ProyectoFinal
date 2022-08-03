package com.bootcamp26.webflux.purse.apirest.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp26.webflux.purse.apirest.app.models.dao.OperationRateDao;
import com.bootcamp26.webflux.purse.apirest.app.models.dao.PaymentModeDao;
import com.bootcamp26.webflux.purse.apirest.app.models.dao.TransactionPurseDao;
import com.bootcamp26.webflux.purse.apirest.app.models.dao.UserDao;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.OperationRate;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.PaymentMode;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.TransactionPurse;
import com.bootcamp26.webflux.purse.apirest.app.models.documents.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionPurseServiceImpl implements TransactionPurseService {

    @Autowired
    private TransactionPurseDao dao;

    @Autowired
    private OperationRateDao operationRateDao;

    @Autowired
    private PaymentModeDao paymentModeDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Flux<TransactionPurse> findAll() {
        return dao.findAll();
    }

    @Override
    public Mono<TransactionPurse> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Mono<TransactionPurse> save(TransactionPurse transactionPurse) {
        return dao.save(transactionPurse);
    }

    @Override
    public Mono<Void> delete(TransactionPurse transactionPurse) {
        return dao.delete(transactionPurse);
    }

    @Override
    public Mono<TransactionPurse> findByNumber(String number) {
        return dao.findByNumber(number);
    }
    
    @Override
    public Mono<TransactionPurse> findByTransactionNumber(String transactionNumber) {
        return dao.findByTransactionNumber(transactionNumber);
    }
   
    

    @Override
    public Flux<User> findAllUser() {
        return userDao.findAll();
    }

    @Override
    public Mono<User> findUserById(String id) {
        return userDao.findById(id);
    }

    @Override
    public Mono<User> saveUser(User user) {
        return userDao.save(user);
    }

    @Override
    public Mono<User> findUserByDocumentNumber(String documentNumber) {
        return userDao.findByDocumentNumber(documentNumber);
    }


    
    @Override
    public Flux<OperationRate> findAllOperationRate() {
        return operationRateDao.findAll();
    }

    @Override
    public Mono<OperationRate> findOperationRateById(String id) {
        return operationRateDao.findById(id);
    }

    @Override
    public Mono<OperationRate> saveOperationRate(OperationRate operationRate) {
        return operationRateDao.save(operationRate);
    }

    @Override
    public Mono<OperationRate> findOperationRateByDescription(String description) {
        return operationRateDao.findByDescription(description);
    }

    
    @Override
    public Flux<PaymentMode> findAllPaymentMode() {
        return paymentModeDao.findAll();
    }

    @Override
    public Mono<PaymentMode> findPaymentModeById(String id) {
        return paymentModeDao.findById(id);
    }

    @Override
    public Mono<PaymentMode> savePaymentMode(PaymentMode paymentMode) {
        return paymentModeDao.save(paymentMode);
    }

    @Override
    public Mono<PaymentMode> findPaymentModeByDescription(String description) {
        return paymentModeDao.findByDescription(description);
    }
    
}
