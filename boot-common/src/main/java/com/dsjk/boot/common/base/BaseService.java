package com.dsjk.boot.common.base;

import com.dsjk.boot.common.utils.IdWorker;
import com.dsjk.boot.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author fengcheng
 * @version 2017/3/26
 */
@Service
public abstract class BaseService<T extends DataEntity<T>> implements BaseServiceI<T> {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected Mapper<T> mapper;

    @Override
    public T get(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T get(T entity) {
        return mapper.selectOne(entity);
    }

    @Override
    public List<T> getList(T entity) {
        return mapper.select(entity);
    }

    @Override
    public PageInfo<T> getPage(T entity) {
        PageHelper.startPage(entity.getPageNum(), entity.getPageSize());
        List<T> list = mapper.select(entity);
        return new PageInfo<>(list);
    }

    @Override
    public int save(T entity) {
        if (StringUtils.isEmpty(entity.getId())) {
            entity.setId(IdWorker.getId() + "");
            return mapper.insertSelective(entity);
        } else {
            return mapper.updateByPrimaryKeySelective(entity);
        }
    }

    @Override
    public int delete(T entity) {
        return mapper.delete(entity);
    }
}
