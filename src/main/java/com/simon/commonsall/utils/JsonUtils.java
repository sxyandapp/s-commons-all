package com.simon.commonsall.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/** 
 * 描述：JsonUtils 
 *  TODO 此版本有问题，不要使用
 *  !!!!WARNING!!!!!
 * @author Shixy 
 * @date   2015年8月9日
 * @since  1.0 
 */
public class JsonUtils {
	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		// 解析器支持解析单引号
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		// 解析器支持解析结束符
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		//忽略目标类中不存在的属性
//		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		mapper.setConfig(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 若值为Null，不参与序列化
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	/**
	 * 根据Json值生成类实例
	 * 
	 * @param jsonString
	 *            json字符串
	 * @param valueType
	 *            类型
	 * @return 若生成失败返回Null
	 */
	public static <T> T from(String jsonString, Class<T> valueType) {
		T t = null;
		try {
			if (StringUtils.isEmpty(jsonString)) {
				return null;
			}
			t = mapper.readValue(jsonString, valueType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public static <T> T from(String jsonString, TypeReference<T> valueType) {
		T t = null;
		try {
			if (StringUtils.isEmpty(jsonString)) {
				return null;
			}
			t = mapper.readValue(jsonString, valueType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 将指定实例转为Json字符串
	 * 
	 * @param object
	 * @return 若转换失败Null
	 */
	public static String to(Object object) {
		Writer strWriter = new StringWriter();
		try {
			mapper.writeValue(strWriter, object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strWriter.toString();

	}
}
