package com.alpha.self.diagnosis.converter;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alpha.commons.core.framework.CustomJSONReturnValueHandler;

/**
 * Created by xc.xiong on 2017/9/21.
 */
@Configuration
@ComponentScan(basePackages = {"com.alpha.commons.core.framework"},useDefaultFilters = true)
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Resource
    MyStringHttpMessageConverter myStringHttpMessageConverter;
    
    List<HttpMessageConverter<?>> converters;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(new CustomMethodArgumentResolver(converters));
        argumentResolvers.add(new RequestJsonParamMethodArgumentResolver());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(myStringHttpMessageConverter);
        this.converters = converters;

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new RequestParamConvertInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
    
    /**
     * 添加自定义的返回值处理器
     */
    @Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
    	System.out.println("add JsonReturnHandler to Spring HandlerMethodReturnValueHandler");
    	returnValueHandlers.add(new CustomJSONReturnValueHandler());
	}

}
