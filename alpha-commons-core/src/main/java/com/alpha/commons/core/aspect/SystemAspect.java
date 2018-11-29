package com.alpha.commons.core.aspect;

import com.alibaba.fastjson.JSON;
import com.alpha.commons.core.annotation.Repeat;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 日志管理
 * 使用方式：@logAnnotation(operationType="操作类型",operationName="操作内容")
 *
 * @author yangbin
 */

@Aspect
@Component
public class SystemAspect {

    private static final Logger logger = LoggerFactory.getLogger(SystemAspect.class);

    //Controller层切点
    @Pointcut("execution (* com.alpha.his.controller..*.*(..))")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()&&@annotation(repeat)")
    public void doBefore(JoinPoint joinPoint, Repeat repeat) {
        if (null != repeat) {
            //获取 joinPoint 的全部参数
            Object[] args = joinPoint.getArgs();
            Object[] re = getRequestAndResponse(args, joinPoint);
            HttpServletRequest request = (HttpServletRequest) re[0];
            if (null != request) {
                HttpSession session = request.getSession();
                String url = request.getRequestURI();
                String json = "";
                try {
                    json = JSON.toJSONString(args[1]);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                String newToken = DigestUtils.md5Hex(json);
                String oldToken = (String) session.getAttribute(url);
                if (StringUtils.isNotBlank(oldToken) && oldToken.equalsIgnoreCase(newToken)) {
                    throw new RuntimeException("repeatException");
                } else {
                    session.setAttribute(url, newToken);
                }
            }
        }
    }

    private Object[] getRequestAndResponse(Object[] args, JoinPoint joinPoint) {
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        for (int i = 0; i < args.length; i++) {
            //获得参数中的 request && response
            if (args[i] instanceof HttpServletRequest) {
                request = (HttpServletRequest) args[i];
            }
            if (args[i] instanceof HttpServletResponse) {
                response = (HttpServletResponse) args[i];
            }
        }
        return new Object[]{request, response};
    }

    //配置controller环绕通知,使用在方法aspect()上注册的切入点
    @Around("controllerAspect()")
    public Object around(JoinPoint joinPoint) {
        ResponseMessage rm;
        try {
            return ((ProceedingJoinPoint) joinPoint).proceed();
        } catch (Throwable e) {
            logger.error(getMethodOfController(joinPoint), e);
            if ("repeatException".equalsIgnoreCase(e.getMessage())) {
                rm = WebUtils.buildResponseMessage(ResponseStatus.DATA_DUPLICATION, e.getMessage());
            }else{
                rm = WebUtils.buildResponseMessage(ResponseStatus.EXCEPTION, e.getMessage());
            }
        }
        return rm;
    }

    private String getMethodOfController(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
    }


    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @After(value = "controllerAspect()") //&& @annotation(logAnnotation)
    public void after(JoinPoint joinPoint) {//LogAnnotation logAnnotation

    }

    //配置后置返回通知,使用在方法aspect()上注册的切入点
    @AfterReturning(returning = "rvt", value = "controllerAspect()&&@annotation(repeat)")
    public void afterReturn(JoinPoint joinPoint, Object rvt,Repeat repeat) {
        if(null!=repeat){
            ResponseMessage rm = (ResponseMessage) rvt;
            if (1000 != rm.getCode() && 218 != rm.getCode()) {
                Object[] args = joinPoint.getArgs();
                Object[] re = getRequestAndResponse(args, joinPoint);
                HttpServletRequest request = (HttpServletRequest) re[0];
                request.getSession(false).removeAttribute(request.getRequestURI());
            }
        }
    }
}
