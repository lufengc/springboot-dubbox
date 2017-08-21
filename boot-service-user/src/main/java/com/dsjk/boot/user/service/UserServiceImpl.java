package com.dsjk.boot.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.base.Result;
import com.dsjk.boot.common.base.ResultCode;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
import com.dsjk.boot.common.utils.Encodes;
import com.dsjk.boot.user.config.BeanValidator;
import com.dsjk.boot.user.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Service(interfaceClass = UserService.class, group = Global.DUBBO_GROUP)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User get(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByLoginName(String loginName) {
        User user = new User();
        user.setLoginName(loginName);
        return userMapper.selectOne(user);
    }

    @Override
    public Result getPage(User user) {
        PageHelper.startPage(user.getPageNum(), user.getPageSize() != 0 ? user.getPageSize() : Global.PAGE_SIZE);
        List<User> users = userMapper.selectByExample(new Example(User.class));
        PageInfo<User> page = new PageInfo<>(users);
        return Result.of(page);
    }

    @Override
    @Transactional
    public Result register(User user) {
        BeanValidator.beanValidator(user);
        user.setId(Encodes.uuid());
        user.setPassword(Encodes.encryptPassword(user.getPassword()));
        user.setUpdateDate(new Date());
        user.setCreateDate(user.getUpdateDate());
        userMapper.insertSelective(user);
        return Result.of(ResultCode.SUCCESS);
    }

    @Override
    public Result updateUser(User user) {
        BeanValidator.beanValidator(user);
        user.setUpdateDate(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        return Result.of(ResultCode.SUCCESS);
    }

    @Override
    public Result forgetPassword(User user) {
        user.setPassword(Encodes.encryptPassword(user.getPassword()));
        user.setUpdateDate(new Date());
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("loginName", user.getLoginName());
        userMapper.updateByExampleSelective(user, example);
        return Result.of(ResultCode.SUCCESS);
    }


}
