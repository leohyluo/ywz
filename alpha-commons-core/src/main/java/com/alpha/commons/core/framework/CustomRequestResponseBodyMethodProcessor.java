package com.alpha.commons.core.framework;

import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.alpha.commons.core.annotation.JSON;
import com.alpha.commons.core.annotation.JSONS;

/**
 * 自定义@ResponseBody返回值处理器
 */
public class CustomRequestResponseBodyMethodProcessor extends RequestResponseBodyMethodProcessor {

	public CustomRequestResponseBodyMethodProcessor(List<HttpMessageConverter<?>> converters) {
		super(converters);
	}

	/**
	 * 重写RequestResponseBodyMethodProcessor,
	 * 当方法上有@ResponseBody注解并且没有@JSON和@JSONS注解的时候才调用Spring内置的返回值处理器(RequestResponseBodyMethodProcessor)
	 * @param returnType
	 * @return
	 */
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		boolean hasJsonAnnotation = returnType.getMethodAnnotation(JSON.class) != null;
		boolean hasJsonsAnnotation = returnType.getMethodAnnotation(JSONS.class) != null;
		if(hasJsonAnnotation == true || hasJsonsAnnotation == true) {
			return  false;
		}
		return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBody.class) ||
				returnType.hasMethodAnnotation(ResponseBody.class));
	}

}
