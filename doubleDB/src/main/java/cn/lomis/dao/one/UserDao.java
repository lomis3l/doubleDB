package cn.lomis.dao.one;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.lomis.po.one.User;

/**
 * 用户Dao
 * @author lomis
 *
 */
public interface UserDao {
	
	public void insert(User user);
	
	public void delete(Long id);
	
	public void update(User user);
	
	public User getUser(@Param("id")Long id);
	
	public List<User> findUser();
}
