package com.yc.ticket.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.TicketUser;
import com.yc.ticket.dao  .ResticketMapper;
import com.yc.bean.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketBizImpl implements TicketBiz{
    @Autowired
    private ResticketMapper resticketMapper;
    int ticketCount = 0;

    @Override
    public Boolean addTicket(double fullmoney,double money, int ticketcount,String type, String starttime,String endtime) {
        return resticketMapper.addTicket(fullmoney, money, ticketcount, type, starttime, endtime);
    }

    @Override
    public Boolean updateTicket(Double getMoney, Integer tno) {
        return resticketMapper.updateTicket(getMoney, tno);
    }


    @Override
    public Boolean over(Integer tno) {
        return resticketMapper.over(tno);
    }

    @Override
    public Ticket selectTicket() {
        return resticketMapper.selectTicket();
    }

    @Override
    public Boolean addToTicketUser(Integer uno, Integer tno, Integer fullmoney, Double money, String type) {
        return resticketMapper.addToTicketUser(uno, tno, fullmoney, money, type);
    }

    @Override
    public List<Ticket> findAll() {
        return resticketMapper.findAll();
    }

}
