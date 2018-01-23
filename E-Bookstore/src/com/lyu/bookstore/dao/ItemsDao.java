package com.lyu.bookstore.dao;

import org.apache.ibatis.annotations.Insert;

import com.lyu.bookstore.bean.Items;

public interface ItemsDao {

	@Insert(value = { "INSERT INTO items (oid, bid, createdate, count, price, total_price)"
			+ " VALUES (#{oid}, #{bid}, #{createdate}, #{count}, #{price}, #{totalPrice})" })
	void insertItems(Items items);

}
