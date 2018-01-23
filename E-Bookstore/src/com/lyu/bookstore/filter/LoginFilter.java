package com.lyu.bookstore.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lyu.bookstore.bean.User;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("LoginFilter.destroy()");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 将请求和响应强制转换成servlet中的请求和响应
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// 获取session对象,并且查看session中是否还有user对象
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		String path = req.getRequestURI();
		// 判断对象是否为空
		if (user != null || path.indexOf("login.jsp") != -1
				|| path.indexOf("login.action") != -1
				|| path.indexOf("register.action") != -1) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect("login.jsp");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("LoginFilter.init()");
	}

}
