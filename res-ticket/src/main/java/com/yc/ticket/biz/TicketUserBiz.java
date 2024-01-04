package com.yc.ticket.biz;

import com.yc.bean.TicketUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TicketUserBiz {
    /**
     * 查询该用户所有的优惠券
     */
    public List<TicketUser> findByUser(Integer uno);

    /**
     * 查看每张优惠券的使用情况
     *
     * @param tno 优惠券编号
     */
    public List<TicketUser> findByTicket(Integer tno);

    // TODO 获取使用的数量和金额
    public Integer useCount(Integer tno);

    public Double useMoney(Integer tno);
    //罗杰镕
    public List<Map<String,Object>> findByUno(@Param("uno")Integer uno);
    public List<Map<String,Object>>findByNo(@Param("no")Integer no);
    public Integer deleteByNo(@Param("no")Integer no);

}
