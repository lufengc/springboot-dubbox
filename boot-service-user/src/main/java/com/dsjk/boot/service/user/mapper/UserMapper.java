package com.dsjk.boot.service.user.mapper;

import com.dsjk.boot.common.base.CommonMapper;
import com.dsjk.boot.common.bean.user.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author fengcheng
 * @version 2017/2/28
 */
@Repository
public interface UserMapper extends CommonMapper<SysUser> {

    SysUser getUserBySql(String id);
}
