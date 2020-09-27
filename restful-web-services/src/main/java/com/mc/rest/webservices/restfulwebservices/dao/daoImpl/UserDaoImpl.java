package com.mc.rest.webservices.restfulwebservices.dao.daoImpl;

import org.springframework.stereotype.Component;

import com.mc.rest.webservices.restfulwebservices.dao.UserDao;
import com.mc.rest.webservices.restfulwebservices.model.User;



@Component
public class UserDaoImpl implements UserDao{

	@Override
	public void createUser(User user) {
		
	}

	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean logout() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User updateProfile(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
