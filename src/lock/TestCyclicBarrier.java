package lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author 李嘉
 * @create 2020-05-30-14:54
 *
 * CyclicBarrier的字面意思是可循环(Cyclic)使用的屏障（Barrier).它要做的事是
 * 让一组线程到达一个屏障（也叫同步点）时被堵塞，直到最后一个线程到达屏障时，屏障
 * 才会开门，所有被屏障拦截的线程才会继续干活，线程进入屏障通过CyclicBarrier的
 * await()方法，好比我们开会，需要等所有人都到齐会议才开始
 *
 * CyclicBarrier与CountDownLatch的区别
*至此我们难免会将CyclicBarrier与CountDownLatch进行一番比较。这两个类都可以实现一组线程在到达某个条件之前进行等待，它们内部都有一个计数器，当计数器的值不断的减为0的时候所有阻塞的线程将会被唤醒。

有区别的是CyclicBarrier的计数器由自己控制，而CountDownLatch的计数器则由使用者来控制，在CyclicBarrier中线程调用await方法不仅会将自己阻塞还会将计数器减1，而在CountDownLatch中线程调用await方法只是将自己阻塞而不会减少计数器的值。

另外，CountDownLatch只能拦截一轮，而CyclicBarrier可以实现循环拦截。一般来说用CyclicBarrier可以实现CountDownLatch的功能，而反之则不能，例如上面的赛马程序就只能使用CyclicBarrier来实现。总之，这两个类的异同点大致如此，至于何时使用CyclicBarrier，何时使用CountDownLatch，还需要读者自己去拿捏。

 */
public class TestCyclicBarrier {
    public static void main(String[] args) {
        //  public CyclicBarrier(int parties, Runnable barrierAction) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10,()->{
            System.out.println("人员到齐，开始开会");
        });

        for (int i = 1; i <=10 ; i++) {
            int finalI = i;
            new Thread(()->{
                System.out.println("第"+ finalI +"个同事到达===");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }
}


