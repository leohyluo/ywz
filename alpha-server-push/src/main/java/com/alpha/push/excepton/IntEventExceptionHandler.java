package com.alpha.push.excepton;

import com.lmax.disruptor.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by MR.Wu on 2018-07-19.
 */
public class IntEventExceptionHandler implements ExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handleEventException(Throwable throwable, long l, Object o) {
        logger.error("handleEventException", throwable);
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        logger.error("handleOnStartException", throwable);
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        logger.error("handleOnShutdownException", throwable);
    }
}
