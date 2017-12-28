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
import cn.lomis.po.one.User;
import cn.lomis.service.UserService;

/**
 * 用户action
 * @author lomis
 *
 */
@RestController
@RequestMapping(value = "user")
public class UserAction {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public String add(@RequestBody User user) {
		ResultData resultData = new ResultData();
		try {
			userService.add(user);
		} catch (Exception e) {
			logger.error("add=> ", e);
			resultData.setCode(ResultCodes.Result_Code_50000);
			resultData.setMsg(ResultCodes.Result_Message_50000);
		}
		return resultData.toJson();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json; charset=UTF-8" })
	public String delete(@PathVariable("id") Long id) {
		ResultData resultData = new ResultData();
		try {
			userService.delete(id);
		} catch (Exception e) {
			logger.error("delete=> ", e);
			resultData.setCode(ResultCodes.Result_Code_50000);
			resultData.setMsg(ResultCodes.Result_Message_50000);
		}
		return resultData.toJson();
	}
	
	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json; charset=UTF-8" })
	public String update(@RequestBody User user) {
		ResultData resultData = new ResultData();
		try {
			userService.update(user);
		} catch (Exception e) {
			logger.error("update=> ", e);
			resultData.setCode(ResultCodes.Result_Code_50000);
			resultData.setMsg(ResultCodes.Result_Message_50000);
		}
		return resultData.toJson();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public String get(@PathVariable("id") Long id) {
		ResultData resultData = new ResultData();
		try {
			resultData.setData(userService.getUser(id));
		} catch (Exception e) {
			logger.error("get=> ", e);
			resultData.setCode(ResultCodes.Result_Code_50000);
			resultData.setMsg(ResultCodes.Result_Message_50000);
		}
		return resultData.toJson();
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public String getList() {
		ResultData resultData = new ResultData();
		try {
			resultData.setData(userService.getUsers());
		} catch (Exception e) {
			logger.error("getlist=> ", e);
			resultData.setCode(ResultCodes.Result_Code_50000);
			resultData.setMsg(ResultCodes.Result_Message_50000);
		}
		return resultData.toJson();
	}
}
