package com.yc.biz;

import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.web.model.CartItem;

import java.util.Set;

public interface ResorderBiz {
    //resorder 订单信息  cartItemSet 购物信息，这是一个Set resuser 当下用户
    public int order(Resorder resorder, Set<CartItem> cartItemSet, Resuser resuser);
}
