package com.cheng.boot.common.service.user;

import com.cheng.boot.common.base.Result;
import com.cheng.boot.common.bean.user.Menu;
import com.cheng.boot.common.bean.user.Role;
import com.cheng.boot.common.bean.user.User;

import java.util.List;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
public interface UserService {
    User get(String id);

    List<Role> getRoleByUserId(String userId);

    List<Menu> getMenuByUserId(String userId);

    User getUserByLoginName(String loginName);

    Result getPage(User user);

    Result register(User user);

    Result updateUser(User user);

    Result forgetPassword(User user);
}
