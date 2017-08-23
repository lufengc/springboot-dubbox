package com.dsjk.boot.web.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
import com.dsjk.boot.web.config.SpringContextHolder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author fengcheng
 * @version 2017/8/15
 */
@Component
public class UserUtils {


    @Reference(group = Global.DUBBO_GROUP)
    private static UserService userService;

    private static RedisTemplate<String, String> redisTemplate = SpringContextHolder.getBean("redisTemplate");

    /**
     * 校验验证码
     *
     * @return 用户信息
     */
    public static boolean checkCaptcha(String captchaCode, String captchaValue) {
        String cacheCaptchaValue = "";
        //验证码校验
        if (captchaCode != null) {
            cacheCaptchaValue = redisTemplate.opsForValue().get(captchaCode);
            redisTemplate.delete(captchaCode);
        }
        return captchaValue != null && Objects.equals(cacheCaptchaValue.toLowerCase(), captchaValue.toLowerCase());
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public static User getUserByLoginName(String loginName) {
        return userService.getUserByLoginName(loginName);
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        return userService.get(jwtUser.getId());
    }

}
