package com.yc.biz;

import com.yc.bean.Resfood;
import com.yc.web.model.MyPageBean;

import java.util.List;

public interface ResfoodBiz {
    public List<Resfood> findAll();

    public Resfood findById(Integer fid);

    //mybatis-plus自带的分页组件Page...
    public MyPageBean findByPage(int pageno, int pagesize, String sortby, String sort);

    public Integer addResfood(Resfood food);
    //下架菜品根据菜品fid
    public int deleteResfood(Integer fid);
    //更新菜品
    public Integer upResFood(Resfood resfood);
}
