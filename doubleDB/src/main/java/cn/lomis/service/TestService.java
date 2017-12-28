package cn.lomis.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cn.lomis.dao.one.UserDao;
import cn.lomis.dao.two.ProductDao;

/**
 * 测试Service
 * @author lomis
 *
 */
@Service
public class TestService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;

	public Map<String, Object> getAll() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("users", userDao.findUser());
		map.put("products", productDao.findProduct());
		return map;
	}

}
