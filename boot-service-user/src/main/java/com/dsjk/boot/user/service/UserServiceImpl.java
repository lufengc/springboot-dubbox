package com.dsjk.boot.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.base.Result;
import com.dsjk.boot.common.base.ResultCode;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
import com.dsjk.boot.common.utils.Encodes;
import com.dsjk.boot.common.utils.StringUtils;
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
    public Result get(String id) {
        return Result.of(userMapper.selectByPrimaryKey(id));
    }

    @Override
    public Result getList(User user) {
        return Result.of(userMapper.select(user));
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
    public Result save(User user) {
        if (!Global.beanValidator(user)) {
            return Result.of(ResultCode.FAILD_PARAM);
        }
        if (StringUtils.isNotEmpty(user.getId())) {
            user.preUpdate(user.getId());
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            user.setId(Encodes.uuid());
            user.setUpdateBy(user.getId());
            user.setCreateBy(user.getId());
            user.setUpdateDate(new Date());
            user.setCreateDate(user.getUpdateDate());
            userMapper.insertSelective(user);
        }
        return Result.of(ResultCode.SUCCESS);
    }

    @Override
    @Transactional
    public Result delete(String ids) {
        for (String id : ids.split(",")) {
            userMapper.deleteByPrimaryKey(id);
        }
        return Result.of(ResultCode.SUCCESS);
    }

    @Override
    public User getUserByLoginName(String loginName) {
        User user = new User();
        user.setLoginName(loginName);
        return userMapper.selectOne(user);
    }
}
