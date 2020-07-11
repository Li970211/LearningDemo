package main.java.juc.prod;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 李嘉
 * @create 2020-05-31-20:24
 * 题目：一个初始值为0的变量，两个线程对其交替操作，一个+1，一个-1，来五轮
 * <p>
 * 1.线程操作资源类
 * 2.判断 干活 通知
 * 3.防止虚假唤醒机制
 *
 *
 * Synchronized和lock的区别，使用新的lock有什么好处，举例说明
 * 1.原始构造
 * synchronized是关键字，属于JVM层面；
 *              使用monitorenter和monitorexit（底层是通过monitor对象来完成
 *              其wait/notify等方法也依赖于monitor对象，只有在同步代码块或者同步方法中才能调用wait/notify等方法
 *
 *              反编译synchronized底层中有一个monitorenter,两个monitorexit,一个monitorexit是正常退出解锁，另外一个是异常情况下解锁
 *  Lock是具体类（java.util.concurrent.Locks.lock）jdk1.5后新增，api层面的锁
 *
 *  2.使用方法
 *  synchronized不需要用户去手动释放锁，当synchronized代码执行完后系统会自动让线程释放锁的占用
 *  ReentrantLock则需要用户去手动释放锁，若没有主动释放锁，就有可能导致出现死锁的现象，
 *  需要lock和unlock()方法去配合try/finally语句来完成
 *
 *  3.等待是否可中断
 *  synchronized不可以中断，除非抛出异常或者正常执行完成
 *  ReentrantLock可中断：
 *                      1.设置超时方法tryLock(long timeout,TimeUnit unit)
 *                      2.lockInterruptibly()放代码块中，调用interrupt()方法可中断
 *
 *  4.加锁是否公平：
 *  synchronized非公平锁，ReentrantLock两者都可以，默认公平锁，构造方法可以传入boolean值，true为公平锁，false为非公平锁
 *
 *  5.锁绑定多个条件Condition
 *  synchronized没有
 *  ReentrantLock用来实现分组唤醒相应唤醒的线程，可以精确唤醒，而不是像synchronized要么随机唤醒一个线程要么唤醒全部线程
 *
 *
 */
public class ProdConsumerDemo {

    public static void main(String[] args) throws InterruptedException {
        Number number = new Number();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                number.add();
            }
        },"生产者1").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                number.add();
            }
        },"生产者2").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                number.sub();
            }
        },"消费者1").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                number.sub();
            }
        },"消费者2").start();
    }
}

class Number {
    private volatile int i = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public  void add() {
        lock.lock();
        try {
            while (i != 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            i++;
            System.out.println(Thread.currentThread().getName() + "---" + i);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    public  void sub() {
        lock.lock();
        try {
            while (i == 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            i--;
            System.out.println(Thread.currentThread().getName() + "---" + i);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

//    public synchronized void add() {
//        while (i != 0) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        i++;
//        System.out.println(Thread.currentThread().getName() + "---" + i);
//        notifyAll();
//    }
//
//    public synchronized void sub() {
//        while (i == 0) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        i--;
//        System.out.println(Thread.currentThread().getName() + "---" + i);
//        notifyAll();
//    }


}
