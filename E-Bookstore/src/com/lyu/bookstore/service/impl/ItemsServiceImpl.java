package com.lyu.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyu.bookstore.bean.Items;
import com.lyu.bookstore.dao.ItemsDao;
import com.lyu.bookstore.service.ItemsService;

@Service
public class ItemsServiceImpl implements ItemsService {

	@Autowired
	private ItemsDao id;
	
	@Override
	public void insertItems(Items items) {

		id.insertItems(items);
	}

}
