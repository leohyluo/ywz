package com.alpha.commons.core.framework;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alpha.commons.core.annotation.JSON;
import com.alpha.commons.core.annotation.JSONS;
import com.alpha.commons.core.jackson.MyJacksonSerializer;


public class CustomJSONReturnValueHandler implements HandlerMethodReturnValueHandler {

	/**
	 * RequestMappingHandlerAdapter类里customReturnValueHandlers：存放我们自定义的HandlerMethodReturnValueHandler； 
		returnValueHandlers：存放最终所有的HandlerMethodReturnValueHandler； 
		在RequestMappingHandlerAdapter创建出来后，会执行afterPropertiesSet()方法，
		在该方法中会设置所有的HandlerMethodReturnValueHandler到RequestMappingHandlerAdapter的returnValueHandlers属性中
	 */
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		//如果有自定义的 JSON 注解 就用自定义的Handler来处理
		System.out.println("JsonReturnHandler================================================");
		boolean hasJsonsAnnotation = returnType.getMethodAnnotation(JSONS.class) != null;
		boolean hasJsonAnnotation = returnType.getMethodAnnotation(JSON.class) != null;
		return hasJsonAnnotation || hasJsonsAnnotation;
	}

	/**
	 * HandlerMethodReturnValueHandlerComposite中selectHandler获取匹配的HandlerMethodReturnValueHandler,
	 * 根据HandlerMethodReturnValueHandler.supportsReturnType匹配,然后执行匹配出来的HandlerMethodReturnValueHandler的
	 * handleReturnValue方法
	 */
	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		// 设置这个就是最终的处理类了，处理完不再去找下一个类进行处理
        mavContainer.setRequestHandled(true);
        
        // 获得注解并执行filter方法 最后返回
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annotations = returnType.getMethodAnnotations();
        MyJacksonSerializer myjacksonSerializer = new MyJacksonSerializer();
        Stream.of(annotations).forEach(e->{
        	if(e instanceof JSON) {
        		JSON json = (JSON) e;
        		myjacksonSerializer.filter(json);
        	} else if (e instanceof JSONS) {
        		JSONS jsons = (JSONS) e;
        		for(JSON json : jsons.value()) {
        			myjacksonSerializer.filter(json);
        		}
        	}
        });
        
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String result = myjacksonSerializer.toJson(returnValue);
        response.getWriter().write(result);
	}

}
