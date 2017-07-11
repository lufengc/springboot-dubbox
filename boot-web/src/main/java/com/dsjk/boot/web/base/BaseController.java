/*
 * Copyright (c) 2017. <a href="http://www.lufengc.com">lufengc</a> All rights reserved.
 */

package com.dsjk.boot.web.base;

import com.dsjk.boot.common.utils.DateUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.Objects;


/**
 * BaseController
 *
 * @author lufengcheng
 * @version 2016-01-15 09:56:22
 */
public abstract class BaseController {

    /**
     * 日志的记录
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null) {
                    if (Objects.equals(text.trim(), "")) {
                        setValue("");
                    } else {
                        setValue(StringEscapeUtils.escapeHtml4(text.trim()));
                    }
                }
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : null;
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? DateUtils.formatDateTime((Date) value) : "";
            }
        });
    }

}
