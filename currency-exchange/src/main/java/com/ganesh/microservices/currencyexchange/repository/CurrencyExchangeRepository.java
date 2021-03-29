package com.ganesh.microservices.currencyexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ganesh.microservices.currencyexchange.beans.CurrencyExchange;

public interface  CurrencyExchangeRepository  extends JpaRepository<CurrencyExchange,Long>{
	CurrencyExchange findByFromAndTo(String from, String to);
}
