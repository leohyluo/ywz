package com.alpha.commons.core.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 通用的service
 *
 * @param <T>
 * @author yangbin
 */
public interface IBaseService<T, K> {

    Long save(T t);

    //    Long saveByBatch(List<T> list);
    Long modify(T t);
    //    Long remove(K id);

    T queryById(T t);

    List<T> query(T t);

    PageInfo<T> queryByPage(T t);
}
