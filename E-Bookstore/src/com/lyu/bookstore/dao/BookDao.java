package com.lyu.bookstore.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lyu.bookstore.bean.Book;
import com.lyu.bookstore.util.PageUtil;

public interface BookDao {

	@Select(value = {
			"SELECT bid, bookname, b_price as bPrice, image, writer, stock FROM book limit #{startrow},#{pageSize}" })
	List<Book> queryBookByPage(PageUtil pageUtil);

	@Select(value = { "SELECT count(1) FROM book" })
	int getCount();

	@Select(value = { "SELECT stock FROM book WHERE bid = #{bid}" })
	int getStock(Integer bid);

	@Update(value = { "UPDATE book SET stock=#{stock} WHERE bid=#{bid}" })
	void updateCount(@Param(value = "bid") int bid, @Param(value = "stock") int stock);

	@Select(value = { "SELECT count(1) FROM book WHERE bookname like CONCAT(CONCAT('%', #{param}), '%') "
			+ "or writer like CONCAT(CONCAT('%', #{param}), '%')" })
	int getFuzzyQueryCount(String param);

	@Select(value = { "SELECT bid, bookname, b_price as bPrice, image, writer, stock FROM book "
			+ "WHERE bookname like CONCAT(CONCAT('%', #{param}), '%') or writer like CONCAT(CONCAT('%', #{param}), '%') limit #{pageUtil.startrow},#{pageUtil.pageSize}" })
	List<Book> fuzzyQueryBookByPage(@Param(value = "pageUtil") PageUtil pageUtil, @Param(value = "param") String param);

}
