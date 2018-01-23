package com.lyu.bookstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyu.bookstore.bean.Book;
import com.lyu.bookstore.dao.BookDao;
import com.lyu.bookstore.service.BookService;
import com.lyu.bookstore.util.PageUtil;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bd;

	@Override
	public List<Book> queryBookByPage(PageUtil pageUtil) {
		return bd.queryBookByPage(pageUtil);
	}

	@Override
	public int getCount() {
		return bd.getCount();
	}

	@Override
	public boolean updateCount(Integer bid, int count) {
		int stock = bd.getStock(bid);
		if (stock - count < 0) { // 库存不够的情况
			return true;
		} else {
			bd.updateCount(bid, stock - count);
			return false;
		}
	}

	@Override
	public int getFuzzyQueryCount(String param) {
		return bd.getFuzzyQueryCount(param);
	}

	@Override
	public List<Book> fuzzyQueryBookByPage(PageUtil pageUtil, String param) {
		return bd.fuzzyQueryBookByPage(pageUtil,param);
	}
}
