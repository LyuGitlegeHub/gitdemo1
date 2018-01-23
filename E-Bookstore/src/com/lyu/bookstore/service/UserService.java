package com.lyu.bookstore.service;

import java.util.List;

import com.lyu.bookstore.bean.User;

public interface UserService {

	int insertUser(User user);

	List<String> selectAllUsername();

	User login(User user);

}
