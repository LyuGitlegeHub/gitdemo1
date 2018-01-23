package com.lyu.bookstore.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.lyu.bookstore.bean.Book;
import com.lyu.bookstore.bean.T_order;
import com.lyu.bookstore.util.PageUtil;

public interface T_orderDao {

	@Insert(value = { "INSERT INTO t_order(oid, username) VALUES(#{oid}, #{username})" })
	void insertT_order(T_order t_order);

	@Select(value = { "SELECT count(1) from t_order WHERE username=#{username}" })
	int getCount(@Param(value = "username") String username);

	@Select(value = { "SELECT o.oid,o.username,i.iid,i.oid as orderid,i.bid as bookid,i.createdate, i.count,i.price,i.total_price,b.bid,b.bookname,b.b_price,b.image,b.writer,b.stock "
	+"FROM t_order o LEFT JOIN items i on o.oid = i.oid LEFT JOIN book b on b.bid = i.bid"
			+ " WHERE o.username =#{username} LIMIT #{pageUtil.startrow},#{pageUtil.pageSize}" })
	@ResultMap(value = "com.lyu.bookstore.dao.T_orderDao.T_orderBaseResultMap")
	List<T_order> queryT_orderByPage(@Param(value = "pageUtil")PageUtil pageUtil, @Param(value = "username") String username);

	@Select(value = { "SELECT count(1) FROM t_order WHERE username=#{username} AND oid LIKE CONCAT(CONCAT('%', #{param}), '%')" })
	int getFuzzyQueryCount(@Param(value = "param") String param,@Param(value = "username")String username);

	@Select(value = { "SELECT o.oid,o.username,i.iid,i.oid as orderid,i.bid as bookid,i.createdate, i.count,i.price,i.total_price,b.bid,b.bookname,b.b_price,b.image,b.writer,b.stock "
			+"FROM t_order o LEFT JOIN items i on o.oid = i.oid LEFT JOIN book b on b.bid = i.bid"
					+ " WHERE o.username =#{username} AND o.oid LIKE CONCAT(CONCAT('%', #{param}), '%') "
			+"LIMIT #{pageUtil.startrow},#{pageUtil.pageSize}" })
			@ResultMap(value = "com.lyu.bookstore.dao.T_orderDao.T_orderBaseResultMap")
	List<Book> fuzzyQueryOrderByPage(@Param(value = "pageUtil")PageUtil pageUtil,@Param(value = "param") String param,@Param(value = "username")String username);

}
