package main.java.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 李嘉
 * @create 2020-05-24-14:54
 */
public class TestSpinLock {

    //原子引用，初始为null
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        System.out.println(Thread.currentThread().getName()+"---进入myLock()");
        /**
         * 开始线程进入时，原子引用初始值为null，将其改成当前线程的引用，即获取了锁；
         *下一个线程进来，发现期望值为获取锁的线程，不是当前线程，compareAndSet就会返回false，
         * 就会一直循环，直到获取锁的线程解锁（即原子引用更新为null）,compareAndSet返回true,
         * 不进入循环
         **/
        while (!atomicReference.compareAndSet(null,Thread.currentThread())){

        }
        System.out.println(Thread.currentThread().getName()+"---获取锁");
    }

    public void myUnLock(){
        System.out.println(Thread.currentThread().getName()+"---进入myUnLock()");
        //如果当前原子引用为当前线程，则将原子引用更新为null,即解锁
        atomicReference.compareAndSet(Thread.currentThread(),null);
    }

    public static void main(String[] args) {
        TestSpinLock testSpinLock = new TestSpinLock();

        new Thread(()->{
            testSpinLock.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testSpinLock.myUnLock();
        },"线程A").start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            testSpinLock.myLock();
            testSpinLock.myUnLock();
        },"线程B").start();
    }

}
