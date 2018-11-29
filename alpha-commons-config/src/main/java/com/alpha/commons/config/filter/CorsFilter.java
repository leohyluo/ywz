package com.alpha.commons.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

@WebFilter(urlPatterns="/*")
public class CorsFilter extends OncePerRequestFilter {
	
	//private final static String ALLOWORIGIN_CORS_A = "http://127.0.0.1:8082";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Access-Control-Allow-Origin: 指定授权访问的域
        response.addHeader("Access-Control-Allow-Origin", "*");  //此优先级高于@CrossOrigin配置

        // Access-Control-Allow-Methods: 授权请求的方法（GET, POST, PUT, DELETE，OPTIONS等)
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");

        response.addHeader("Access-Control-Allow-Headers", "Content-Type, token");

        response.addHeader("Access-Control-Max-Age", "1800");//30 min
        filterChain.doFilter(request, response);
	}

}
