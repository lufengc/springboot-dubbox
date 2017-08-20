/*
 * Copyright (c) 2017. <a href="http://www.lufengc.com">lufengc</a> All rights reserved.
 */

package com.dsjk.boot.common.base;

/**
 * 参数异常
 *
 * @author lufengcheng
 * @version 2016-01-15 09:56:22
 */
public class ParamException extends BaseException {

    private static final long serialVersionUID = 1L;

    {
        this.setSubClass(this.getClass());
    }

    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
