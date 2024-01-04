package com.yc.ticket.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.TicketUser;
import com.yc.ticket.dao.TicketUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TicketUserBizImpl implements TicketUserBiz{
    @Autowired
    private TicketUserMapper ticketUserMapper;
    @Override
    public List<TicketUser> findByUser(Integer uno) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("uno",uno);
        return ticketUserMapper.selectList(wrapper);
    }

    @Override
    public List<TicketUser> findByTicket(Integer tno) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("tno",tno);
        return ticketUserMapper.selectList(wrapper);
    }

    @Override
    public Integer useCount(Integer tno) {
        return ticketUserMapper.useCount(tno);
    }

    @Override
    public Double useMoney(Integer tno) {
        return ticketUserMapper.useMoney(tno);
    }
    @Override
    public List<Map<String, Object>> findByUno(Integer uno) {
        return ticketUserMapper.findByUno(uno);
    }

    @Override
    public List<Map<String, Object>> findByNo(Integer no) {
        return ticketUserMapper.findByNo(no);
    }

    @Override
    public Integer deleteByNo(Integer no) {
        return ticketUserMapper.deleteById(no);
    }


}
