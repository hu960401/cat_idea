package service;

import entity.User;
import exception.ServiceException;

public interface UserService {
	public User login(User user) throws ServiceException;

	public void regist(User u)throws ServiceException;
}
