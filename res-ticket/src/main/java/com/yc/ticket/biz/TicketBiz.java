package com.yc.ticket.biz;

import com.yc.bean.Ticket;
import com.yc.bean.TicketUser;
import java.util.List;


public interface TicketBiz {

    /**
     * 两个方法都是发布优惠券
     */
    public Boolean addTicket(double fullmoney,double money, int ticketcount,String type, String starttime,String endtime);
    /**
     * 抢票成功之后,修改表中的数据
     */
    public Boolean updateTicket(Double getMoney, Integer tno);

    /**
     * 若金额为零,或优惠券数为0则结束活动
     */
    public Boolean over(Integer tno);
//    public Boolean overView(Integer tno);

    /**
     * 获取现在正在进行中的优惠券活动
     */
    public Ticket selectTicket();

    /**
     *  将优惠券存入
     */
    public Boolean addToTicketUser(Integer uno,Integer tno,Integer fullmoney,Double money,String type);

    /**
     * 将所有的优惠券查询出来
     */
    public List<Ticket> findAll();


}
