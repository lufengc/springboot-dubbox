package com.dsjk.boot.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
    public User getBySql(@PathVariable String id) {
        return userService.getUserBySql(id);
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User get(@PathVariable String id) {
        return userService.get(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public User get(User user) {
        return userService.get(user);
    }

    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<User> getList(User user) {
        return userService.getList(user);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageInfo<User> getPage(User user) {
        return userService.getPage(user);
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(User user) {
        userService.save(user);
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(User user) {
        userService.delete(user);
    }
}
