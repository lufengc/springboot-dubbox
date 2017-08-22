package com.dsjk.boot.common.service.user;

import com.dsjk.boot.common.base.Result;
import com.dsjk.boot.common.bean.user.Menu;
import com.dsjk.boot.common.bean.user.Role;
import com.dsjk.boot.common.bean.user.User;

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
