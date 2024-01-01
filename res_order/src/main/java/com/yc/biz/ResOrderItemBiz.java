package com.yc.biz;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.yc.bean.Resorderitem;
import com.yc.web.model.OrderItems;


import java.util.List;

public interface ResOrderItemBiz {
    //通过订单roid查询订单详情
    public List<OrderItem> findByRoid(Integer roid);
    //通过订单roid查询订单所有商品
    public List<OrderItems>findItemByRoid(Integer roid);

}
