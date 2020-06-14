package threadDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 李嘉
 * @create 2020-05-09-11:13
 */
public class TestABC {
    public static void main(String[] args) {
        ABCDemo abcDemo = new ABCDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    abcDemo.printA();
                }

            }
        }, "线程1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {

                    abcDemo.printB();
                }
            }
        }, "线程2").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {

                    abcDemo.printC();
                }
            }
        }, "线程3").start();
    }
}

class ABCDemo {
    private int i = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void printA() {

        lock.lock();
        try {
            if(i!= 1){
                condition1.await();
            }

            System.out.println(Thread.currentThread().getName() + "-----A");
            i = 2;
            condition2.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            if(i!=2){
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "-----B");
            i = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void printC() {
        lock.lock();
        try {
            if(i!=3){
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "-----C");
            i = 1;
            condition1.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

