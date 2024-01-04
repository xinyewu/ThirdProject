package com.yc.biz;

import com.yc.bean.Reschat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReschatBiz {
    /**
     * 获取两人之间的聊天记录
     */
    List<Reschat> findByUsers(String toUser, String fromUser);
    public Integer addChatRecord(String content,String fromuser, String touser,
                                 String type, Integer readed);

    /**
     *  在后台获取有过聊天的所有用户
     */
    List<String> selectUsers();

    /**
     * 将消息变为已读状态
     * @param toUser 我
     * @param fromUser 对方
     */
    public Integer handleUnreadMessage(String toUser,String fromUser);

    /**
     * 获取两人之间的聊天记录
     */
    public List<Reschat> selectRecord(String toUser, String fromUser);
    public List<Reschat> selectAll();

    /**
     * 获取消息未读数量
     */
    public Integer getUnreadCount(String fromUser, String toUser);
}
