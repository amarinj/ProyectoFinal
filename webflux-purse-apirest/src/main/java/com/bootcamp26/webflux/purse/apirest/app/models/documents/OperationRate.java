package com.bootcamp26.webflux.purse.apirest.app.models.documents;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "operationRates")
public class OperationRate {

	@Id
	private String id;

	@NotEmpty
	private String description;

	@NotNull
	private Double amountPurchase;

	@NotNull
	private Double amountSale;
	
	public OperationRate(){
	}
	
	public OperationRate(String description, Double amountPurchase, Double amountSale) {
		this.description = description;
		this.amountPurchase = amountPurchase;
		this.amountSale = amountSale;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmountPurchase() {
		return amountPurchase;
	}

	public void setAmountPurchase(Double amountPurchase) {
		this.amountPurchase = amountPurchase;
	}

	public Double getAmountSale() {
		return amountSale;
	}

	public void setAmountSale(Double amountSale) {
		this.amountSale = amountSale;
	}

}
