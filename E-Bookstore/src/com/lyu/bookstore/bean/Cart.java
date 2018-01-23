package com.lyu.bookstore.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//购物车类
public class Cart {

	List<Book> booklist = new ArrayList<Book>();

	public List<Book> getList() {
		return booklist;
	}

	public void setList(List<Book> booklist) {
		this.booklist = booklist;
	}

	// 添加商品
	public void addBook(Book book) {
		for (Book b : booklist) {
			if (b.getBid() == book.getBid() || b.getBid().equals(book.getBid())) {
				return;
			}
		}
		booklist.add(book);
	}

	//移除商品
	public void removeBook(Book book) {
		Iterator<Book> iter = booklist.iterator();
		while (iter.hasNext()) {
			Book b = iter.next();
			if (b.equals(book)) {
				iter.remove();
			}
		}
	}

	public void clearCart() {
		booklist.clear();
	}
}
