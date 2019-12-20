package service.impl;

import constant.Constant;

import factory.ObjectFactory;

import entity.User;
import exception.ServiceException;
import service.UserService;
import dao.*;
public class UserServiceImpl implements UserService{
	UserDao dao;
	@Override
	public User login(User user) throws ServiceException {
		dao = (UserDao) ObjectFactory.getObject("userDao");
		User u = dao.selectByLoginnameAndPassword(user);
		if(u == null){
			throw new ServiceException("用户名/密码错误");
		}
		return u;
	}
	@Override
	public void regist(User u) throws ServiceException {
		dao = (UserDao) ObjectFactory.getObject("userDao");
		User user = dao.selectByLoginnameAndPassword(u);
		if(user == null){
			dao.insertUser(u);
		}else{
			throw new ServiceException("用户名已成功注册");
		}
	}
}
