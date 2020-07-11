package main.java.juc.queue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author 李嘉
 * @create 2020-05-30-16:26
 * 当堵塞队列是空时，从队列中获取元素的操作将会被堵塞
 * 当堵塞队列是满时，往队列里添加元素的操作将会被堵塞
 * <p>
 * 试图从空的堵塞队列中获取元素的线程将会被堵塞，直到
 * 其他线程往空的队列中插入新的元素
 * 同样
 * 试图往已满的堵塞队列中添加新元素的线程同样也会被堵塞，直到
 * 其他的线程从列中移除一个或者多个元素或者完全清空队列后
 * 使队列重新变得空闲起来并后续新增。
 * <p>
 * <p>
 * 在多线程领域：所谓堵塞，在某些情况下会挂起线程（即堵塞），
 * 一旦条件满足，被挂起的线程又会被自动唤醒。
 * <p>
 * 使用BlockingQueue（接口）好处是我们不需要关心什么时候需要堵塞线程，
 * 什么时候需要唤醒线程，因为这一切BlockingQueue都给你一手包办了
 * <p>
 * <p>
 * 常见队列：
 * ArrayBlockingQueue：由数组结构组成的有界堵塞队列
 * LinKBlockingQueue:由链表结构组成的有界（但大小默认值为Integer.MAX_VALUE）堵塞队列
 * SynchronousQueue: 不存储元素的堵塞队列，也即单个元素的队列
 * <p>
 * BlockingQueue的核心方法
 * 方法类型      抛出异常           特殊值         堵塞            超时
 * 插入          add(e)            offer(e)       put(e)         offer(e,time,unit)
 * 移除          remove()          poll()         take()         poll(time,unit)
 * 检查          element()         peek()         不可用          不可用
 * <p>
 * 抛出异常 当堵塞队列满时，在往队列中add插入元素会抛异常IllegalStateException:Queue full
 * 当堵塞队列空时，再往队列在remove移除元素会抛NoSuchElementException
 * <p>
 * 特殊值   插入方法，成功true,失败false
 * 移除方法，成功返回出队列的元素,队列里面没有就返回null
 * <p>
 * 一直堵塞  当堵塞队列满时，生产者线程继续往队列里put元素，队列会一直堵塞线程直到put数据或者响应中断退出
 * 当堵塞队列空时，消费者线程试图从队列里take元素，队列会一直堵塞消费者线程直到队列可用。
 * SynchronousQueue没有容量，与其他BlockingQueue不同，SynchronousQueue是一个不存储元素的BlockingQueue.
 * 每一个put操作必须要等待一个take操作，否则不能继续添加元素，反之亦然。
 */
public class TestBlockQueue {
    public static void main(String[] args) {
        testSynchronousQueue();
    }


    private static void testSynchronousQueue() {
        SynchronousQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(() -> {

            System.out.println(Thread.currentThread().getName() + "---put1");
            try {
                blockingQueue.put("1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "---put2");
            try {
                blockingQueue.put("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "---put3");
            try {
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "线程1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "---" + blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "---" + blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "---" + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "线程2").start();
    }
}
