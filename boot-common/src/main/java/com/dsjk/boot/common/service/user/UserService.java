package com.dsjk.boot.common.service.user;

import com.dsjk.boot.common.bean.user.SysUser;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
public interface UserService {
    SysUser getUserBySql(String id);
}
