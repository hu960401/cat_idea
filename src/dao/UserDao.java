package dao;

import entity.User;

public interface UserDao {

	User selectByLoginnameAndPassword(User user);

	void insertUser(User u);

}
