package com.alpha.commons;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 1.mybatis Mapper 基本查询
 * Created by HP on 2018/4/3.
 *
 * 2.PageHelper.startPage(pagenum,size);
 * list<T> list=mapper.select(params)
 * PageInfo<T> page =new PageInfo<>(list)</>
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
