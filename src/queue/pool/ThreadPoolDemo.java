package queue.pool;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 李嘉
 * @create 2020-06-14-8:59
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //创建一个固定线程数量的线程池
        //一池5个处理线程
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //一池一个线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //一池N个线程，看情况
//        ExecutorService threadPool = Executors.newCachedThreadPool();


        //模拟10个用户处理业务，每个用户是一个来自外部的请求线程
        try{
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
