package main.java.juc.threadDemo;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 李嘉
 * @create 2020-05-03-16:22
 */
public class AtomicIntegerTest {
    public static void main(String[] args) {


        AtomicInteger atomicInteger = new AtomicInteger(3);
        atomicInteger.getAndIncrement();
        System.out.println(atomicInteger.get());
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        System.out.println(atomicBoolean.get());

        boolean b = atomicBoolean.compareAndSet(false, true);
        System.out.println("cas比较结果为"+b+"--此时值是"+atomicBoolean.get());
        atomicBoolean.getAndSet(false);
        System.out.println(atomicBoolean.get());

    }
}
