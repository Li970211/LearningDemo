package main.java.juc.threadDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 李嘉
 * @create 2020-05-09-9:44
 */

/**
 * 实现callable接口，相比起runable接口的方式，方法可以返回结果，并且可以抛出异常
 * 需要FutureTask实现类的支持，用于接收运算结果，FutureTask是Future接口的实现类
 */
public class TestCallable
{
    public static void main(String[] args) {

        NumberAdd numberAdd = new NumberAdd();

        //执行callable方式，需要FutureTask实现类的支持，用于接收运算结果
        FutureTask<Integer> task = new FutureTask<>(numberAdd);
        Thread thread = new Thread(task);
        thread.start();

        try {
            //接收线程运算的后的结果，需要等待线程执行完成后，在执行这个获取结果的方法，FutureTask可以闭锁
            Integer integer = task.get();
            System.out.println("结果为："+integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
class NumberAdd implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int a = 0;
        for (int i = 0; i < 1000; i++) {
            a += i;

        }
        return a;
    }
}
