package threadDemo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 李嘉
 * @create 2020-05-09-10:24
 */
public class TestLock
{
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        Thread thread1 = new Thread(ticket,"窗口1");
        Thread thread2 = new Thread(ticket,"窗口2");
        Thread thread3 = new Thread(ticket,"窗口3");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
class Ticket implements Runnable{

    private int tick = 100;

    private ReentrantLock lock = new ReentrantLock();
    @Override
    public void run() {
        while (true){
//            synchronized (this){
            lock.lock();
            try {
                if(tick > 0){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"卖出："+tick--);
                }else{
                    break;
                }
            } finally {
                lock.unlock();
            }
//            }

        }

    }
}
/**

 在使用阻塞等待获取锁的方式中，必须在try代码块之外，并且在加锁方法与try代码块之间没有任何可能抛出异常的方法调用，
 避免加锁成功后，在finally中无法解锁。
 说明一：如果在lock方法与try代码块之间的方法调用抛出异常，那么无法解锁，造成其它线程无法成功获取锁。
 说明二：如果lock方法在try代码块之内，可能由于其它方法抛出异常，导致在finally代码块中，unlock对未加锁的对象解锁，
 它会调用AQS的tryRelease方法（取决于具体实现类），抛出IllegalMonitorStateException异常。
 说明三：在Lock对象的lock方法实现中可能抛出unchecked异常，产生的后果与说明二相同。 java.concurrent.LockShouldWithTryFinallyRule.rule.desc

 Positive example：
 Lock lock = new XxxLock();
 // ...
 lock.lock();
 try {
 doSomething();
 doOthers();
 } finally {
 lock.unlock();
 }



 Negative example：
 Lock lock = new XxxLock();
 // ...
 try {
 // If an exception is thrown here, the finally block is executed directly
 doSomething();
 // The finally block executes regardless of whether the lock is successful or not
 lock.lock();
 doOthers();

 } finally {
 lock.unlock();
 }


 */
