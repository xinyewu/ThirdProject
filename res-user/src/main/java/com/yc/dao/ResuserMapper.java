package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.Resuser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

public interface ResuserMapper extends BaseMapper<Resuser> {
    @Update("update resuser set lastonLine=NOW() where userid=#{userid}")
    Boolean updateLastOnline(@Param("userid") Integer userid);
    @Select("select lastonline from resuser where username=#{username}")
    String lastOnlineTime(@Param("username") String username);
    @Select("select count(*) from resorder,resuser where resorder.userid=resuser.userid and resuser.username=#{username}")
    Integer orderCount(@Param("username") String username);
    @Select("select ordertime from resorder,resuser where resorder.userid=resuser.userid and  resuser.username=#{username} and roid=(select MIN(roid) from resorder)")
    String firstOrderTime(@Param("username") String username);
    @Select("SELECT \n" +
            "    SUM(CASE WHEN stars >= 1 AND stars <= 2 THEN 1 ELSE 0 END) AS \"差评\",\n" +
            "    SUM(CASE WHEN stars = 3 THEN 1 ELSE 0 END) AS \"中评\",\n" +
            "    SUM(CASE WHEN stars >= 4 AND stars <= 5 THEN 1 ELSE 0 END) AS \"好评\"\n" +
            "FROM\n" +
            "    resorder,resuser where resorder.userid=resuser.userid and resuser.username=#{username}")
    public Map<String,Integer> selectUserAppraise(@Param("username") String username);

}
