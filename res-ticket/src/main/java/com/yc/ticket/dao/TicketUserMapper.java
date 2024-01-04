package com.yc.ticket.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.TicketUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TicketUserMapper extends BaseMapper<TicketUser> {
    @Select("select count(*) from resticket_user where state=1 and tno=#{tno}")
    public Integer useCount(@Param("tno") Integer tno);
    @Select("select sum(money) from resticket_user where state=1 and tno=#{tno}")
    public Double useMoney(@Param("tno") Integer tno);

    @Select("select resticket_user.*,ticket.* from resticket_user,ticket where resticket_user.tno = ticket.tno and uno = #{uno} and NOW() BETWEEN resticket_user.gettime AND resticket_user.overtime ")
    public List<Map<String,Object>> findByUno(@Param("uno")Integer uno);
    @Select("SELECT no,uno,tno,fullmoney,money,type,state FROM resticket_user WHERE no=#{no}")
    public List<Map<String,Object>> findByNo(@Param("no")Integer no);

}
