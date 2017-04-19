package com.dsjk.boot.common.service.user;

import com.dsjk.boot.common.base.BaseServiceI;
import com.dsjk.boot.common.bean.user.SysUser;

/**
 * @author fengcheng
 * @version 2017/4/17
 */
public interface UserService extends BaseServiceI<SysUser> {
    SysUser getUserBySql(String id);
}
