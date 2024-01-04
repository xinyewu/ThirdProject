package com.yc.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.web.model.CartItem;

import java.util.List;
import java.util.Map;
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

    //根据用户id查订单
    public Page findByUid(Integer userid, int pageno, int pagesize, String sortby, String sort);

    //根据roid查订单日期
    public List<Map<String, Object>> findOrder(Integer roid, String year);

    //查询全部年份
    public List<String> findMonths();

    //查询该年每月的金额
    public List<Map<String, Object>> findMoney(String year);

    //罗杰镕
    public List<Map<String, Object>> findOldAll(Integer Pageno, Integer PageSize, String the_time, Integer userid);

    public void delete_orderOldBy_roid(Integer roid);

    public List<Map<String, Object>> findOldAll1(String the_time, Integer userid);

    public Integer updataByRoidStar(Integer roid, Integer star);

}
