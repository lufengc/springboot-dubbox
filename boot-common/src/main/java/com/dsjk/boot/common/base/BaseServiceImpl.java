package com.dsjk.boot.common.base;

import com.dsjk.boot.common.utils.Reflections;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author fengcheng
 * @version 2017/3/26
 */
@Service
public abstract class BaseServiceImpl<T extends DataEntity<T>> implements BaseService<T> {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected Mapper<T> mapper;

    /**
     * 插入
     *
     * @param object T
     */
    @Override
    public int insert(T object) throws Exception {
        return mapper.insertSelective(object);
    }

    /**
     * 更新
     *
     * @param object T
     */
    @Override
    public void update(T object) throws Exception {
        mapper.updateByPrimaryKeySelective(object);
    }

    /**
     * 删除
     *
     * @param ids 删除的ID
     * @return 删除数量
     */
    @Override
    public int delete(String ids) throws Exception {
        String[] split = ids.split(",");
        for (String s : split) {
            mapper.deleteByPrimaryKey(s);
        }
        return split.length;
    }

    /**
     * 根据ID查询
     *
     * @param id 对象id
     * @return Object
     */
    public T get(Object id) throws Exception {
        T entity = mapper.selectByPrimaryKey(id);
        if (entity == null) {
            @SuppressWarnings("unchecked")
            Class<T> entityClass = Reflections.getClassGenricType(getClass());
            entity = entityClass.newInstance();
        }
        return entity;
    }

    /**
     * 查询列表,
     * 参数为空对象，则查询所有，如：getList(new SysUser())
     *
     * @param object 要查询的对象
     * @return List<T>
     */
    public List<T> getList(T object) throws Exception {
        List<T> list = mapper.select(object);
        if (list == null) {
            list = Lists.newArrayList();
        }
        return list;
    }

    /**
     * 分页查询
     *
     * @param object 分页对象
     * @param example Example
     * @return Page<T>
     */
    @Override
    public PageInfo<T> getPage(T object, Example example) throws Exception {
        if (example.getOredCriteria().size() > 0) {
            example.getOredCriteria().get(0).andNotEqualTo("delFlag", BaseEntity.DEL_FLAG_DELETE);
        } else {
            example.createCriteria().andNotEqualTo("delFlag", BaseEntity.DEL_FLAG_DELETE);
        }
        int pageSize = object.getPageSize() == 0 ? Global.PAGE_SIZE : object.getPageSize();
        if (pageSize != -1) {
            PageHelper.startPage(object.getPageNum(), pageSize);
        }
        List<T> list = mapper.selectByExample(example);
        PageInfo<T> page = new PageInfo<>(list);
        page.setPageNum(object.getPageNum());
        page.setPageSize(pageSize);
        return page;
    }
}
