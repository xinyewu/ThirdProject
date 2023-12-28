package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.bean.Resuser;
import com.yc.dao.ResuserMapper;
import com.yc.web.model.MyPageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//因为这个业务类中所有的方法都有find，所有事物配置为readOnly
@Slf4j
public class ResuserBizImpl implements ResuserBiz{
    @Autowired
    private ResuserMapper resuserMapper;

    @Override
    public Resuser findByName(String name) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("username",name);
        Resuser resuser = resuserMapper.selectOne(wrapper);
        return resuser;
    }

    @Override
    public Resuser findByName(String name, String password) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("username",name);
        wrapper.eq("pwd",password);
        Resuser resuser = resuserMapper.selectOne(wrapper);
        return resuser;
    }

    @Override
    public Resuser findById(Integer userid) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("userid",userid);
        Resuser resuser = resuserMapper.selectOne(wrapper);
        return resuser;
    }

    @Override
    public List<Resuser> findAll() {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.orderByAsc("userid");
        return resuserMapper.selectList(wrapper);
    }
    @Transactional(readOnly = false)
    @Override
    public int upStatus(Integer userid, String username, String pwd) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("userid",userid);
        wrapper.eq("username",username);
        wrapper.eq("pwd",pwd);
        Resuser resuser=new Resuser();
        resuser.setStatus("1"); //0 正常  1 拉黑
        return resuserMapper.update(resuser,wrapper);
    }

}
