package main.java.juc.lock;

import java.util.concurrent.CountDownLatch;

public class Compter {
    public static int sum = 0;// 存储1加到100的数
    public static CountDownLatch count = new CountDownLatch(4);// 闭锁，计数器设置为4

    static class ComputeThread extends Thread {// 内部类
        int start, end;// 起始与结束

        public ComputeThread(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {// 每个线程都进行累加
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            System.out.println(currentThread().getName() + ":" + sum);
            count.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 建立4个线程
        ComputeThread c1 = new Compter.ComputeThread(1, 25);
        ComputeThread c2 = new Compter.ComputeThread(26, 50);
        ComputeThread c3 = new Compter.ComputeThread(51, 75);
        ComputeThread c4 = new Compter.ComputeThread(76, 100);
        // 启动4个线程
        c1.start();
        c2.start();
        c3.start();
        c4.start();
        // 让调用线程停止，等待计数器为0
        count.await();
        System.out.println(sum);
    }
}