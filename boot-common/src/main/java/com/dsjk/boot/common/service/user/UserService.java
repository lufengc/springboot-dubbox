package com.dsjk.boot.common.service.user;

import com.dsjk.boot.common.base.Result;
import com.dsjk.boot.common.bean.user.User;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
public interface UserService {
    User get(String id);

    User getUserByLoginName(String loginName);

    Result getPage(User user);

    Result register(User user);

    Result updateUser(User user);

    Result forgetPassword(User user);
}
