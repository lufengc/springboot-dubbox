package com.dsjk.boot.common.service.user;

import com.dsjk.boot.common.bean.user.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
public interface UserService {
    User get(String id);

    List<User> getList(User user);

    PageInfo<User> getPage(User user);

    void save(User user);

    void delete(String ids);
}
