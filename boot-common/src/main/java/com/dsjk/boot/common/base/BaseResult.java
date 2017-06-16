package com.dsjk.boot.common.base;


import java.io.Serializable;

/**
 * 结果返回基类
 *
 * @author lufengc
 * @version 2016/12/26
 */
public class BaseResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code = ResultCode.SUCCESS.getCode();
    private String message = ResultCode.SUCCESS.getMessage();

    public BaseResult() {
    }

    public BaseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
