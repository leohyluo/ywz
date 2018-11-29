package com.alpha.commons.core.framework;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.google.common.collect.Lists;

/**
 *Spring容器初始化的时候将HandlerMethodReturnValueHandler替换为自定义的CustomRequestResponseBodyMethodProcessor
 * 目的是让有@ResponseBody注解方法上在返回之前进入自定义的CustomRequestResponseBodyMethodProcessor
 */
@Component
public class ResponseBodyWrapFactoryBean implements InitializingBean {
	
	@Resource
	private RequestMappingHandlerAdapter handlerAdapter;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<HandlerMethodReturnValueHandler> returnValueList = handlerAdapter.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> returnValueHandlersList = Lists.newArrayList(returnValueList);
		decorateHandlers(returnValueHandlersList);
		handlerAdapter.setReturnValueHandlers(returnValueHandlersList);
	}
	
	private void decorateHandlers(List<HandlerMethodReturnValueHandler> returnValueList) {
		for(HandlerMethodReturnValueHandler handler : returnValueList) {
			//用自定义的替换掉框架的处理器
			if(handler instanceof RequestResponseBodyMethodProcessor) {
				int index = returnValueList.indexOf(handler);
				returnValueList.set(index, new CustomRequestResponseBodyMethodProcessor(handlerAdapter.getMessageConverters()));
				break;
			}
		}
	}

}
