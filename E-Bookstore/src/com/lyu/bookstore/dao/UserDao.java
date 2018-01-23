package com.lyu.bookstore.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.lyu.bookstore.bean.User;

public interface UserDao {

	@Insert(value = { "INSERT INTO user(username,password,email) VALUES(#{username}," + "#{password},#{email})" })
	int insertUser(User user);

	@Select(value = { "SELECT username from user" })
	List<String> selectAllUsername();

	@Select(value = { "SELECT username, password from user WHERE username=#{username} and password=#{password}" })
	User login(User user);

}
