package com.dsjk.boot.common.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author fengcheng
 * @version 2017/4/19
 */
public interface BaseServiceI<T> {

    T get(String id);

    T get(T entity);

    List<T> getList(T entity);

    PageInfo<T> getPage(T entity);

    int save(T entity);

    int delete(T entity);
}
