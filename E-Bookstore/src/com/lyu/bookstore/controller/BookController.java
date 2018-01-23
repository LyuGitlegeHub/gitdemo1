package com.lyu.bookstore.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lyu.bookstore.bean.Book;
import com.lyu.bookstore.bean.Cart;
import com.lyu.bookstore.service.BookService;
import com.lyu.bookstore.util.PageUtil;
import com.lyu.bookstore.vo.MsgVo;

@Controller
public class BookController {

	@Autowired
	private BookService bs;
	private Cart cart = new Cart();
	private MsgVo msg = new MsgVo();
	Gson gson = new Gson();

	private static final int CURRNAV = 1;
	private static final int PAGESIZE = 5;

	/**
	 * 商品展示
	 * 
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping("/showBook.action")
	public String showBook(ModelMap map) {

		int rowcount = bs.getCount();

		// 获取pageUtil对象，并对其中的属性进行赋值
		PageUtil pageUtil = new PageUtil(rowcount, PAGESIZE, CURRNAV, 10);

		List<Book> bookList = new ArrayList<>();
		bookList = bs.queryBookByPage(pageUtil);
		pageUtil.setPageData(bookList);
		map.put("pageutil", pageUtil);
		return "index.jsp";
	}

	@RequestMapping("/showBookByPage.action")
	public String showBookByPage(int currnav, ModelMap map) {

		int rowcount = bs.getCount();

		// 获取pageUtil对象，并对其中的属性进行赋值
		PageUtil pageUtil = new PageUtil(rowcount, PAGESIZE, currnav, 10);

		List<Book> bookList = new ArrayList<>();
		bookList = bs.queryBookByPage(pageUtil);
		pageUtil.setPageData(bookList);
		map.put("pageutil", pageUtil);
		return "index.jsp";
	}

	// 放入购物车
	@RequestMapping("/cartAdd.action")
	@ResponseBody
	public MsgVo cartAdd(@CookieValue(value = "cartCookie", required = false) String cartCookieStr,
			@RequestBody Book book, HttpServletResponse response) {
		if (cartCookieStr == null || "".equals(cartCookieStr)) {
			cart.clearCart();
			cart.addBook(book);
		} else {
			JsonObject jsonCart = (JsonObject) new JsonParser().parse(cartCookieStr);
			cart = gson.fromJson(jsonCart, Cart.class);// Json转换成对象Cart
			cart.addBook(book);
		}

		String cartCookie = gson.toJson(cart); // 对象转换json字符串
		Cookie cookie = null;
		try {
			cookie = new Cookie("cartCookie", URLEncoder.encode(cartCookie, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cookie.setMaxAge(60 * 60 * 24 * 2);// 保留2天
		response.addCookie(cookie);
		msg.setMessage("添加书籍成功!");
		msg.setStatus(1);
		return msg;
	}

	/**
	 * 移除商品
	 * @param cartCookieStr
	 * @param book
	 * @param response
	 * @param cart
	 * @return
	 */
	@RequestMapping("/cartRemove.action")
	@ResponseBody
	public MsgVo cartRemove(@CookieValue(value = "cartCookie", required = false) String cartCookieStr,
			@RequestBody Book book, HttpServletResponse response, Cart cart) {
		JsonObject jsonCart = (JsonObject) new JsonParser().parse(cartCookieStr);
		cart = gson.fromJson(jsonCart, Cart.class); // Json转换成对象Cart
		cart.removeBook(book);
		String cartCookie = gson.toJson(cart); // 对象转换json字符串
		Cookie cookie = null;
		try {
			cookie = new Cookie("cartCookie", URLEncoder.encode(cartCookie, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cookie.setMaxAge(60 * 60 * 24 * 2);// 保留2天
		response.addCookie(cookie);
		msg.setMessage("移除书籍成功!");
		msg.setStatus(1);

		return msg;
	}

	/**
	 * 展示购物车 从cookie获取数据放入ModelMap
	 * 
	 * @param cartCookieStr
	 * @param map
	 * @return
	 */
	@RequestMapping("/showCart.action")
	public String showCartAction(@CookieValue(value = "cartCookie", required = false) String cartCookieStr,
			ModelMap map) {
		if (cartCookieStr != null && !"".equals(cartCookieStr)) {

			try {
				String cartCookie = URLDecoder.decode(cartCookieStr, "UTF-8");
				JsonObject jsonCart = (JsonObject) new JsonParser().parse(cartCookie);
				cart = gson.fromJson(jsonCart, Cart.class); // Json转换成对象Cart
				List<Book> bookList = cart.getList();
				map.addAttribute("bookList", bookList);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "cart.jsp";
	}
	
	/**
	 * 主页模糊查询书籍
	 * @param param
	 * @param map
	 * @return
	 */
	@RequestMapping("/fuzzyQueryBookByPage.action")
	public String fuzzyQueryByPageAction(String param,ModelMap map){
		int rowcount = bs.getFuzzyQueryCount(param);

		// 获取pageUtil对象，并对其中的属性进行赋值
		PageUtil pageUtil = new PageUtil(rowcount, PAGESIZE, CURRNAV, 10);

		List<Book> bookList = new ArrayList<>();
		bookList = bs.fuzzyQueryBookByPage(pageUtil,param);
		pageUtil.setPageData(bookList);
		map.put("pageutil", pageUtil);
		return "index.jsp";
	}
}
