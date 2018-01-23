package com.lyu.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lyu.bookstore.bean.User;
import com.lyu.bookstore.service.UserService;
import com.lyu.bookstore.vo.MsgVo;

@Controller
@SessionAttributes({ "user" })
public class UserController {

	@Autowired
	private UserService us;
	private MsgVo msg = new MsgVo();

	/**
	 * 用户注册 注册成功后跳转到登录页面
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("register.action")
	@ResponseBody
	public MsgVo registerAction(@RequestBody User user) {
		if (!checkName(user.getUsername())) {// 验证用户名是否可用
			msg.setMessage("用户名已存在!");
			msg.setStatus(0);
		} else {
			us.insertUser(user);// 保存用户数据到数据库
			msg.setMessage("注册成功!");
			msg.setStatus(1);
		}
		return msg;
	}

	/**
	 * 用户登录
	 * 
	 * @return
	 */
	@RequestMapping("login.action")
	@ResponseBody
	public MsgVo loginAction(@RequestBody User user, ModelMap map) {
		if (checkName(user.getUsername())) {// 验证用户名是否存在
			msg.setMessage("用户名输入错误或不存在");
			msg.setStatus(0);
		} else {
			User user1 = us.login(user);
			if (user1 != null) {
				map.addAttribute("user", user1);
				msg.setMessage("登录成功");
				msg.setStatus(1);
			} else {
				msg.setMessage("用户名或密码错误");
				msg.setStatus(0);
			}
		}
		return msg;
	}

	/**
	 * 用户注销
	 * @param map
	 * @return
	 */
	@RequestMapping("logout.action")
	public String logoutAction(ModelMap map) {
		map.clear();
		return "login.jsp";
	}

	/**
	 * 用户名不存在 返回true
	 * 
	 * @param username
	 * @return
	 */
	public boolean checkName(String username) {
		boolean result = false;
		List<String> nameList = new ArrayList<>();
		nameList = us.selectAllUsername();// 获取所有用户名
		for (String nameStr : nameList) {
			result = true;
			if (nameStr.equals(username)) {
				result = false;
				break;
			}
		}
		return result;
	}
	/*
	 * public static void main(String[] args) { UserController uc= new
	 * UserController(); uc.checkName("admin"); }
	 */
}
