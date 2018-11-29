package com.alpha.commons.web;

import com.alpha.commons.exception.CommonException;
import com.alpha.commons.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice(basePackages = {"com.alpha.user.controller", "com.alpha.self.diagnosis.controller"})
public class ControllerEnhancerment {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({Exception.class})
    public ResponseMessage processException(HttpServletRequest request, Exception ex) {
        logger.error("", ex);
        return WebUtils.buildResponseMessage(ResponseStatus.EXCEPTION, ex.getMessage());
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseMessage processServiceException(HttpServletRequest request, ServiceException ex) {
        logger.error("", ex);
        return WebUtils.buildResponseMessage(ex.getResultEnum());
    }
}
