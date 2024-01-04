package com.yc.ticket.test;

import java.util.Random;

/*
    100块钱，分成3个包，现在有5个人去抢，
    其中，红包是共享数据，5个人是5条线程
 */

public class MyThread1 extends Thread {
    // 共享数据
    static double money = 100;
    static int count = 3;
    // 最小的中奖金额
    static final double MIN = 0.01;

    @Override
    public void run() {
        // 同步代码块
        synchronized (MyThread1.class){
            // 判断是否还有红包
            if(count == 0){
                // 判断，共享数据是否到了末尾（已经到末尾）
                System.out.println(getName() + "没有抢到红包！");
            } else{
                // 判断，共享数据是否到了末尾（没有到末尾）
                // 定义一个变量，表示中奖的金额
                double prize = 0;
                if(count == 1){
                    // 表示此时是最后一个红包了，就无需随机，剩余所有的钱都是中奖金额
                    prize = money;
                }else {
                    // 表示第一次，第二次（随机）
                    Random r = new Random();
                    // 100 元 3个包  最多只能 99.98  0.01  0.01
                    double bounds = money - (count - 1) * MIN;
//                    r.nextDouble(100); JDK 17之后才能用
                    prize = r.nextDouble() * bounds;
                    // 由于 nextDouble 使用左闭右开，即有可能为0(小于MIN)，所以做如下判断
                    prize = Math.max(prize, MIN);
                }
                // 从 money 中，去掉当前中奖的金额
                money -= prize;
                // 红包个数-1
                count --;
                System.out.println(getName() + "抢到了" + prize + "的红包");
            }

        }

    }

    public static void main(String[] args) {

        // 创建线程对象
        MyThread1 t1 = new MyThread1();
        MyThread1 t2 = new MyThread1();
        MyThread1 t3 = new MyThread1();
        MyThread1 t4 = new MyThread1();
        MyThread1 t5 = new MyThread1();

        // 给线程设置名字
        t1.setName("小花花");
        t2.setName("小QQ");
        t3.setName("小丹丹");
        t4.setName("小牛牛");
        t5.setName("小思思");

        // 启动线程
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();


    }
}
