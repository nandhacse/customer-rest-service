package com.mc.rest.webservices.restfulwebservices.service.serviceImpl;

import org.springframework.stereotype.Component;

import com.mc.rest.webservices.restfulwebservices.service.UserService;
import com.mc.rest.webservices.restfulwebservices.model.User;



@Component
public class UserServiceImpl implements UserService{

	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean logout() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void createUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User updateProfile(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
