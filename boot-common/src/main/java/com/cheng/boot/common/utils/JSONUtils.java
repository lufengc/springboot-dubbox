package com.cheng.boot.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author fengcheng
 * @version 2017/7/11
 */
public class JSONUtils {

    public static Object JSONFilter(Object object) {
        PropertyFilter filter = (ob, key, value) -> !"remarks,createBy,createDate,updateBy,updateDate,delFlag".contains(key);
        String s = JSON.toJSONString(object, filter, SerializerFeature.WriteMapNullValue);
        return JSON.parse(s);
    }
}
