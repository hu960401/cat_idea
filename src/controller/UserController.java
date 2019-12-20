package controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.annotation.RequestMapping;
import mvc.annotation.ResponseBody;
import service.UserService;


import constant.Constant;
import entity.Cart;
import entity.User;
import exception.ServiceException;

import util.CommonUtil;
import util.CommonUtil.MyImage;

import factory.ObjectFactory;
@RequestMapping("/user")
public class UserController {
	
	UserService userService;
	@RequestMapping("/login")
	@ResponseBody
	public Map<String,String> login(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> result = new HashMap<String,String>();
		String code =  request.getParameter("code");
		String localCode = (String) request.getSession().getAttribute("code");
		if(localCode ==null || !localCode.equals(code)){
			result.put("errMsg", "验证码错误");
			//return result;
		}
		userService = (UserService) ObjectFactory.getObject("userService");
		User u = new User();
		CommonUtil.getObj(request, u, null);
		System.out.println("controller:"+u);
		try {
			User user = userService.login(u);
			request.getSession().setAttribute(Constant.SESSION_USER, user);
			
			result.put("success", "success");
			//redirect:重定向back_Home.do请求(绝对请求路径)
			return result;
		}catch (ServiceException e) {
			result.put("errMsg", e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/getCode")
	public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("UserController.getCode()");
		MyImage m = CommonUtil.getImage(null, 4, true, true);
		System.out.println("code=" + m.getCode());
		request.getSession().setAttribute("code", m.getCode());
		ServletOutputStream responseOutputStream = response.getOutputStream();
		// 输出图象到页面
		ImageIO.write(m.getImage(), "JPEG", responseOutputStream);
		// 以下关闭输入流！
		responseOutputStream.flush();
		responseOutputStream.close();
	}
	
	//退出登录
	@RequestMapping("/logout")
	public String loginOut(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return "redirect:/showList.do";
	}	
	
	
	@RequestMapping("/regist")		
	public String regist(HttpServletRequest request, HttpServletResponse response) {
		String code =  request.getParameter("code");
		String localCode = (String) request.getSession().getAttribute("code");
		if(localCode ==null || !localCode.equals(code)){
			System.out.println("验证码不正确");
			return "/showLogin.do";
		}
		userService = (UserService) ObjectFactory.getObject("userService");
		User u = new User();
		CommonUtil.getObj(request, u, null);
		System.out.println("userController:"+u);
		try {
			userService.regist(u);
			request.getSession().setAttribute(Constant.SESSION_USER, u);
		}catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/showLogin.do";
	}
}
