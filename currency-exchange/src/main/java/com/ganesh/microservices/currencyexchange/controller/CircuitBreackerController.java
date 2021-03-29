package com.ganesh.microservices.currencyexchange.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreackerController {

	private Logger logger =LoggerFactory.getLogger(CircuitBreackerController.class);
	
	@GetMapping("/sample-api")
	//@Retry(name="sample-api",fallbackMethod = "hardCodedResponse")
	@CircuitBreaker(name="default",fallbackMethod = "hardCodedResponse")
	String SimpleApi() {		
		logger.info("Sample Api request recived ->> ");
		ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:8080", String.class);
		return  responseEntity.getBody();
	}
	
	
	public String hardCodedResponse(Exception e) {
		return "Fallback -response called!";
	}
}
