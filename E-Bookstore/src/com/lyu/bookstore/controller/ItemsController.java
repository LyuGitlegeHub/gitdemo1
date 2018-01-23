package com.lyu.bookstore.controller;

import java.util.Date;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lyu.bookstore.bean.Book;
import com.lyu.bookstore.bean.Cart;
import com.lyu.bookstore.bean.Items;
import com.lyu.bookstore.bean.T_order;
import com.lyu.bookstore.bean.User;
import com.lyu.bookstore.service.BookService;
import com.lyu.bookstore.service.ItemsService;
import com.lyu.bookstore.service.T_orderService;
import com.lyu.bookstore.util.UUIdUtil;
import com.lyu.bookstore.vo.MsgVo;

@Controller
@SessionAttributes({ "user" })
public class ItemsController {

	@Autowired
	private ItemsService is;
	@Autowired
	private T_orderService os;
	@Autowired
	private BookService bs;

	private Cart cart = new Cart();
	private MsgVo msg = new MsgVo();
	Gson gson = new Gson();

	/**
	 * 用户确认购买 后台接收书名+购买数量.生成订单以及订单明细
	 * 
	 * @param cartCookieStr
	 * @param params
	 * @param map
	 * @return
	 */
	@RequestMapping("/produceOrders.action")
	@ResponseBody
	public MsgVo produceOrders(@CookieValue(value = "cartCookie", required = false) String cartCookieStr,
			@RequestBody String params, ModelMap map, HttpServletResponse response) {
		try {
			// 从购物车中获取cart对象
			JsonObject jsonCart = (JsonObject) new JsonParser().parse(cartCookieStr);
			cart = gson.fromJson(jsonCart, Cart.class);

			// 把前端传过来的json字符串转成jsonArray
			JsonArray jsonArray = (JsonArray) new JsonParser().parse(params).getAsJsonArray();
			User user = (User) map.get("user");

			// 遍历解析jsonarray获取参数
			Iterator<JsonElement> iter = jsonArray.iterator();
			while (iter.hasNext()) {
				// 解析参数
				JsonElement j = iter.next();
				String bookname = j.getAsJsonObject().get("bookname").getAsString();
				int count = j.getAsJsonObject().get("count").getAsInt();

				// 获取对应的书籍
				Book book = getBookFromCart(bookname, cart);

				// 跟新书籍的库存,库存不足返回false
				boolean isNotEnough = bs.updateCount(book.getBid(), count);
				if (isNotEnough) {
					msg.setMessage("库存不足,请选择其他书籍!");
					msg.setStatus(0);
					return msg;
				} else {
					// 每本书生成对应的订单
					T_order t_order = new T_order();
					// 获取uuid
					int uuid = UUIdUtil.getOrderIdByUUId();
					t_order.setOid(uuid);
					t_order.setUsername(user.getUsername());
					// 保存order
					os.insertT_order(t_order);
					// 生成订单明细
					Items items = new Items();
					items.setBid(book.getBid());
					items.setCount(count);
					items.setCreatedate(new Date());
					items.setOid(uuid);// UUid生成
					items.setPrice(book.getbPrice());
					items.setTotalPrice(book.getbPrice() * count);
					// 保存items
					is.insertItems(items);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMessage("生成订单失败,请联系客服人员");
			msg.setStatus(0);
			return msg;
		}

		// 清除cookie中购物车的内容
		try {
			cart.clearCart();
			Cookie cookie = new Cookie("cartCookie", null);
			cookie.setMaxAge(0);// 清除
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		msg.setMessage("购买成功!可在'我的订单'页查看详情!");
		msg.setStatus(1);
		return msg;
	}

	/**
	 * 遍历购物车 cart对象的booklist 获取对应book
	 * @param bookname
	 * @param cart
	 * @return
	 */
	public static Book getBookFromCart(String bookname, Cart cart) {
		List<Book> bookList = cart.getList();
		Iterator<Book> iter = bookList.iterator();
		while (iter.hasNext()) {
			Book b = iter.next();
			if (b.getBookname().equals(bookname)) {
				return b;
			}
		}
		return null;
	}
}
