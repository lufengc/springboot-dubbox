package com.dsjk.boot.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.base.Result;
import com.dsjk.boot.common.base.ResultCode;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
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
public class UserController {

    @Reference(group = Global.DUBBO_GROUP)
    private UserService userService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(String id) throws Exception {
        User user = userService.get(id);
        return Result.of(user);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result getList(User user) throws Exception {
        List<User> list = userService.getList(user);
        return Result.of(list);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Result getPage(User user) throws Exception {
        PageInfo<User> page = userService.getPage(user);
        return Result.of(page);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(User user) throws Exception {
        userService.save(user);
        return Result.of(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(String ids) throws Exception {
        userService.delete(ids);
        return Result.of(ResultCode.SUCCESS);
    }
}
