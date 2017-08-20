package com.dsjk.boot.web.security;

import com.dsjk.boot.common.base.BaseException;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
public class RequestLimitException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Request limit exception.
     */
    public RequestLimitException() {
        super("HTTP请求超出设定的限制");
    }

    /**
     * Instantiates a new Request limit exception.
     *
     * @param message the message
     */
    public RequestLimitException(String message) {
        super(message);
    }

}
