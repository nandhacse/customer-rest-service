package com.mc.rest.webservices.restfulwebservices.dao;

import com.mc.rest.webservices.restfulwebservices.model.User;

public interface UserDao {

	void createUser(User user);
	boolean login(String username, String password);
	boolean logout();
	User updateProfile(User user);
}
