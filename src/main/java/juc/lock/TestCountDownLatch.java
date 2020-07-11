package main.java.juc.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 李嘉
 * @create 2020-05-24-21:17
 */
public class TestCountDownLatch {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        AtomicInteger i = new AtomicInteger(6);
        for (int j = 0; j < 6; j++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "---" + i.getAndDecrement());
                countDownLatch.countDown();
            }, String.valueOf(j)).start();

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(countDownLatch.toString());
        System.out.println(Thread.currentThread().getName() + "===end");


        System.out.println("==============================");
        /**
         * countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
         是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，计数器的值就-1，当计数-器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了

         */
        TestPrint testPrint = new TestPrint();
        CountDownLatch countDownLatchA = new CountDownLatch(1);
        CountDownLatch countDownLatchB = new CountDownLatch(1);
        CountDownLatch countDownLatchC = new CountDownLatch(1);
        new Thread(() -> {
            testPrint.printA();
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatchA.countDown();
        }, "线程1").start();

        new Thread(() -> {
            try {
                countDownLatchA.await(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testPrint.printB();
            countDownLatchB.countDown();

        }, "线程2").start();

        new Thread(() -> {
            try {
                countDownLatchB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testPrint.printC();
            countDownLatchC.countDown();

        }, "线程3").start();


    }


}

class TestPrint {
    public void printA() {
        System.out.println(Thread.currentThread().getName() + "---A");
    }

    public void printB() {
        System.out.println(Thread.currentThread().getName() + "---B");
    }

    public void printC() {
        System.out.println(Thread.currentThread().getName() + "---C");
    }
}