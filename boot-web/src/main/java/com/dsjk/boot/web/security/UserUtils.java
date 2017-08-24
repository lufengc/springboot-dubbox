package com.dsjk.boot.web.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.bean.user.Menu;
import com.dsjk.boot.common.bean.user.Role;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
import com.dsjk.boot.web.config.SpringContextHolder;
import com.google.common.collect.Lists;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
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
        User user = userService.getUserByLoginName(loginName);
        if (user == null) {
            return null;
        }
        user.setRoleList(userService.getRoleByUserId(user.getId()));
        return user;
    }

    /**
     * 获取当前登录者ID
     *
     * @return 当前用户id
     */
    public static String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        return jwtUser.getId();
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    public static User getUser() {
        return userService.get(getUserId());
    }

    /**
     * 获取当前用户角色列表
     *
     * @return 角色列表
     */
    public static List<Role> getRoleList() {
        return userService.getRoleByUserId(getUserId());
    }

    /**
     * 获取当前用户授权菜单
     *
     * @return 权限菜单列表
     */
    public static List<Menu> getMenuList() {
        List<Menu> menuList = userService.getMenuByUserId(getUserId());
        List<Menu> list = Lists.newArrayList();
        Menu.sort(menuList);
        Menu.sortList(list, menuList, Menu.getRootId(), true);
        return list;
    }

}
