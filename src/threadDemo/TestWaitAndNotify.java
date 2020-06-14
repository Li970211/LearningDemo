package threadDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 李嘉
 * @create 2020-05-08-21:03
 * <p>
 * wait():一旦执行该方法，当前线程就会堵塞状态，并释放同步监视器（锁）
 * notify():一旦执行该方法，就会唤醒被wait的一个线程，如果是多个，则唤醒优先级高的线程
 * notifyAll():一旦执行该方法，就会唤醒所有被wait的线程
 * wait(),notify(),notifyAll()三个方法必须使用在同步代码块或者同步方法中
 * wait(),notify(),notifyAll()三个方法必须是同步代码块或者同步方法中的同步监视器（锁）
 */

public class TestWaitAndNotify {
    public static void main(String[] args) {
        Number number = new Number();
        System.out.println(number + "---");
        Thread t1 = new Thread(number, "线程1");
        Thread t2 = new Thread(number, "线程2");
        t1.start();
        t2.start();

    }
}

class Number implements Runnable {

    private int i = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    @Override
    public void run() {
        while (true) {
            //加锁
            lock.lock();
            try {

                condition.signal();
                if (i < 100) {
                    i++;
                    System.out.println(Thread.currentThread().getName() + "---" + i);
                } else {
                    break;
                }
                condition.await();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //解锁
                lock.unlock();

            }

        }
    }
}


//    @Override
//    public void run() {
//        while (true){
//            synchronized (this) {
//                System.out.println(this);
//                notify();
//                if(i < 100){
//                    i++;
//                    System.out.println(Thread.currentThread().getName()+"---"+i);
//                }else{
//                    break;
//                }
//                try {
//                    wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }