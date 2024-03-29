package service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import service.ProductService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import constant.Constant;
import dao.ProductDao;
import entity.Cart;
import entity.Item;
import entity.Order;
import entity.Product;
import exception.ServiceException;
import factory.ObjectFactory;

public class ProductServiceImpl implements ProductService {
	ProductDao dao;

	@Override
	public PageInfo<Product> findAllProduct(Integer pageNo) throws ServiceException {
		dao = (ProductDao) ObjectFactory.getObject("productDao");
		PageHelper.startPage(pageNo, Constant.PAGE_SIZE);
		List<Product> list = dao.selectAll();

		if (null == list) {
			throw new ServiceException("没有更多商品了...");
		}
		return new PageInfo<Product>(list);
	}

	@Override
	public Product findById(Integer id) throws ServiceException {
		dao = (ProductDao) ObjectFactory.getObject("productDao");
		return dao.selectById(id);
	}

	@Override
	public void addToCart(Integer userId, Integer productId, Double price)
			throws ServiceException {
		dao = (ProductDao) ObjectFactory.getObject("productDao");

		Cart c = dao.selectItemFromCartByUserIdAndProductId(userId, productId);
		if (c == null) {
			c = new Cart();
			c.setUserId(userId);
			c.setNum(1);
			c.setPrice(price);
			c.setProductId(productId);
			dao.insertIntoCart(c);
		} else {
			c.setNum(c.getNum() + 1);
			c.setPrice(c.getPrice() + price);
			dao.updateCart(c);
		}
	}

	@Override
	public PageInfo<Cart> findCartByUserId(Integer userId, Integer pageNo) throws ServiceException {
		dao = (ProductDao) ObjectFactory.getObject("productDao");
		//开启分页插件的分页功能
		PageHelper.startPage(pageNo, Constant.PAGE_SIZE);
		List<Cart> carts = dao.selectCartByUserId(userId);

		PageInfo<Cart> info = new PageInfo<Cart>(carts);
		return info;
	}

	@Override
	public void modifyCart(Cart c)
			throws ServiceException {
		dao = (ProductDao) ObjectFactory.getObject("productDao");
		int num = c.getNum();
		c.setPrice(num * c.getPrice());
		if (num > 0) {
			dao.updateCart(c);
		} else {
			dao.deleteCart(c.getUserId(), c.getProductId());
		}
	}

	@Override
	public void deleteCart(Integer userId, Integer productId)
			throws ServiceException {
		dao = (ProductDao) ObjectFactory.getObject("productDao");
		dao.deleteCart(userId, productId);
	}

	@Override
	public List<Cart> findAllCartByUserId(Integer userId) throws ServiceException {
		dao = (ProductDao) ObjectFactory.getObject("productDao");
		List<Cart> carts = dao.selectCartByUserId(userId);

		return carts;
	}

	@Override
	public String createOrder(Integer userId, Double price) throws ServiceException {
		dao = (ProductDao) ObjectFactory.getObject("productDao");
		//生成订单号
		String no = createNo();
		Order o = new Order();
		o.setNo(no);
		o.setPrice(price);
		o.setUserId(userId);
		//同时获取自动生成的id值
		dao.insertOrder(o);
		List<Cart> list = dao.selectCartByUserId(userId);
		//如果用户只是刷新success.jsp页面，则会一次购物，产生多个订单
		if (list.isEmpty()) {
			throw new ServiceException("订单已经产生");
		}
		Item i;
		for (Cart c : list) {
			i = new Item();
			i.setNum(c.getNum());
			i.setOrderId(o.getId());
			i.setPrice(c.getPrice());
			i.setProductId(c.getProductId());
			dao.insertIntoItem(i);
		}
		dao.deleteCart(userId, null);
		return o.getNo();
	}

	private String createNo() {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			sb.append((char) ('a' + r.nextInt(26)));
		}
		return sb.toString() + new Date().getTime();
	}
}