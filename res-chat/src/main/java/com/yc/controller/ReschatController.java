package com.yc.controller;

import com.yc.bean.Reschat;
import com.yc.biz.ReschatBiz;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("reschat")
public class ReschatController {
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private ReschatBiz reschatBiz;

    @RequestMapping(value = "send",method = {RequestMethod.GET,RequestMethod.POST})
    public void sendNotification() {
        try {
            // 创建业务消息信息
            String message = "postman调用接口访问后端服务器存储数据并使用websocket将消息推送给前端客户端";

            // 全体发送
//            webSocket.sendAllMessage(message);

            // 单个用户发送 (userId为用户id)
            String username = "1";

            String message1 = "【websocket消息】 单点消息:只发送给id为"+username+"的用户。";
            webSocket.sendOneMessage(username, message1);

            // 多个用户发送 (userIds为多个用户id，逗号‘,’分隔)
//            String[] usernames = {"1", "2"};
//            String message2 = "【websocket消息】 单点消息:只发送给id为"+usernames.toString()+"的用户。";
//            webSocket.sendMoreMessage(usernames, message2);
        } catch (Exception e) {
            // 输出异常信息
            e.printStackTrace();
        }
    }

    @RequestMapping("selectUsers")
    public Map<String, Object> selectUsers(){
        Map<String,Object> map = new HashMap<>();
        List<String> users = reschatBiz.selectUsers();
        map.put("code",1);
        map.put("obj",users);
        return map;
    }
    @RequestMapping("handleUnreadMessage")
    public Map<String,Object> handleUnreadMessage(@RequestParam String toUser, @RequestParam String fromUser){
        Map<String,Object> map = new HashMap<>();
        Integer result = reschatBiz.handleUnreadMessage(toUser, fromUser);
        map.put("code",result);
        return map;
    }
    @RequestMapping("addChatRecord")
    public Map<String,Object> addChatRecord(@RequestParam String content,@RequestParam String fromuser,@RequestParam String touser,
                                            @RequestParam String type,@RequestParam Integer readed){
        Map<String,Object> map = new HashMap<>();
        reschatBiz.addChatRecord(content, fromuser, touser, type, readed);
        map.put("code",1);
        return map;
    }

    @RequestMapping("selectRecord")
    public Map<String,Object> selectRecord(@RequestParam String toUser,@RequestParam String fromUser){
        Map<String,Object> map = new HashMap<>();
        List<Reschat> records = reschatBiz.selectRecord(toUser, fromUser);
        if (records.size() != 0){
            map.put("code",1);
            map.put("obj",records);
            return map;
        }
        map.put("code",0);
        map.put("msg","没有找到相关聊天记录");
        return map;
    }

    @RequestMapping("selectAll")
    public Map<String,Object> selectAll(){
        Map<String,Object> map = new HashMap<>();
        List<Reschat> records = reschatBiz.selectAll();
        if (records.size() != 0){
            map.put("code",1);
            map.put("obj",records);
            return map;
        }
        map.put("code",0);
        map.put("msg","没有找到相关聊天记录");
        return map;
    }

    @RequestMapping("getUnreadCount")
    public Map<String,Object> getUnreadCount(@RequestParam String fromUser, @RequestParam String toUser){
        Map<String,Object> map = new HashMap<>();
        map.put("code",1);
        map.put("obj",reschatBiz.getUnreadCount(fromUser, toUser));
        return map;
    }

}
