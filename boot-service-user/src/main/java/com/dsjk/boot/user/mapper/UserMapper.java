package com.dsjk.boot.user.mapper;

import com.dsjk.boot.common.bean.user.Menu;
import com.dsjk.boot.common.bean.user.Role;
import com.dsjk.boot.common.bean.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Mapper
@Repository
public interface UserMapper extends CommonMapper<User> {

    List<Role> selectRoleByUserId(String userId);

    List<Menu> selectMenuByUserId(String userId);
}
