package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resorder;

import com.yc.bean.Resorderitem;
import com.yc.bean.Resuser;
import com.yc.dao.ResorderMapper;
import com.yc.dao.ResorderitemMapper;
import com.yc.web.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.SUPPORTS,
        isolation = Isolation.DEFAULT,
        timeout = 2000,
        readOnly = false,
        rollbackFor = RuntimeException.class)
@Slf4j
public class ResorderBizImpl implements ResorderBiz {
    @Autowired
    private ResorderMapper resorderMapper;
    @Autowired
    private ResorderitemMapper resorderitemMapper;

    @Override
    public int order(Resorder resorder, Set<CartItem> cartItemSet, Resuser resuser) {
        resorder.setUserid(resuser.getUserid());
        this.resorderMapper.insert(resorder);
        for (CartItem cartItem : cartItemSet) {
            Resorderitem resorderitem = new Resorderitem();
            resorderitem.setRoid(resorder.getRoid());//roid
            resorderitem.setFid(cartItem.getFood().getFid());//id
            resorderitem.setNum(cartItem.getNum());//num
            resorderitem.setDealprice(cartItem.getSmallCount());//dealprice
            resorderitemMapper.insert(resorderitem);
        }
        return 0;
    }

    @Override
    public List<Resorder> findAll() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("roid");
        wrapper.notIn("status", 1, 3, 4);//0待发货 2待退款 1已发货 3已收货 4退款成功
        return resorderMapper.selectList(wrapper);
    }

    @Override
    public int updateByRoid(Integer roid) {
        QueryWrapper wrapper = new QueryWrapper();
        Resorder resorder = new Resorder();
        resorder.setStatus(1);//订单发货
        wrapper.eq("roid", roid);
        return resorderMapper.update(resorder, wrapper);
    }

    @Override
    public int drawback(Integer roid) {
        QueryWrapper wrapper = new QueryWrapper();
        Resorder resorder = new Resorder();
        resorder.setStatus(4);//给用户退款
        wrapper.eq("roid", roid);
        return resorderMapper.update(resorder, wrapper);
    }

    @Override
    public Page findByUid(Integer userid, int pageno, int pagesize, String sortby, String sort) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("userid", userid);
        wrapper.eq("status", 3);//是这个用户且订单已经签收完成
        if (sort.equalsIgnoreCase("desc")) {
            wrapper.orderByDesc(sortby);
        } else {
            wrapper.orderByAsc(sortby);
        }
        // 设置分页信息, 查第3页, 每页2条数据
        Page<Resorder> page = new Page<>(pageno, pagesize);
        Page page1 = resorderMapper.selectPage(page, wrapper);
        // 执行分页查询
        // 获取分页查询结果
        return page1;
    }

    @Override
    public List<Map<String, Object>> findOrder(Integer fid, String year) {
        return resorderMapper.findOrder(fid, year);
    }

    @Override
    public List<String> findMonths() {
        return resorderMapper.findMonths();
    }

    @Override
    public List<Map<String, Object>> findMoney(String year) {
        return resorderMapper.findMoney(year);
    }

    @Override
    public List<Map<String, Object>> findOldAll(Integer Pageno,Integer PageSize,String the_time,Integer userid) {

        return resorderMapper.findOldAll(Pageno,PageSize,the_time,userid);
    }

    @Override
    public void delete_orderOldBy_roid(Integer roid) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("roid",roid);
        Resorder resorder=new Resorder();
        resorder.setStatus(0);
        resorderMapper.update(resorder,wrapper);
    }

    @Override
    public List<Map<String, Object>> findOldAll1(String the_time, Integer userid) {
        return resorderMapper.findOldAll1(the_time,userid);
    }

    @Override
    public Integer updataByRoidStar(Integer roid, Integer star) {
        Resorder resorder = new Resorder();
        resorder.setRoid(roid);
        resorder.setStars(star.toString());
        return resorderMapper.updateById(resorder);
    }

}
