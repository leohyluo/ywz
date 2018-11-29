package com.alpha.push.excepton;

/**
 * Created by MR.Wu on 2018-07-24.
 */
public class RetryException extends Exception {

    private Object obj;

    private String msg;

    public RetryException(Object obj, String msg) {
        super(msg);
        this.obj = obj;
        this.msg = msg;
    }

    public RetryException(String message) {
        super(message);
    }
}
