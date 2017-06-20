package com.dsjk.boot.service.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
import com.dsjk.boot.common.utils.StringUtils;
import com.dsjk.boot.service.user.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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
    public List<User> getList(User user) {
        return userMapper.select(user);
    }

    @Override
    public PageInfo<User> getPage(User user) {
        PageHelper.startPage(user.getPageNum(), user.getPageSize() != 0 ? user.getPageSize() : Global.PAGE_SIZE);
        List<User> users = userMapper.selectByExample(new Example(User.class));
        return new PageInfo<>(users);
    }

    @Override
    @Transactional
    public void save(User user) {
        if (StringUtils.isNotEmpty(user.getId())) {
            user.preUpdate();
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            user.preInsert();
            userMapper.insertSelective(user);
        }
    }

    @Override
    @Transactional
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            userMapper.deleteByPrimaryKey(id);
        }
    }
}
