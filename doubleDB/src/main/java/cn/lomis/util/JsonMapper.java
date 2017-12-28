/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package cn.lomis.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
 * 
 * 封装不同的输出风格, 使用不同的builder函数创建实例.
 * 
 * @author calvin
 * @version 2013-01-15
 */
public class JsonMapper {

	private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

	private ObjectMapper mapper;

	public JsonMapper() {
		this(null);
	}

	public JsonMapper(Include include) {
		mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy年MM月dd日 HH:mm"));
		//设置输出时包含属性的风格
		if (include != null) {
			mapper.setSerializationInclusion(include);
		}
		//设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	public static JsonMapper getJsonMapper() {
		return new JsonMapper();
	}
	
	/**
	 * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
	 */
	public static JsonMapper alwaysMapper() {
		return new JsonMapper(Include.ALWAYS);
	}

	/**
	 * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
	 */
	public static JsonMapper nonEmptyMapper() {
		return new JsonMapper(Include.NON_EMPTY);
	}
	
	public static JsonMapper nonNullMapper() {
		return new JsonMapper(Include.NON_NULL);
	}

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
	 */
	public static JsonMapper nonDefaultMapper() {
		return new JsonMapper(Include.NON_DEFAULT);
	}
	
	/**
	 * 默认值不序列化
	 * @return
	 */
	public static JsonMapper getInstance() {
		return new JsonMapper(Include.NON_DEFAULT).enableSimple();
	}

	/**
	 * Object可以是POJO，也可以是Collection或数组。
	 * 如果对象为Null, 返回"null".
	 * 如果集合为空集合, 返回"[]".
	 */
	public String toJson(Object object) {

		try {
			String json = mapper.writeValueAsString(object);
			return json.replaceAll("\"null\"", "\"\"").replaceAll("null", "\"\"");
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	/**
	 * 反序列化POJO或简单Collection如List<String>.
	 * 
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
	 * @see #fromJson(String, JavaType)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			jsonString = escapeStr(jsonString);
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 反序列化复杂Collection如List<Bean>, 先使用函數createCollectionType构造类型,然后调用本函数.
	 * @see #createCollectionType(Class, Class...)
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, JavaType javaType) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			jsonString = escapeStr(jsonString);
			return (T) mapper.readValue(jsonString, javaType);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 構造泛型的Collection Type如:
	 * ArrayList<MyBean>, 则调用constructCollectionType(ArrayList.class,MyBean.class)
	 * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
	 */
	public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	/**
	 * 當JSON裡只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
	 */
	@SuppressWarnings("unchecked")
	public <T> T update(String jsonString, T object) {
		try {
			return (T) mapper.readerForUpdating(object).readValue(jsonString);
		} catch (JsonProcessingException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		} catch (IOException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		}
		return null;
	}

	/**
	 * 輸出JSONP格式數據.
	 */
	public String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}

	/**
	 * 設定是否使用Enum的toString函數來讀寫Enum,
	 * 為False時時使用Enum的name()函數來讀寫Enum, 默認為False.
	 * 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
	 */
	public JsonMapper enableEnumUseToString() {
		mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		return this;
	}

	/**
	 * 允许单引号
	 * 允许不带引号的字段名称
	 */
	public JsonMapper enableSimple() {
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		return this;
	}
	
	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}
	
	private String escapeStr(String json) {
		if (json == null || json.length() < 1) {
			return json;
		}
		StringBuffer sb = new StringBuffer();
		int no = 0;
		for (int i = 0; i < json.length(); i++) {
			char c = json.charAt(i);
			switch (c) {
			case '"':
				no++;
				sb.append(c);
				break;
			case '\b':
				if (no % 2 == 1) {
					sb.append("\\b");
				} else {
					sb.append(c);
				}
				break;
				
			case '\f':
				if (no % 2 == 1) {
					sb.append("\\f");
				} else {
					sb.append(c);
				}
				break;
			case '\n':
				if (no % 2 == 1) {
					sb.append("\\n");
				} else {
					sb.append(c);
				}
				break;
			case '\r':
				if (no % 2 == 1) {
					sb.append("\\r");
				} else {
					sb.append(c);
				}
				break;
			case '\t':
				if (no % 2 == 1) {
					sb.append("\\t");
				} else {
					sb.append(c);
				}
				break;

			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
	
//	/**
//	 * 测试
//	 */
//	public static void main(String[] args) {
//		List<Map<String, Object>> list = Lists.newArrayList();
//		Map<String, Object> map = Maps.newHashMap();
//		map.put("id", 1);
//		map.put("pId", -1);
//		map.put("name", "根节点");
//		list.add(map);
//		map = Maps.newHashMap();
//		map.put("id", 2);
//		map.put("pId", 1);
//		map.put("name", "你好");
//		map.put("open", true);
//		list.add(map);
//		String json = JsonMapper.getInstance().toJson(list);
//		System.out.println(json);
//	}
	
}
