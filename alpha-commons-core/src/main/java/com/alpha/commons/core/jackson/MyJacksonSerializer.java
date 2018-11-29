package com.alpha.commons.core.jackson;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.alpha.commons.core.annotation.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyJacksonSerializer {

	ObjectMapper mapper = new ObjectMapper();
	JacksonJsonFilter jacksonFilter = new JacksonJsonFilter();
	
	public void filter(Class<?> clazz, String include, String exclude) {
		if(clazz == null) return;
		if(StringUtils.isNotEmpty(include)) {
			List<String> fieldList = Stream.of(include.split(",")).map(String::trim).collect(Collectors.toList());
			String[] fields = new String[fieldList.size()];
			jacksonFilter.include(clazz, fieldList.toArray(fields));
		}
		if(StringUtils.isNotEmpty(exclude)) {
			List<String> fieldList = Stream.of(exclude.split(",")).map(String::trim).collect(Collectors.toList());
			String[] fields = new String[fieldList.size()];
			jacksonFilter.exclude(clazz, fieldList.toArray(fields));
		}
		//为class类应用过滤器
		mapper.addMixIn(clazz, jacksonFilter.getClass());
	}
	
	public String toJson(Object obj) throws JsonProcessingException {
		//设置过滤器
		mapper.setFilterProvider(jacksonFilter);
		return mapper.writeValueAsString(obj);
	}
	
	public void filter(JSON json) {
		this.filter(json.type(), json.include(), json.exclude());
	}
}
