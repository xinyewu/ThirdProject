package com.yc.controller;

import com.alibaba.fastjson.JSON;
import com.yc.bean.Reschat;
import com.yc.biz.ReschatBiz;
import com.yc.bean.MessageObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@RestController
@ServerEndpoint("/websocket/{username}")
@Slf4j
public class WebSocket {
    @Autowired
    private ReschatBiz reschatBiz;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 用户名
     */
    private String username;
    /**
     * 消息队列，用于对方不在线时保存未读消息
     */
    private Map<String, Queue<MessageObj>> messageQueue = new HashMap<>();

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "username") String username) {
        try {
            this.session = session;
            this.username = username;
            webSockets.add(this);
            sessionPool.put(username, session);
            // 上线时，发送未读消息数量
            int unreadMessages = messageQueue.getOrDefault(username, new LinkedList<>()).size();
            session.getBasicRemote().sendText(String.valueOf(unreadMessages));
            log.info("【websocket消息】有新的连接，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.username);
            log.info("【websocket消息】连接断开，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * 服务器收到客户端消息后调用的方法
     * 引入消息队列保存消息和未读数量
     * @param message
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        MessageObj messageObj = JSON.parseObject(message, MessageObj.class);
        String toUser = messageObj.getToUser();
        String fromUser = messageObj.getFromUser();
        String content = messageObj.getContent();
        String date = messageObj.getDate();
        messageObj.setContent(content);
        messageObj.setFromUser(fromUser);
        messageObj.setToUser(toUser);
        messageObj.setDate(date);


        // 将服务器收到的消息转发到接收方和发送方，用于更新消息界面
        // 如果对方在线，直接进行实时通信
        if(sessionPool.get(toUser) != null ){
            messageObj.setReaded(1);
            session = sessionPool.get(toUser);
            session.getBasicRemote().sendText(JSON.toJSONString(messageObj));
            session = sessionPool.get(fromUser);
            session.getBasicRemote().sendText(JSON.toJSONString(messageObj));
            log.info("【websocket消息】收到客户端来自 "+fromUser+" 的消息:" + content);
        } else {
            messageObj.setReaded(0);
            session = sessionPool.get(fromUser);
            session.getBasicRemote().sendText(JSON.toJSONString(messageObj));
            // 对方不在线，将消息保存到对方的消息队列中
            Queue<MessageObj> userMessageQueue = messageQueue.getOrDefault(toUser, new LinkedList<>());
            userMessageQueue.add(messageObj);
            messageQueue.put(toUser, userMessageQueue);
            log.info("【离线消息】将消息保存到 " + toUser + " 的消息队列中:" + content);
        }


    }

    /**
     * 发送错误时的处理
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误,原因:" + error.getMessage());
        error.printStackTrace();
    }

    // 此为广播消息
//    public void sendAllMessage(String message) {
//        log.info("【websocket消息】广播消息:" + message);
//        for (WebSocket webSocket : webSockets) {
//            try {
//                if (webSocket.session.isOpen()) {
//                    webSocket.session.getAsyncRemote().sendText(message);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    // 此为单点消息
    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息(多人)
//    public void sendMoreMessage(String[] userIds, String message) {
//        for (String userId : userIds) {
//            Session session = sessionPool.get(userId);
//            if (session != null && session.isOpen()) {
//                try {
//                    log.info("【websocket消息】 单点消息:" + message);
//                    session.getAsyncRemote().sendText(message);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }

    /**
     * 对方接收到未读消息的数量后，可以通过 http API 获取消息队列中的所有未读消息，并将其标记为已读
     */
    @GetMapping("/unreadMessages/{username}")
    public List<MessageObj> getUnreadMessages(@PathVariable String username){
        Queue<MessageObj> userMessageQueue = messageQueue.getOrDefault(username, new LinkedList<>());
        // 标记所有未读消息为已读
        List<MessageObj> unreadMessages = new ArrayList<>(userMessageQueue);
        userMessageQueue.clear();
        return unreadMessages;
    }
    private String dateFormat(){
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(now);
    }


}
