package com.bootcamp26.webflux.apirest.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class WebfluxEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxEurekaServerApplication.class, args);
	}

}
