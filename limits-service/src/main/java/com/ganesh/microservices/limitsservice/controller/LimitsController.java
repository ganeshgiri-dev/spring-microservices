package com.ganesh.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ganesh.microservices.limitsservice.configuration.Configuration;
import com.ganesh.microservices.limitsservice.model.Limits;

@RestController
public class LimitsController {

	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public Limits getLimits() {
//		return new Limits(1,2000);
		
		return new Limits(configuration.getMinimum(),configuration.getMaximum());
	}
	
}
