package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.annotation.RequestMapping;
import mvc.annotation.ResponseBody;
import service.ProductService;
import util.AjaxResult;
import util.CommonUtil;

import com.github.pagehelper.PageInfo;


import constant.Constant;
import entity.Cart;
import entity.Product;
import entity.User;
import factory.ObjectFactory;

@RequestMapping("/product")
public class ProductController {
	ProductService ps;
	@RequestMapping("/findAllProduct")
	@ResponseBody
	public AjaxResult findAllProduct(HttpServletRequest request, HttpServletResponse response) 
	{
		ps = (ProductService) ObjectFactory.getObject("productService");
		AjaxResult result = new AjaxResult(true, "查询成功", null);
		Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
		try {
			PageInfo<Product> info = ps.findAllProduct(pageNo);	
			result.setObj(info);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/modifyCart")
	public void modifyCart(HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("modifyCart");
		ps = (ProductService) ObjectFactory.getObject("productService");
		Cart c = new Cart();
		CommonUtil.getObj(request, c, null);
		User u  = (User) request.getSession().getAttribute(Constant.SESSION_USER);
		try{
			if(u==null){
				System.out.println("用户没登录");
			}else{
				c.setUserId(u.getId());
				ps.modifyCart(c);
			}
		}catch(Exception e){
			System.out.println("修改购物车失败");
		}
		
	}
	@RequestMapping("/deleteCart")
	public void deleteCart(HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("deleteCart");
		ps = (ProductService) ObjectFactory.getObject("productService");
		Integer productId = Integer.valueOf(request.getParameter("productId"));
		User u  = (User) request.getSession().getAttribute(Constant.SESSION_USER);
		try{
			if(u==null){
				System.out.println("用户没登录");
			}else{
				ps.deleteCart(u.getId(),productId);
			}
		}catch(Exception e){
			System.out.println("删除购物车失败");
		}
		
	}
	
	@RequestMapping("/deleteAllCart")
	public void deleteAllCart(HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("deleteAllCart");
		ps = (ProductService) ObjectFactory.getObject("productService");
		User u  = (User) request.getSession().getAttribute(Constant.SESSION_USER);
		try{
			if(u==null){
				System.out.println("用户没登录");
			}else{
				ps.deleteCart(u.getId(),null);
			}
		}catch(Exception e){
			System.out.println("删除购物车失败");
		}
		
	}
	@RequestMapping("/addCart")
	public String addCart(HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("......");
		ps = (ProductService) ObjectFactory.getObject("productService");
		Integer productId = Integer.valueOf(request.getParameter("productId"));	
		Double price = Double.valueOf(request.getParameter("price"));	
		User u  = (User) request.getSession().getAttribute(Constant.SESSION_USER);
		try{
			if(u==null){
				System.out.println("用户没登录");
			}
			ps.addToCart(u.getId(),productId,price);
		}catch(Exception e){
			System.out.println("添加购物车失败");
		}
		return "/showList.do";
	}
	
	@RequestMapping("/findCart")
	@ResponseBody	
	public AjaxResult findCart(HttpServletRequest request, HttpServletResponse response) 
	{
		ps = (ProductService) ObjectFactory.getObject("productService");
		AjaxResult result = new AjaxResult(true, "查询成功", null);
		Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
		User u = (User)request.getSession().getAttribute(Constant.SESSION_USER);
		try {
			PageInfo<Cart> info = ps.findCartByUserId(u.getId(),pageNo);
			System.out.println("controller-cart:"+info);
			result.setObj(info);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/findAllCart")
	@ResponseBody	
	public List<Cart> findAllCart(HttpServletRequest request, HttpServletResponse response) 
	{
		ps = (ProductService) ObjectFactory.getObject("productService");
		User u = (User)request.getSession().getAttribute(Constant.SESSION_USER);
		List<Cart>list = null;
		try {
			list = ps.findAllCartByUserId(u.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("findAllCart:"+list);
		return list;
	}
	
	@RequestMapping("/createOrder")
	public String createOrder(HttpServletRequest request, HttpServletResponse response) 
	{
		ps = (ProductService) ObjectFactory.getObject("productService");	
		User u  = (User) request.getSession().getAttribute(Constant.SESSION_USER);
		Double price = Double.valueOf(request.getParameter("sumPrice"));
		
		try{
			if(u==null){
				System.out.println("用户没登录");
			}
			String orderId = ps.createOrder(u.getId(),price);
			request.getSession().setAttribute("no", orderId);
			System.out.println("创建订单成功");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("创建订单失败");
		}
		return "/showSuccess.do";
	}
}
