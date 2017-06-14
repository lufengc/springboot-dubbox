package com.dsjk.boot.common.service.user;

import com.dsjk.boot.common.base.BaseServiceI;
import com.dsjk.boot.common.bean.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
@Mapper
@Repository
public interface UserService extends BaseServiceI<User> {
    User getUserBySql(String id);
}
