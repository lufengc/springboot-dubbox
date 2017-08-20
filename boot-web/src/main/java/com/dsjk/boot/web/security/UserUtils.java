package com.dsjk.boot.web.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author fengcheng
 * @version 2017/8/15
 */
@Component
public class UserUtils {


    @Reference(group = Global.DUBBO_GROUP)
    private static UserService userService;

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
