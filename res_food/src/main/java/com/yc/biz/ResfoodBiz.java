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

}
