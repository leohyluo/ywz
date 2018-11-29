package com.alpha.commons.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	protected static final ObjectMapper jacksonMapper = Jackson2ObjectMapperBuilder.json().build();
	private static final Logger logger = Logger.getLogger(JsonUtils.class);

	public static ObjectMapper getJacksonMapper() {
		return jacksonMapper;
	}

	public static String toJsonString(Object obj) {
		try {
			return getJacksonMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}

	@SuppressWarnings("unchecked") 
	public static Map<String, Object> json2Map(String jsonStr) {
		try {
			return getJacksonMapper().readValue(jsonStr, Map.class);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} 
		return null;
	}
	
	public static boolean isJson(String str) {
		try {
			 getJacksonMapper().readValue(str, Map.class);
			 return true;
		}catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	public static String map2Json(Map<String, Object> map) {
		try {
			return getJacksonMapper().writeValueAsString(map);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
		return "{}";
	}

	public static <T> String serrializeObject(T t) {
		String jsonStr = JSON.toJSONString(t);
		return jsonStr;
	}

	public static <T> T deserialize(String jsonStr, Class<T> clazz) {
		T t = JSON.parseObject(jsonStr, clazz);
		return t;
	}

	public static <T> List<T> deserialize2List(String jsonStr, Class<T> clazz) {
		JSONArray jarr = JSONArray.parseArray(jsonStr);
		List<T> list = jarr.toJavaList(clazz);
		return list;
	}
}
