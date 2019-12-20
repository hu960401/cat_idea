package service.proxy;

import java.util.List;

import service.ProductService;
import transaction.TransactionManager;

import com.github.pagehelper.PageInfo;

import entity.Cart;
import entity.Product;
import exception.ServiceException;
import factory.ObjectFactory;

public class ProductServiceProxy implements ProductService{
	private ProductService ps;
	private TransactionManager tm;


	@Override
	public PageInfo<Product> findAllProduct(Integer pageNo) throws ServiceException {
		ps = (ProductService) ObjectFactory.getObject("productServiceTarget");
		tm = (TransactionManager) ObjectFactory.getObject("tm");
		PageInfo<Product> info = null;
		try {
			tm.beginTransaction();
			info = ps.findAllProduct(pageNo);
			tm.commit();
		} catch (Exception e) {
			tm.rollback();
			e.printStackTrace();
			throw new ServiceException("发生未知错误...");
		}
		return info;
	}


	@Override
	public Product findById(Integer id) throws ServiceException {
		ps = (ProductService) ObjectFactory.getObject("productServiceTarget");
		tm = (TransactionManager) ObjectFactory.getObject("tm");
		Product p = null;
		try {
			tm.beginTransaction();
			p = ps.findById(id);
			tm.commit();
		} catch (Exception e) {
			tm.rollback();
			e.printStackTrace();
			throw new ServiceException("发生未知错误...");
		}
		return p;
	}


	@Override
	public void addToCart(Integer userId, Integer productId,Double price) throws ServiceException {
		ps = (ProductService) ObjectFactory.getObject("productServiceTarget");
		tm = (TransactionManager) ObjectFactory.getObject("tm");
		try {
			tm.beginTransaction();
			ps.addToCart(userId,productId,price);
			tm.commit();
		} catch (Exception e) {
			tm.rollback();
			e.printStackTrace();
			throw new ServiceException("发生未知错误...");
		}
	}


	@Override
	public PageInfo<Cart> findCartByUserId(Integer userId,Integer pageNo) throws ServiceException {
		ps = (ProductService) ObjectFactory.getObject("productServiceTarget");
		tm = (TransactionManager) ObjectFactory.getObject("tm");
		PageInfo<Cart> list = null;
		try {
			tm.beginTransaction();
			list = ps.findCartByUserId(userId,pageNo);
			tm.commit();
		} catch (Exception e) {
			tm.rollback();
			e.printStackTrace();
			throw new ServiceException("发生未知错误...");
		}
		return list;
	}


	@Override
	public void modifyCart(Cart c) throws ServiceException {
		ps = (ProductService) ObjectFactory.getObject("productServiceTarget");
		tm = (TransactionManager) ObjectFactory.getObject("tm");
		try {
			tm.beginTransaction();
			ps.modifyCart(c);
			tm.commit();
		} catch (Exception e) {
			tm.rollback();
			e.printStackTrace();
			throw new ServiceException("发生未知错误...");
		}
	}


	@Override
	public void deleteCart(Integer id, Integer productId)
			throws ServiceException {
		ps = (ProductService) ObjectFactory.getObject("productServiceTarget");
		tm = (TransactionManager) ObjectFactory.getObject("tm");
		try {
			tm.beginTransaction();
			ps.deleteCart(id,productId);
			tm.commit();
		} catch (Exception e) {
			tm.rollback();
			e.printStackTrace();
			throw new ServiceException("发生未知错误...");
		}
	}


	@Override
	public List<Cart> findAllCartByUserId(Integer userId) throws ServiceException {
		ps = (ProductService) ObjectFactory.getObject("productServiceTarget");
		tm = (TransactionManager) ObjectFactory.getObject("tm");
		List<Cart> list = null;
		try {
			tm.beginTransaction();
			list = ps.findAllCartByUserId(userId);
			tm.commit();
		} catch (Exception e) {
			tm.rollback();
			e.printStackTrace();
			throw new ServiceException("发生未知错误...");
		}
		return list;
	}


	@Override
	public String createOrder(Integer userId,Double price) throws ServiceException {
		ps = (ProductService) ObjectFactory.getObject("productServiceTarget");
		tm = (TransactionManager) ObjectFactory.getObject("tm");
		String orderId = null;
		try {
			tm.beginTransaction();
			orderId = ps.createOrder(userId,price);
			tm.commit();
		} catch (ServiceException e) {
			tm.rollback();
			throw e;
		}
		return orderId;
	}
}
