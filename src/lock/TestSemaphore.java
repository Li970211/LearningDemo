package lock;

import java.util.concurrent.Semaphore;

/**
 * @author 李嘉
 * @create 2020-05-30-16:16
 */
public class TestSemaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"====抢到车位");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName()+"====离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
