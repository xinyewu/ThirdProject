package com.yc.ticket.test;//package com.yc.ticket.test;
//
//import com.yc.ticket.entity.Ticket;
//import com.yc.ticket.entity.TicketType;
//
//import java.util.Random;
//import java.util.concurrent.Semaphore;
//
//public class ThreadTest implements Runnable{
//    // 设置 5 个信号量
//    private static final Semaphore semaphore = new Semaphore(5);
//    TicketType type = new TicketType();
//    int detailCount = 0; // 点击次数
//    Ticket ticket = new Ticket(100D,8,type.NoThresholdCoupons);
//    int ticketCount = ticket.getTicketCount(); // 优惠券数
//    double money = ticket.getMoney(); // 总金额
//    final double MIN = 0.01;
//    static double count = 0;
//    public static void main(String[] args) {
//
//        ThreadTest ticketTest = new ThreadTest();
//        new Thread(ticketTest,"小明").start();
//        new Thread(ticketTest,"老师").start();
//        new Thread(ticketTest,"大叔").start();
//        new Thread(ticketTest,"爷爷").start();
//        new Thread(ticketTest,"奶奶").start();
//        new Thread(ticketTest,"小花花").start();
//        new Thread(ticketTest,"小QQ").start();
//        new Thread(ticketTest,"小丹丹").start();
//        new Thread(ticketTest,"小牛牛").start();
//        new Thread(ticketTest,"小思思").start();
//    }
//
//    @Override
//    public void run() {
//        int chance = 1; // 每个人可以抢券的机会
//        // 同步代码块
//        synchronized (ThreadTest.class){
//            while (chance > 0){
//                try {
//                    semaphore.acquire(); // 获取信号量
//                    // 限定每个人抽取到的金额 至少是0.01
//                    double getMoney = 0;
//                    ++detailCount;
//                    if(ticketCount <= 0){
//                        System.out.println(Thread.currentThread().getName()+"没有抢到券");
//                        System.out.println(count+"元总计");
//                    }else{
//                        getMoney = nextDouble(MIN,2*(money/ticketCount));
//                        if (ticketCount == 1){
//                            getMoney = Math.max(money, MIN);
//                        }
//                        System.out.println(Thread.currentThread().getName()+"抢到了一张"+format(getMoney)+"元的"+ticket.getTicketType()+"==》"+detailCount);
//                        money -= getMoney;
//                        count += getMoney;
//                        ticketCount--;
//                        chance--;
//                    }
//
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                } finally {
//                    semaphore.release();
//                }
//                Thread.yield(); // 线程让步
//            }
//        }
//    }
//
//    /**
//     * 优惠券金额格式  保留两位小数
//     * @param value
//     * @return
//     */
//    public static String format(double value) {
//
//        return new java.text.DecimalFormat("0.00").format(value); // 保留两位小数
//    }
//
//    /**
//     * 生成min到max范围的浮点数
//     **/
//    public static double nextDouble(final double min, final double max) {
//        return min + ((max - min) * new Random().nextDouble());
//    }
//
//}
