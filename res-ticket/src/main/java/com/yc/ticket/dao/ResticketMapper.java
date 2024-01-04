package com.yc.ticket.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.Ticket;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ResticketMapper extends BaseMapper<Ticket> {
    @Update("update resticket set money=#{money} where ticketname=#{ticketname}")
    public Boolean updateMoney(@Param("money") double money, @Param("ticketname") String ticketname);
    @Select("select * from resticket where ticketname=#{ticketname}")
    public Ticket findByName(@Param("ticketname") String ticketname);
    @Insert("insert into ticket values(null,#{fullmoney},#{money},#{ticketcount},#{type},#{starttime},#{endtime},#{money},#{ticketcount},1)")
    public Boolean addTicket(@Param("fullmoney") double fullmoney,@Param("money") double money,
                             @Param("ticketcount") int ticketcount,@Param("type") String type,
                             @Param("starttime") String starttime,@Param("endtime") String endtime);
//    @Insert("insert into resticket_view values(null,#{tno},#{money},#{ticketcount},1)")
//    public Boolean addTicket_view(@Param("tno") double tno,@Param("money") double money,
//                             @Param("ticketcount") int ticketcount);
    @Update("update ticket set state=0 where tno=#{tno}")
    public Boolean over(@Param("tno") Integer tno);
//    @Update("update resticket_view set state=0 where tno=#{tno}")
//    public Boolean overView(@Param("tno") Integer tno);
    @Update("update ticket set remainmoney=remainmoney-#{getMoney},remainticket=remainticket-1 where tno=#{tno}")
    public Boolean updateTicket(@Param("getMoney") Double getMoney,@Param("tno") Integer tno);
    @Select("select * from ticket where state=1")
    public Ticket selectTicket();
//    @Select("select * from resticket_view where state=1")
//    public TicketView selectTicketByState();
//    @Select("select * from ticket where state=1")
//    public Ticket selectFromTicket();
    @Insert("insert into resticket_user values(null,#{uno},#{tno},#{fullmoney},#{money},#{type},0,NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH))")
    public Boolean addToTicketUser(@Param("uno") Integer uno,@Param("tno") Integer tno,
                                   @Param("fullmoney") Integer fullmoney,
                                   @Param("money") Double money,@Param("type") String type);
    @Select("select * from ticket")
    public List<Ticket> findAll();

}
