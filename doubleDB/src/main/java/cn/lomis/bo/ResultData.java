package cn.lomis.bo;

import java.io.Serializable;

import cn.lomis.constant.ResultCodes;
import cn.lomis.util.JsonMapper;

/**
 * 结构返回结果类
 * @author xiexiaogang
 *
 */
public class ResultData implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code;	    // 结果状态码
	private String msg = "success";
	private Object data;	// 结果数据集
	
	public ResultData() {
		code = ResultCodes.Result_Code_200;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String toJson() {
		return JsonMapper.nonNullMapper().toJson(this);
	}

}
