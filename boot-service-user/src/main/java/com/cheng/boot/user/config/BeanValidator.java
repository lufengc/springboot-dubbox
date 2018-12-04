package com.cheng.boot.user.config;

import com.cheng.boot.common.base.ParamException;
import com.cheng.boot.common.utils.BeanValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;

/**
 * @author fengcheng
 * @version 2017/8/14
 */
public class BeanValidator {

    private static Logger logger = LoggerFactory.getLogger(BeanValidator.class);

    /**
     * 验证Bean实例对象
     */
    private static Validator validator = SpringContextHolder.getBean(Validator.class);

    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组,不传入此参数时，同@Valid注解验证
     * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中，并抛出异常
     */
    public static boolean beanValidator(Object object, Class<?>... groups) {
        try {
            BeanValidators.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            throw new ParamException(list.toString());
        }
        return true;
    }
}
