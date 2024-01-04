package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Reschat;
import com.yc.dao.ReschatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReschatBizImpl implements ReschatBiz{
    @Autowired
    ReschatMapper reschatMapper;
    @Override
    public List<Reschat> findByUsers(String toUser, String fromUser) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("touser",toUser);
        wrapper.eq("fromuser",fromUser);

        return reschatMapper.selectList(wrapper);
    }

    @Override
    public Integer addChatRecord(String content, String fromuser, String touser, String type, Integer readed) {
        return reschatMapper.addChatRecord(content, fromuser, touser, type, readed);
    }


    @Override
    public List<String> selectUsers() {
        return reschatMapper.selectUsers();
    }

    @Override
    public Integer handleUnreadMessage(String toUser, String fromUser) {
        return reschatMapper.handleUnreadMessage(toUser, fromUser);
    }

    @Override
    public List<Reschat> selectRecord(String toUser, String fromUser) {
        return reschatMapper.selectRecord(toUser, fromUser);
    }

    @Override
    public List<Reschat> selectAll() {
        return reschatMapper.selectAll();
    }

    @Override
    public Integer getUnreadCount(String fromUser, String toUser) {
        return reschatMapper.getUnreadCount(fromUser, toUser);
    }
}
