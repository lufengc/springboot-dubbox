package com.dsjk.boot.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.base.Result;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
import com.dsjk.boot.common.utils.StringUtils;
import com.dsjk.boot.web.base.BaseController;
import com.dsjk.boot.web.security.UserUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    @Reference(group = Global.DUBBO_GROUP)
    private UserService userService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(String id) throws Exception {
        if (StringUtils.isNotEmpty(id)) {
            return Result.of(userService.get(id));
        } else {
            return Result.of(UserUtils.getUser());
        }
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Result getPage(User user) throws Exception {
        return userService.getPage(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Result updateUser(User user) throws Exception {
        return userService.updateUser(user);
    }

}
