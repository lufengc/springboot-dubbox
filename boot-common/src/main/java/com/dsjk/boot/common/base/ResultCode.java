package com.dsjk.boot.common.base;

/**
 * 返回结果常量
 *
 * @author fengcheng
 * @version 2017/3/14
 */
public enum ResultCode {

    SUCCESS("0", "处理成功"),
    FAILD("10000", "处理失败"),
    FAILD_PARAM("20000", "参数错误"),

    INVALID_CLIENTID("30003", "Invalid clientid"),
    INVALID_PASSWORD("30004", "User name or password is incorrect"),
    INVALID_CAPTCHA("30005", "Invalid captcha or captcha overdue"),
    INVALID_TOKEN("30006", "Invalid token"),;

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
