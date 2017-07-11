package com.dsjk.boot.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.base.Result;
import com.dsjk.boot.common.base.ResultCode;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
import com.dsjk.boot.web.base.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    @Reference(group = Global.DUBBO_GROUP)
    private UserService userService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(String id) throws Exception {
        return userService.get(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result getList(User user) throws Exception {
        return userService.getList(user);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Result getPage(User user) throws Exception {
        return userService.getPage(user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(User user) throws Exception {
        return userService.save(user);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(String ids) throws Exception {
        return userService.delete(ids);
    }
}
