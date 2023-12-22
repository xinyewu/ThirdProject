package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.Resfood;

public interface ResFoodMapper extends BaseMapper<Resfood> {
//    @Select()
//    public void findAll();
}
//利用动态代理根据这个接口的方法来生成一个代理对象，并将所有的方法实现