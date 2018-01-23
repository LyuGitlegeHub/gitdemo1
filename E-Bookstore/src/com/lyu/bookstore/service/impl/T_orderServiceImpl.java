package com.lyu.bookstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyu.bookstore.bean.Book;
import com.lyu.bookstore.bean.T_order;
import com.lyu.bookstore.dao.T_orderDao;
import com.lyu.bookstore.service.T_orderService;
import com.lyu.bookstore.util.PageUtil;

@Service
public class T_orderServiceImpl implements T_orderService {

	@Autowired
	private T_orderDao td;

	@Override
	public void insertT_order(T_order t_order) {
		td.insertT_order(t_order);
	}

	@Override
	public int getCount(String username) {
		return td.getCount(username);
	}

	@Override
	public List<T_order> queryT_orderByPage(PageUtil pageUtil, String username) {
		return td.queryT_orderByPage(pageUtil, username);
	}

	@Override
	public int getFuzzyQueryCount(String param,String username) {
		return td.getFuzzyQueryCount(param,username);
	}

	@Override
	public List<Book> fuzzyQueryOrderByPage(PageUtil pageUtil, String param,String username) {
		return td.fuzzyQueryOrderByPage(pageUtil, param, username);
	}

}
