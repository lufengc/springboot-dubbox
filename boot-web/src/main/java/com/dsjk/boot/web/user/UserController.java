package com.dsjk.boot.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.bean.user.SysUser;
import com.dsjk.boot.common.service.user.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/sql/{id}", method = RequestMethod.GET)
    public SysUser getBySql(@PathVariable String id) {
        return userService.getUserBySql(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SysUser get(@PathVariable String id) {
        return userService.get(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public SysUser get(SysUser user) {
        return userService.get(user);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<SysUser> getList(SysUser user) {
        return userService.getList(user);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageInfo<SysUser> getPage(SysUser user) {
        return userService.getPage(user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(SysUser user) {
        userService.save(user);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(SysUser user) {
        userService.delete(user);
    }
}
