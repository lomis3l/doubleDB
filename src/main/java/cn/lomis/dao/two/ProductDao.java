package cn.lomis.dao.two;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.lomis.po.two.Product;

/**
 * 产品dao
 * @author lomis
 *
 */
public interface ProductDao {
	
	public void insert(Product product);
	
	public void delete(Long id);
	
	public void update(Product product);
	
	public Product getProduct(@Param("id")Long id);
	
	public List<Product> findProduct();
}
