package com.mc.rest.webservices.restfulwebservices.util;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mc.rest.webservices.restfulwebservices.model.Loan;


//@FeignClient(name="loan-service", url="localhost:9091")
@FeignClient(name="netflix-zuul-api-gateway-server")
@RibbonClient(name="loan-service")
public interface LoanServiceProxy {
	@GetMapping("loan-service/loans/{id}")
	public List<Loan> retrieveUserById(@PathVariable int id);
}