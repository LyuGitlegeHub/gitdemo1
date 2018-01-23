package com.lyu.bookstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyu.bookstore.bean.User;
import com.lyu.bookstore.dao.UserDao;
import com.lyu.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao	ud;
	@Override
	public int insertUser(User user) {
		return ud.insertUser(user);
	}

	@Override
	public List<String> selectAllUsername() {
		return ud.selectAllUsername();
	}

	@Override
	public User login(User user) {
		return ud.login(user);
	}

}
