package main.java.juc.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 李嘉
 * @create 2020-05-24-16:52
 */
public class TestReadWriteLock {
    public static void main(String[] args) {
        MapTest mapTest = new MapTest();
        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            new Thread(()->{
                mapTest.put(finalI+"", finalI+"");
            },"线程"+i).start();
        }

        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            new Thread(()->{
                mapTest.get(finalI+"");
            },"线程"+i).start();
        }
    }
}
class MapTest{
    private volatile Map<String,String> map = new HashMap<>();
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key,String value){
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"---正在写入:"+key);
            try {
                TimeUnit.MICROSECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"---写入完成");
        }finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public void get(String key){
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"---正在读取:"+key);
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"---读取完成:"+result);
        }finally {
            readWriteLock.readLock().unlock();
        }

    }
}