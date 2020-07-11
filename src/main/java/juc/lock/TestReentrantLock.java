package main.java.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 李嘉
 * @create 2020-05-23-15:53
 */
public class TestReentrantLock {

    public static void main(String[] args) {
        Demo demo = new Demo();
        Thread thread1 = new Thread(demo, "线程1");
        Thread thread2 = new Thread(demo, "线程2");
        thread1.start();
        thread2.start();

    }
}
class Demo implements Runnable{

    private Lock lock = new ReentrantLock();

    public void getOne(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"-------getOne()");
            getTwo();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public  void getTwo(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"-------getTwo()");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        getOne();
    }
}
