package com.yc.biz;

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
}
