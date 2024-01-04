package com.yc.ticket.controller;

import com.yc.bean.Ticket;
import com.yc.ticket.biz.TicketBiz;
import com.yc.ticket.config.RedisKeys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@RestController
@RequestMapping("resticket")
public class TicketController {
    private static final int MAX_CONCURRENT_REQUESTS = 1;

    // 定义分布式锁的键
    private static final String LOCK_KEY = "ticket_lock";
    // 优惠券中的最小金额
    private static final double MIN = 0.01;
    @Autowired  // 注入 redisson 客户端
    private RedissonClient redissonClient;
    @Autowired
    private TicketBiz ticketBiz;


    @RequestMapping(value = "addTicket",method = {RequestMethod.POST,RequestMethod.GET})
//    @PostMapping(value = "addTicket")
    public Map<String,Object> addTicket(@RequestParam Double fullmoney,@RequestParam Double money,
                                        @RequestParam Integer ticketcount,@RequestParam String type,
                                        @RequestParam String starttime,@RequestParam String endtime){
        Map<String,Object> map = new HashMap<>();
        System.out.println("-----------------------------");
        System.out.println(fullmoney);
        System.out.println(money);
        System.out.println(ticketcount);
        System.out.println(type);
        System.out.println(starttime);
        System.out.println(endtime);
        if (ticketBiz.addTicket(fullmoney, money, ticketcount, type, starttime, endtime)){
            map.put("code",1);
            map.put("msg","发布成功");
            return map;
        }
        map.put("code",0);
        map.put("msg","发布失败");
        return map;
    }

    @Autowired
    private RedisTemplate redisTemplate;
    private final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_REQUESTS);
//    @RequestMapping("grabTicket")
//    public Map<String,Object> grab(@RequestParam String username){
//        TicketView ticket = ticketBiz.selectTicketByState();
//        Map<String,Object> map = new HashMap<>();
//        RLock rLock = redissonClient.getFairLock(LOCK_KEY);
//        try{
//            rLock.lock();
//            executorService.submit(()->{
//
//                // TODO 获取奖金余额和优惠券剩余数
//                int tno = ticket.getTno();
//                int ticketCount = ticket.getTicketCount();
//                double money = ticket.getMoney();
//                if (redisTemplate.hasKey(RedisKeys.RESUSER_GRAB_TICKET_UID_+tno+"_"+username)){
//                    map.put("code",0);
//                    map.put("msg","此次活动已经参与过了");
//                    return map;
//                }
//                synchronized (TicketController.class) {
////                    redis中是否存在该用户的抢券记录
//                    while (!redisTemplate.hasKey(RedisKeys.RESUSER_GRAB_TICKET_UID_+tno+"_"+username)) {
//                        try {
//                            semaphore.acquire(); // 获取信号量
//                            double getMoney = 0; // 每个线程抢到的金额
//                            if (ticketCount <= 0 || money <= 0) {
//                                map.put("code", 0);
//                                map.put("msg", "没有抢到券");
//                            } else {
//                                redisTemplate.opsForValue().set(RedisKeys.RESUSER_GRAB_TICKET_UID_+tno+"_"+username,true);
//                                getMoney = Double.parseDouble(String.format("%.2f", new Random().nextDouble() * (2 * (money / ticketCount))));
//                                System.out.println(username+"抢到" + getMoney);
//                                if (ticketCount == 1) {
//                                    getMoney = Math.max(MIN, money);
//                                }
//                                money -= getMoney;
////                                chance--;
//                                ticketCount--;
//                                if (ticketCount == 0 || money == 0){
//                                    ticketBiz.over(tno);
//                                }
//                                // TODO 将优惠券存入数据库并更新money的最新状态
//                                ticketBiz.updateTicketView(getMoney,tno);
//                                map.put("code", 1);
//                                map.put("msg", "运气不错，"+username+"抢到一张" + getMoney + "元的优惠券！");
//                            }
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        } finally {
//                            semaphore.release(); // 释放信号量
//                        }
//                        Thread.yield(); // 线程让步
//                    }
//                }
//            });
//        }finally {
//            rLock.unlock();
//        }
//
//        return map;
//    }
@RequestMapping("grabTicket")
public Map<String, Object> grab(@RequestParam String username,@RequestParam Integer uno) {
    // cong
    Ticket ticket = ticketBiz.selectTicket();
    Map<String, Object> map = new HashMap<>();
    RLock rLock = redissonClient.getFairLock(LOCK_KEY);
    try {
        rLock.lock();
        if (ticket == null){
            map.put("code",0);
            map.put("msg","当前没有正在进行的抢券活动");
            return map;
        }
        // 获取奖金余额和优惠券剩余数
        int tno = ticket.getTno();
        int ticketCount = ticket.getRemainticket();
        double money = ticket.getRemainmoney();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Integer fullMoney = ticket.getFullmoney();
        if (redisTemplate.hasKey(RedisKeys.RESUSER_GRAB_TICKET_UID_ + tno + "_" + username)) {
            map.put("code", 0);
            map.put("msg", "此次活动已经参与过了");
        } else {
            if (ticketCount <= 0 || money <= 0) {
                map.put("code", 0);
                map.put("msg", "没有抢到券");
            } else {
                double getMoney;
                if (ticketCount == 1) {
                    getMoney = Math.max(MIN, money);
                    ticketBiz.over(tno); // 结束活动
                } else {
                    // 模拟抢券的过程
                    getMoney = Double.parseDouble(String.format("%.2f", new Random().nextDouble() * (2 * (money / ticketCount))));
                }
                ticketBiz.updateTicket(getMoney, tno); // 更新数据库
                // 添加到个人 (压测的时候关闭)
                ticketBiz.addToTicketUser(uno,tno,fullMoney,getMoney, ticket.getType());
                map.put("code", 1);
                map.put("msg", "运气不错，" + username + "抢到一张" + getMoney + "元的优惠券！");
                map.put("obj",getMoney);
                // 将抢券记录写入 Redis
                redisTemplate.opsForValue().set(RedisKeys.RESUSER_GRAB_TICKET_UID_ + tno + "_" + username, true);
            }
        }
    } finally {
        rLock.unlock();
    }
    return map;
}

    @RequestMapping("findAll")
    public Map<String,Object> findAll(){
        Map<String,Object> map = new HashMap<>();
        List<Ticket> ticket = ticketBiz.findAll();
         if (ticket != null){
             map.put("code",1);
             map.put("obj",ticket);
             return map;
         }
         map.put("code",0);
         return map;
    }

    @RequestMapping("selectTicket")
    public Map<String,Object> selectTicket(){
        Map<String,Object> map = new HashMap<>();
        Ticket ticket = ticketBiz.selectTicket();
        if (ticket != null){
            map.put("code",1);
            map.put("obj",ticket);
            return map;
        }
        map.put("code",0);
        return map;
    }

    @RequestMapping("over")
    public Map<String,Object> over(@RequestParam Integer tno){
        Map<String,Object> map = new HashMap<>();
        if (ticketBiz.over(tno)){
            map.put("code",1);
            return map;
        }
        map.put("code",0);
        return map;
    }

    /**
     * 优惠券金额格式  保留两位小数
     * @param value
     * @return
     */
    public String format(double value) {

        return new java.text.DecimalFormat("0.00").format(value); // 保留两位小数
    }
    /**
     * 生成min到max范围的浮点数
     **/
    public String nextDouble(final double min, final double max) {
        return format(min + ((max - min) * new Random().nextDouble()));
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        使用默认线程池（ForkJoinPool.commonPool()）的方法
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("do something....");
            return "result";
        });
        // 自定义线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("do something....");
            return "result";
        }, executorService);


        //等待任务执行完成
        System.out.println("结果->" + cf1.get());
        System.out.println("结果->" + cf2.get());
    }

}

