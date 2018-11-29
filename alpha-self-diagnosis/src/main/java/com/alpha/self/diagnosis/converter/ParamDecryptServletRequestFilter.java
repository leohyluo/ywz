/**
 * Project Name: 智慧药师大众版
 * File Name: ParamDecryptServletRequestFilter.java
 * Package Name: com.zhys.server.filter
 * Date: 2016年4月7日上午11:30:51
 * Copyright (c) 2016, 深圳智慧药师股份有限公司  All Rights Reserved.
 */
package com.alpha.self.diagnosis.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * TODO
 */
@WebFilter(urlPatterns = "/*")
public class ParamDecryptServletRequestFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParamDecryptServletRequestFilter.class);

    private String paramName = "";

    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        paramName = filterConfig.getInitParameter("needDecryptParamName");
        LOGGER.info("需要解密的参数字段 --> {}", paramName);

        servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
//        if (!Objects.equals(Boolean.TRUE, servletContext.getInitParameter(Constant.ENABLEPARAMENCRYPT))) {
//            filterChain.doFilter(request, response);
//        } else {
//            filterChain.doFilter(new CustomHttpServletRequest((HttpServletRequest) request, paramName), response);
//        }
        try {
            filterChain.doFilter(new CustomHttpServletRequest((HttpServletRequest) request), response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
