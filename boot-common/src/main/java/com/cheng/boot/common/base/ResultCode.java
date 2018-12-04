package com.cheng.boot.common.base;

/**
 * 返回结果常量
 *
 * @author fengcheng
 * @version 2017/3/14
 */
public enum ResultCode {

    SUCCESS("200", "处理成功"),
    FAILD_PARAM("400", "参数错误"),
    FAILD_PERMISSION("401", "没有权限"),
    FAILD("500", "处理失败"),

    INVALID_PASSWORD("10001", "登录名或密码错误"),
    INVALID_CAPTCHA("10002", "无效的验证码或验证码过期"),;

    private String code;
    private String message;

    ResultCode(String code, String message) {
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
