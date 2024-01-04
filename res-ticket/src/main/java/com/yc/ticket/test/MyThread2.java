package com.yc.ticket.test;

import java.util.ArrayList;
import java.util.Collections;

/*
有一个抽奖池,该抽奖池中存放了奖励的金额,该抽奖池中的奖项为 {10,5,20,50,100,200 ,500,800,2,80,300,700};
创建两个抽奖箱(线程)设置线程名称分别为"抽奖箱1”。“抽奖箱2.
随机从抽奖池中获取奖项元素并打印在控制台上,格式如下:
每次抽出一个奖项就打印一个(随机)
抽奖箱1又产生了一个10元大奖
抽奖箱1又产生了一个 100元大奖
抽奖箱1又产生了一个 288元大奖
抽奖箱1又产生了一个800元大奖
抽奖箱2又产生了一个 780元大奖
 */
public class MyThread2 extends Thread{
    ArrayList<Integer> list;
    public MyThread2(ArrayList<Integer> list){
        this.list = list;
    }

    // 线程一
    static ArrayList<Integer> list1 = new ArrayList<>();
    //线程二
    static ArrayList<Integer> list2 = new ArrayList<>();

    @Override
    public void run() {
        // 1.循环
        // 2.同步代码块
        // 3.判断
        // 4.判断

        while (true) {
            synchronized (MyThread2.class){
                if (list.size() == 0) {
                    if ("抽奖箱1".equals(getName())){
                        System.out.println("抽奖箱1"+list1+",最大奖项为 "+Collections.max(list1));
                    }else {
                        System.out.println("抽奖箱2"+list2+",最大奖项为 "+Collections.max(list2));
                    }
                    System.out.print("抽奖结束，最大奖项出自");
                    String result = Collections.max(list1)>Collections.max(list2)?"抽奖箱1":"抽奖箱2";
                    System.out.print(result);
                    break;
                }else{
                    // 继续抽奖
                    // 随机打乱list中的顺序
                    Collections.shuffle(list);
//                    Integer integer = list.get(0);
//                    list.remove(0);
                    // 上面两个操作可以转换为如下操作
                    int prize = list.remove(0);
                    if ("抽奖箱1".equals(getName())){
                        list1.add(prize);
                    }else {
                        list2.add(prize);
                    }
                    System.out.println(getName()+"产生了一个"+prize+"元大奖");
                }
            }
            try {
                // 是两个线程明显的平均
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        //创建奖池
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list,10,5,20,50,100,200 ,500,800,2,80,300,700);

        //创建线程
        MyThread2 t1 = new MyThread2(list);
        MyThread2 t2 = new MyThread2(list);

        //设置名字
        t1.setName("抽奖箱1");
        t2.setName("抽奖箱2");

        //启动线程
        t1.start();
        t2.start();
    }
}
