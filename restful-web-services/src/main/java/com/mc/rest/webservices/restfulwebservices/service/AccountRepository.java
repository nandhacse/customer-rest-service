package com.mc.rest.webservices.restfulwebservices.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mc.rest.webservices.restfulwebservices.model.Account;



public interface AccountRepository extends JpaRepository<Account, Integer> {

}
