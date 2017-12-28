package cn.lomis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lomis.dao.two.ProductDao;
import cn.lomis.po.two.Product;

/**
 * 产品Service
 * @author lomis
 *
 */
@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	
	public void add(Product product) {
		productDao.insert(product);
	}
	
	public void delete(Long id) {
		productDao.delete(id);
	}
	
	public void update(Product product) {
		productDao.update(product);
	}
	
	public Product getProduct(Long id) {
		return productDao.getProduct(id);
	}
	
	public List<Product> getProducts(){
		return productDao.findProduct();
	}
}
