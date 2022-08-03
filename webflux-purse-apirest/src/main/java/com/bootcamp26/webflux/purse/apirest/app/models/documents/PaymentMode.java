package com.bootcamp26.webflux.purse.apirest.app.models.documents;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="paymentModes")
public class PaymentMode {


	 @Id
	    @NotEmpty
	    private String id;

	    private String description;

	    public PaymentMode() {
	    }

	    public PaymentMode(String description) {
	        this.description = description;
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
	
}
