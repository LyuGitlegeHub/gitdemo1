package com.lyu.bookstore.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lyu.bookstore.bean.Book;
import com.lyu.bookstore.bean.T_order;
import com.lyu.bookstore.bean.User;
import com.lyu.bookstore.service.T_orderService;
import com.lyu.bookstore.util.PageUtil;

@Controller
@SessionAttributes({ "user" })
public class T_orderController {

	@Autowired
	private T_orderService ts;

	private static final int CURRNAV = 1;
	private static final int PAGESIZE = 5;

	/**
	 * 短消息展示
	 * 
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping("/showOrder.action")
	public String showt_order(ModelMap map) {
		// 从session中获取username
		User user = (User) map.get("user");
		int rowcount = ts.getCount(user.getUsername());

		// 获取pageUtil对象，并对其中的属性进行赋值
		PageUtil pageUtil = new PageUtil(rowcount, PAGESIZE, CURRNAV, 10);

		List<T_order> t_orderList = new ArrayList<>();
		t_orderList = ts.queryT_orderByPage(pageUtil, user.getUsername());
		pageUtil.setPageData(t_orderList);
		map.put("pageutil", pageUtil);
		return "order.jsp";
	}

	@RequestMapping("/showOrderByPage.action")
	public String showt_orderByPage(int currnav, ModelMap map) {

		// 从session中获取username
		User user = (User) map.get("user");
		int rowcount = ts.getCount(user.getUsername());

		// 获取pageUtil对象，并对其中的属性进行赋值
		PageUtil pageUtil = new PageUtil(rowcount, PAGESIZE, currnav, 10);

		List<T_order> t_orderList = new ArrayList<>();
		t_orderList = ts.queryT_orderByPage(pageUtil, user.getUsername());
		pageUtil.setPageData(t_orderList);
		map.put("pageutil", pageUtil);
		return "order.jsp";
	}
	
	/**
	 * 订单页模糊查询订单信息
	 * @param param
	 * @param map
	 * @return
	 */
	@RequestMapping("/fuzzyQueryOrderByPage.action")
	public String fuzzyQueryByPageAction(String param,ModelMap map){
		
		User user = (User) map.get("user");
		int rowcount = ts.getFuzzyQueryCount(param,user.getUsername());

		// 获取pageUtil对象，并对其中的属性进行赋值
		PageUtil pageUtil = new PageUtil(rowcount, PAGESIZE, CURRNAV, 10);

		List<Book> bookList = new ArrayList<>();
		bookList = ts.fuzzyQueryOrderByPage(pageUtil,param,user.getUsername());
		pageUtil.setPageData(bookList);
		map.put("pageutil", pageUtil);
		return "order.jsp";
	}
}
