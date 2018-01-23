package com.lyu.bookstore.service;

import java.util.List;

import com.lyu.bookstore.bean.Book;
import com.lyu.bookstore.bean.T_order;
import com.lyu.bookstore.util.PageUtil;

public interface T_orderService {

	void insertT_order(T_order t_order);

	int getCount(String username);

	List<T_order> queryT_orderByPage(PageUtil pageUtil, String username);

	int getFuzzyQueryCount(String param, String username);

	List<Book> fuzzyQueryOrderByPage(PageUtil pageUtil, String param, String username);

}
