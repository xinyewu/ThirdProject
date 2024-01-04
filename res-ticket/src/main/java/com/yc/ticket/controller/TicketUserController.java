package com.yc.ticket.controller;

import com.yc.ticket.biz.TicketUserBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("resticket")
public class TicketUserController {
    @Autowired
    private TicketUserBiz ticketUserBiz;
    @RequestMapping("findByUser")
    public Map<String,Object> findByUser(@RequestParam Integer uno){
        Map<String,Object> map = new HashMap<>();
        if(ticketUserBiz.findByUser(uno) != null){
            map.put("code",1);
            map.put("obj",ticketUserBiz.findByUser(uno));
            return map;
        }
        map.put("code",0);
        return map;
    }
    @RequestMapping("findByTicket")
    public Map<String,Object> findByTicket(@RequestParam Integer tno){
        Map<String,Object> map = new HashMap<>();
        if(ticketUserBiz.findByTicket(tno) != null){
            map.put("code",1);
            map.put("obj",ticketUserBiz.findByTicket(tno));
            return map;
        }
        map.put("code",0);
        return map;
    }
    @RequestMapping("useCountMoney")
    public Map<String,Object> useCountMoney(@RequestParam Integer tno){
        Map<String,Object> map = new HashMap<>();
        Integer useCount = ticketUserBiz.useCount(tno);
        Double useMoney = ticketUserBiz.useMoney(tno) == null ? 0: ticketUserBiz.useMoney(tno);
        map.put("code",1);
        map.put("useCount", useCount);
        map.put("useMoney",useMoney);
        return map;
    }
    @RequestMapping("findByUno")
    public Map<String,Object> findByUno(@RequestParam Integer uno){
        Map<String,Object>map  = new HashMap<>();
        if(!ticketUserBiz.findByUno(uno).isEmpty()){
            map.put("code",1);
            map.put("data",ticketUserBiz.findByUno(uno));
        }else {
            map.put("code",0);
        }
        return map;
    }
    @RequestMapping("findByNo")
    public Map<String,Object>findByNo(@RequestParam Integer no){
        Map<String,Object>map  = new HashMap<>();
        if(!ticketUserBiz.findByNo(no).isEmpty()){
            map.put("code",1);
            map.put("data",ticketUserBiz.findByNo(no));
        }else {
            map.put("code",0);
        }
        return map;
    }
    @RequestMapping("deleteByNo")
    public Integer deleteByNo(@RequestParam Integer no){
        if(ticketUserBiz.deleteByNo(no)!=0){
            return 1;
        }else {
            return 0;
        }
    }

}
