package com.dsjk.boot.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.base.Result;
import com.dsjk.boot.common.base.ResultCode;
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

    @ApiOperation(value="获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "/getBySql", method = RequestMethod.GET)
    public Result getBySql(String id) {
        User user = userService.getUserBySql(id);
        return Result.of(user);
    }

    @ApiOperation(value="获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(String id) {
        User user = userService.get(id);
        return Result.of(user);
    }

    @ApiOperation(value="获取用户分页")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Result getPage(User user) {
        PageInfo<User> page = userService.getPage(user);
        return Result.of(page);
    }

    @ApiOperation(value="创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(User user) {
        userService.save(user);
        return Result.of(ResultCode.SUCCESS);
    }

    @ApiOperation(value="删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(String id) {
        userService.delete(new User());
        return Result.of(ResultCode.SUCCESS);
    }
}
