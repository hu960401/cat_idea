package service.proxy;

import service.UserService;
import transaction.TransactionManager;

import com.github.pagehelper.PageInfo;

import entity.User;
import exception.ServiceException;
import factory.ObjectFactory;


public class UserProxy implements UserService {
	UserService userService;
	TransactionManager tm;
	
	//登录
	@Override
	public User login(User user) throws ServiceException{
		userService = (UserService) ObjectFactory.getObject("userServiceTarget");
		tm = (TransactionManager) ObjectFactory.getObject("tm");
		User u = null;
		try {
			tm.beginTransaction();
			u = userService.login(user);
			tm.commit();
		}catch (Exception e) {
			tm.rollback();
			e.printStackTrace();
			throw new ServiceException("用户名或密码错误",e);
		}
		return u;
	}
	
	//注册
	@Override
	public void regist(User user)throws ServiceException {
		userService = (UserService) ObjectFactory.getObject("userServiceTarget");
		tm = (TransactionManager) ObjectFactory.getObject("tm");
		try {
			tm.beginTransaction();
			userService.regist(user);
			tm.commit();
		}catch (Exception e) {
			tm.rollback();
			e.printStackTrace();
			throw new ServiceException(e.getMessage(),e);
		}
	}
	
}
