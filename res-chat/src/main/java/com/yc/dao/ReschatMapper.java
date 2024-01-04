package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.Reschat;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.stream.BaseStream;
@Mapper
public interface ReschatMapper extends BaseMapper<Reschat> {
    @Select("select touser from reschat where fromuser='小萌神客服' union distinct select fromuser from reschat where touser='小萌神客服'")
    List<String> selectUsers();
    @Insert("insert into reschat values(null,#{content},#{fromuser},#{touser},#{type},NOW(),#{readed})")
    public Integer addChatRecord(@Param("content") String content,@Param("fromuser") String fromuser,@Param("touser") String touser,
                                 @Param("type") String type,@Param("readed") Integer readed);

    @Update("update reschat set readed=1 where touser=#{toUser} and fromuser=#{fromUser}")
    public Integer handleUnreadMessage(@Param("toUser") String toUser,@Param("fromUser") String fromUser);
    @Select("select * from reschat where fromUser=#{toUser} and toUser=#{fromUser}\n" +
            "UNION\n" +
            "select * from reschat where fromUser=#{fromUser} and toUser=#{toUser}\n" +
            "ORDER BY id")
    public List<Reschat> selectRecord(@Param("toUser") String toUser, @Param("fromUser") String fromUser);
    @Select("select * from reschat order by id")
    public List<Reschat> selectAll();

    @Select("select count(*) from reschat where fromUser=#{fromUser} and toUser=#{toUser} and readed=0")
    public Integer getUnreadCount(@Param("fromUser") String fromUser, @Param("toUser") String toUser);
}
