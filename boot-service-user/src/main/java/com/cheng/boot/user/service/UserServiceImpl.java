package com.cheng.boot.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.cheng.boot.common.base.Global;
import com.cheng.boot.common.base.Result;
import com.cheng.boot.common.base.ResultCode;
import com.cheng.boot.common.bean.user.Menu;
import com.cheng.boot.common.bean.user.Role;
import com.cheng.boot.common.bean.user.User;
import com.cheng.boot.common.service.user.UserService;
import com.cheng.boot.common.utils.Encodes;
import com.cheng.boot.user.config.BeanValidator;
import com.cheng.boot.user.mapper.UserMapper;
import com.cheng.datasource.TargetDataSource;
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
    public List<Role> getRoleByUserId(String userId) {
        return userMapper.selectRoleByUserId(userId);
    }

    @Override
    public List<Menu> getMenuByUserId(String userId) {
        return userMapper.selectMenuByUserId(userId);
    }

    @Override
    @TargetDataSource("datasource")
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
