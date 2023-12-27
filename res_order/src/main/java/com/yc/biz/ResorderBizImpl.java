package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import java.util.List;
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
        wrapper.notIn("status",1,3,4);//0待发货 2待退款 1已发货 3已收货 4退款成功
        return resorderMapper.selectList(wrapper);
    }

    @Override
    public int updateByRoid(Integer roid) {
        QueryWrapper wrapper = new QueryWrapper();
        Resorder resorder=new Resorder();
        resorder.setStatus(1);//订单发货
        wrapper.eq("roid", roid);
        return resorderMapper.update(resorder,wrapper);
    }

    @Override
    public int drawback(Integer roid) {
        QueryWrapper wrapper=new QueryWrapper();
        Resorder resorder=new Resorder();
        resorder.setStatus(4);//给用户退款
        wrapper.eq("roid", roid);
        return resorderMapper.update(resorder,wrapper);
    }
}
