package com.bootcamp26.webflux.purse.apirest.app.models.documents;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="transactionPurses")
public class TransactionPurse {

	@Id
	@NotEmpty
	private String id;

	@NotEmpty
	private String number;
	
	@Valid
	@NotNull
	private OperationRate operationRate;
	
	@NotEmpty
	private String transactionNumber;

	@Valid
	@NotNull
	private User userRequest;

	@Valid
	@NotNull
	private PaymentMode paymentMode;

	@NotNull
	private Double amount;

	@Valid
	@NotNull
	private User userAccept;

	@NotEmpty
	private String state;

	public TransactionPurse() {
	}

	public TransactionPurse(String number, OperationRate operationRate, String transactionNumber, User userRequest, PaymentMode paymentMode, Double amount,
			User userAccept, String state) {
		this.number = number;
		this.operationRate = operationRate;
		this.transactionNumber = transactionNumber;
		this.userRequest = userRequest;
		this.paymentMode = paymentMode;
		this.amount = amount;
		this.userAccept = userAccept;
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public OperationRate getOperationRate() {
		return operationRate;
	}

	public void setOperationRate(OperationRate operationRate) {
		this.operationRate = operationRate;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public User getUserRequest() {
		return userRequest;
	}

	public void setUserRequest(User userRequest) {
		this.userRequest = userRequest;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public User getUserAccept() {
		return userAccept;
	}

	public void setUserAccept(User userAccept) {
		this.userAccept = userAccept;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
