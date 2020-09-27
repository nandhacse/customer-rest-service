package com.mc.rest.webservices.restfulwebservices.service;

import com.mc.rest.webservices.restfulwebservices.model.User;

public interface UserService {
	void createUser(User user);
	boolean login(String username, String password);
	boolean logout();
	User updateProfile(User user);
}
