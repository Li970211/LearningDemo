package lock;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 李嘉
 * @create 2020-06-30-22:54
 */
public class DeadLockDemo {

    public
    static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLock(lockA,lockB)).start();
        new Thread(new HoldLock(lockB,lockA)).start();
    }
}

class HoldLock implements Runnable{
    private String lockA;
    private String lockB;

    public HoldLock(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"---持有锁：" + lockA + "---试图获取锁："+lockB);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"---持有锁：" + lockB);
            }
        }
    }
}