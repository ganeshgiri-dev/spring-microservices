package com.ganesh.microservices.currencyconversion.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ganesh.microservices.currencyconversion.beans.CurrencyConversion;
import com.ganesh.microservices.currencyconversion.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	@Autowired
	CurrencyExchangeProxy currencyExchangeProxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);

		CurrencyConversion body = responseEntity.getBody();

		return new CurrencyConversion(body.getId(), from, to, quantity, body.getConversionMultiple(),
				quantity.multiply(body.getConversionMultiple()), body.getEnvironment() +" " +"Rest template");
	}

	
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

	
		CurrencyConversion body = currencyExchangeProxy.retrieveExchangeValue(from, to);

		return new CurrencyConversion(body.getId(), from, to, quantity, body.getConversionMultiple(),
				quantity.multiply(body.getConversionMultiple()), body.getEnvironment() +" " +"feign");
	}

	
	
}
