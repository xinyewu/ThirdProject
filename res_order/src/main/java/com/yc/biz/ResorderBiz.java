package com.yc.biz;

import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.web.model.CartItem;

import java.util.List;
import java.util.Set;

public interface ResorderBiz {
    //resorder 订单信息  cartItemSet 购物信息，这是一个Set resuser 当下用户
    public int order(Resorder resorder, Set<CartItem> cartItemSet, Resuser resuser);
    //查询全部订单(待发货 待退款)
    public List<Resorder> findAll();
    //下订单 则删除resorder 和 resorderitem表中的数据
    public int updateByRoid(Integer roid);
    //给用户退款
    public int drawback(Integer roid);
}
