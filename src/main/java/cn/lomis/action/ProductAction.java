package cn.lomis.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.lomis.bo.ResultData;
import cn.lomis.constant.ResultCodes;
import cn.lomis.po.two.Product;
import cn.lomis.service.ProductService;

/**
 * 产品action
 * @author lomis
 *
 */
@RestController
@RequestMapping(value = "product")
public class ProductAction {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductService productService;
	
	@RequestMapping(method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public String add(@RequestBody Product product) {
		ResultData resultData = new ResultData();
		try {
			productService.add(product);
		} catch (Exception e) {
			logger.error("add=>", e);
			resultData.setCode(ResultCodes.Result_Code_50000);
			resultData.setMsg(ResultCodes.Result_Message_50000);
		}
		return resultData.toJson();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json; charset=UTF-8" })
	public String delete(@PathVariable("id") Long id) {
		ResultData resultData = new ResultData();
		try {
			productService.delete(id);
		} catch (Exception e) {
			logger.error("delete=>", e);
			resultData.setCode(ResultCodes.Result_Code_50000);
			resultData.setMsg(ResultCodes.Result_Message_50000);
		}
		return resultData.toJson();
	}
	
	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json; charset=UTF-8" })
	public String update(@RequestBody Product product) {
		ResultData resultData = new ResultData();
		try {
			productService.update(product);
		} catch (Exception e) {
			logger.error("update=>", e);
			resultData.setCode(ResultCodes.Result_Code_50000);
			resultData.setMsg(ResultCodes.Result_Message_50000);
		}
		return resultData.toJson();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public String get(@PathVariable("id") Long id) {
		ResultData resultData = new ResultData();
		try {
			resultData.setData(productService.getProduct(id));
		} catch (Exception e) {
			logger.error("get=>", e);
			resultData.setCode(ResultCodes.Result_Code_50000);
			resultData.setMsg(ResultCodes.Result_Message_50000);
		}
		return resultData.toJson();
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public String getList() {
		ResultData resultData = new ResultData();
		try {
			resultData.setData(productService.getProducts());
		} catch (Exception e) {
			logger.error("getList=>", e);
			resultData.setCode(ResultCodes.Result_Code_50000);
			resultData.setMsg(ResultCodes.Result_Message_50000);
		}
		return resultData.toJson();
	}
}
