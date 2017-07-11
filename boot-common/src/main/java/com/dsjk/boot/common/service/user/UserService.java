package com.dsjk.boot.common.service.user;

import com.dsjk.boot.common.base.Result;
import com.dsjk.boot.common.bean.user.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
public interface UserService {
    Result get(String id);

    Result getList(User user);

    Result getPage(User user);

    Result save(User user);

    Result delete(String ids);
}
