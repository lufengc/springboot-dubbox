/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cheng.boot.common.base;

import com.cheng.boot.common.utils.Encodes;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 数据Entity类
 *
 * @author lufengc
 * @version 2017/2/28
 */
public abstract class DataEntity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = 1L;

    protected String remarks;    // 备注
    protected String createBy;    // 创建者
    protected Date createDate;    // 创建日期
    protected String updateBy;    // 更新者
    protected Date updateDate;    // 更新日期
    protected String delFlag;    // 删除标记（0：正常；1：删除；2：审核）

    public DataEntity() {
        super();
        this.delFlag = DEL_FLAG_NORMAL;
    }

    /**
     * 插入之前执行方法，需要手动调用
     */
    public void preInsert(String userId) {
        setId(Encodes.uuid());
        if (StringUtils.isNotBlank(userId)) {
            this.updateBy = userId;
            this.createBy = userId;
        }
        this.updateDate = new Date();
        this.createDate = this.updateDate;
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    public void preUpdate(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            this.updateBy = userId;
        }
        this.updateDate = new Date();
    }

    public DataEntity(String id) {
        super(id);
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