// 抢优惠券(分布式锁方案)
//    @RequestMapping("grabTicket")
//    public Map<String,Object> grab(){
//        Map<String,Object> map = new HashMap<>();
//        RLock lock = redissonClient.getLock(LOCK_KEY);
////        try{
////            lock.lock(); // 获取分布式锁
////            // 从数据库中获取当前的优惠券数和剩余金额数
////            int ticketCount = 10;
////            double money = 1000d;
////            // 在锁的保护下执行抢券的操作
////            if (ticketCount > 0){
////                // 抢券逻辑
////            }
////        } finally {
////            lock.unlock(); // 释放锁
////        }
//        class GrabThread implements Runnable{
//            @Override
//            public void run() {
//                synchronized (GrabThread.class){
//
//                }
//            }
//        }
//        return map;
//    }
// 抢优惠券（普通方案）
// 设置 5 个信号量


//        class GrabThread implements Runnable{
//            // 从数据库中获取当前的优惠券数和剩余金额数
//            int ticketCount = 10;
//            double money = 1000d;
//            int chance = 1; // 获取还能领取优惠券的次数
//            @Override
//            public void run() {
//                synchronized (GrabThread.class){
//                    while (chance > 0){
//                        try {
//                            semaphore.acquire(); // 获取信号量
//                            double getMoney = 0; // 每个线程抢到的金额
//                            if (ticketCount <= 0 ){
//                                // 抢券逻辑
//                                map.put("code",0);
//                                map.put("msg","没有抢到券");
//                            }else {
//                                getMoney = Double.parseDouble(format(nextDouble(MIN,2*(money/ticketCount))));
//                                System.out.println("抢到"+getMoney);
//                                if (ticketCount == 1){
//                                    getMoney = Math.max(MIN,money);
//                                }
//                                money -= getMoney;
//                                ticketCount--;
//                                chance--;
//                                // 将优惠券存入数据库并更新money的最新状态
//                                map.put("code",1);
//                                map.put("msg","运气不错，抢到一张"+getMoney+"元的优惠券！");
//                            }
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        } finally {
//                            semaphore.release(); // 释放信号量
//                        }
//                        Thread.yield(); // 线程让步
//                    }
//                }
//            }
//        }
