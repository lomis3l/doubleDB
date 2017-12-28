package cn.lomis.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.lomis.bo.ResultData;
import cn.lomis.constant.ResultCodes;
import cn.lomis.po.one.User;
import cn.lomis.service.TestService;

/**
 * 测试action
 * @author lomis
 *
 */
@RestController
@RequestMapping(value = "test")
public class TestAction {
	
	@Autowired
	private TestService testService;
	
	@RequestMapping(method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public String all(@RequestBody User user) {
		ResultData resultData = new ResultData();
		try {
			resultData.setData(testService.getAll());
		} catch (Exception e) {
			resultData.setCode(ResultCodes.Result_Code_50000);
			resultData.setMsg(ResultCodes.Result_Message_50000);
		}
		return resultData.toJson();
	}
}
