package com.alpha.commons.core.service.impl;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.commons.core.pojo.BasePojo;
import com.alpha.commons.core.service.IBaseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 通用的service
 *
 * @param <T>
 * @param <Dao>
 * @author yangbin
 */
public class BaseServiceImpl<T extends BasePojo<T>, Dao extends IBaseDao<T, Long>> implements IBaseService<T, Long> {

    @Autowired
    Dao dao;

    @Override
    public Long save(T t) {
        return dao.insertSelective(t);
    }

    @Override
    public Long modify(T t) {
        return dao.updateSelective(t);
    }

//    @Override
//    public Long remove(Long id) {
//        return dao.delete(id);
//    }
//
//    @Override
//    public Long saveByBatch(List<T> list) {
//        return dao.insertOfBatch(list);
//    }

    @Override
    public T queryById(T t) {
        List<T> list = dao.selectSelective(t);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<T> query(T t) {
        return dao.selectSelective(t);
    }

    @Override
    public PageInfo<T> queryByPage(T t) {
        PageInfo<T> pageInfo = t.getPageInfo();
        Page<T> page = getPage(pageInfo);
        List<T> pagelist = dao.selectSelective(t);
        setPageInfo(pageInfo, page, pagelist);
        return pageInfo;
    }

    private Page<T> getPage(PageInfo<T> pageInfo) {
        return PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    private void setPageInfo(PageInfo<T> pageInfo, Page<T> page, List<T> pagelist) {
        long count = page.getTotal();
        pageInfo.setList(pagelist);
        pageInfo.setTotal(count);
    }

}
