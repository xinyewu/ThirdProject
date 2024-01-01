package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.yc.bean.Resorderitem;
import com.yc.dao.ResorderitemMapper;
import com.yc.web.model.OrderItems;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS,
        isolation = Isolation.DEFAULT,
        timeout = 2000,
        readOnly = false,
        rollbackFor = RuntimeException.class)
@Slf4j
public class ResOrderItemBizImpl implements ResOrderItemBiz {
    @Autowired
    private ResorderitemMapper resorderitemMapper;

    @Override
    public List<OrderItem> findByRoid(Integer roid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("roid", roid);
        return resorderitemMapper.selectList(wrapper);
    }

    @Override
    public List<OrderItems> findItemByRoid(Integer roid) {
        return resorderitemMapper.findItemByRoid(roid);
    }

}
