package com.mc.rest.webservices.restfulwebservices.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mc.rest.webservices.restfulwebservices.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
