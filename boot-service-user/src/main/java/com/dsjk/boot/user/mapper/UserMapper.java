package com.dsjk.boot.user.mapper;

import com.dsjk.boot.common.bean.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Mapper
@Repository
public interface UserMapper extends CommonMapper<User> {

}
