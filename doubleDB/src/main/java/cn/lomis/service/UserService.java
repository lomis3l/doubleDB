package cn.lomis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lomis.dao.one.UserDao;
import cn.lomis.po.one.User;

/**
 * 用户Service
 * @author lomis
 *
 */
@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public void add(User user) {
		userDao.insert(user);
	}
	
	public void delete(Long id) {
		userDao.delete(id);
	}
	
	public void update(User user) {
		userDao.update(user);
	}
	
	public User getUser(Long id) {
		return userDao.getUser(id);
	}
	
	public List<User> getUsers(){
		return userDao.findUser();
	}
}
