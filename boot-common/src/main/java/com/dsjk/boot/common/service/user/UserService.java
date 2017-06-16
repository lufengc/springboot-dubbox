package com.dsjk.boot.common.service.user;

import com.dsjk.boot.common.base.BaseService;
import com.dsjk.boot.common.bean.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
@Mapper
@Repository
public interface UserService extends BaseService<User> {
    void save(User user);
}
