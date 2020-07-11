package main.java.juc.prod;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 李嘉
 * @create 2020-06-07-17:07
 */
public class ProdConsumerQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        MyResource myResource = new MyResource(new LinkedBlockingQueue<String>(10));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"启动成功");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "main/java/juc/prod").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"启动成功");
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"comsumer").start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println("5s停止生产消费");
        myResource.stop();


    }
}

class MyResource {
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws Exception {
        String data = null;
        boolean result;
        while (flag) {
            data = atomicInteger.incrementAndGet() + "";
            result = this.blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (result) {
                System.out.println(Thread.currentThread().getName() + "\t 插入数据" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入数据" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);

        }

        System.out.println(Thread.currentThread().getName() + "\t 停止生产");
    }

    public void myConsumer() throws Exception {
        String data = null;
        while (flag) {
            data = this.blockingQueue.poll(2L, TimeUnit.SECONDS);
            if(null == data || "".equals(data)){
                System.out.println(Thread.currentThread().getName() + "\t 超过2s获取不到数据");
                flag = false;
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费数据" + data + "成功");

        }
    }

    public void stop() {
        this.flag = false;
    }
}
