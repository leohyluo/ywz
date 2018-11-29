/**
 * Project Name: 智慧药师大众版
 * File Name: RequestJsonParamMethodArgumentResolver.java
 * Package Name: com.zhys.server.converter
 * Date: 2016年4月8日下午2:05:49
 * Copyright (c) 2016, 深圳智慧药师股份有限公司  All Rights Reserved.
 */
package com.alpha.self.diagnosis.converter;

import com.alpha.self.diagnosis.annotation.RequestJsonParam;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

/**
 * TODO
 *
 * @author jianghu
 * @date 2016年4月8日 下午2:05:49 <br/>
 */
public class RequestJsonParamMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestJsonParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object argument = objectMapper.readValue(webRequest.getParameter(parameter.getParameterAnnotation(RequestJsonParam.class).value()), parameter.getParameterType());
//
//		String name = Conventions.getVariableNameForParameter(parameter);
//		WebDataBinder binder = binderFactory.createBinder(webRequest, argument, name);
//
//		if (argument != null) {
//			validate(binder, parameter);
//		}
//
//		mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());

        return argument;
    }

    private void validate(WebDataBinder binder, MethodParameter parameter) throws Exception, MethodArgumentNotValidException {

        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation annot : annotations) {
            if (annot.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = AnnotationUtils.getValue(annot);
                binder.validate(hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
                BindingResult bindingResult = binder.getBindingResult();
                if (bindingResult.hasErrors()) {
                    if (isBindExceptionRequired(binder, parameter)) {
                        throw new MethodArgumentNotValidException(parameter, bindingResult);
                    }
                }
                break;
            }
        }
    }

    /**
     * Whether to raise a {@link MethodArgumentNotValidException} on validation errors.
     *
     * @param binder    the data binder used to perform data binding
     * @param parameter the method argument
     * @return {@code true} if the next method argument is not of type {@link Errors}.
     */
    private boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));

        return !hasBindingResult;
    }

}
