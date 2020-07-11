package main.java.juc.threadDemo;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 李嘉
 * @create 2020-05-03-17:58
 */
public class ABATest {
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(5,1);

    public static void main(String[] args) {
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"第一次版本号:"+atomicStampedReference.getStamp());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(5,6,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"第二次版本号:"+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(6,5,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"第三次版本号:"+atomicStampedReference.getStamp());
        },"线程1").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"第一次版本号:"+stamp);
            //暂停3秒，保证线程1完成一次ABA
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean b = atomicStampedReference.compareAndSet(5, 7, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()+"修改结果"+b+",当前版本号："+atomicStampedReference.getStamp());
            System.out.println("最终结果值为"+atomicStampedReference.getReference());

        },"线程2").start();
    }
}
