package com.lyu.bookstore.service;

import java.util.List;

import com.lyu.bookstore.bean.Book;
import com.lyu.bookstore.util.PageUtil;

public interface BookService {

	List<Book> queryBookByPage(PageUtil pageUtil);

	int getCount();

	boolean updateCount(Integer bid, int count);

	int getFuzzyQueryCount(String param);

	List<Book> fuzzyQueryBookByPage(PageUtil pageUtil, String param);

}
