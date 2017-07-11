package com.dsjk.boot.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author fengcheng
 * @version 2017/7/11
 */
public class JSONUtils {

    public static Object JSONFilter(Object object) {
        PropertyFilter filter = (ob, key, value) -> !"remarks,createBy,createDate,updateBy,updateDate,delFlag".contains(key);
        String s = JSON.toJSONString(object, filter, SerializerFeature.WriteMapNullValue);
        SerializeConfig config = SerializeConfig.getGlobalInstance();
        config.addFilter(object.getClass(), filter);
        return JSON.parse(s);
    }
}
