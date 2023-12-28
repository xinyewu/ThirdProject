package com.yc.biz;


import com.yc.bean.Resuser;
import com.yc.web.model.MyPageBean;

import java.util.List;

public interface ResuserBiz {
    public Resuser findByName(String name);
    public Resuser findByName(String name,String password);
    public Resuser findById(Integer userid);
    public List<Resuser> findAll();
    public int upStatus(Integer userid,String username,String pwd);
}
