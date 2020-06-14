package threadDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author 李嘉
 * @create 2020-06-07-21:49
 */
public class TestCallable2 {
    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        new Thread(futureTask,"线程1").start();
        new Thread(futureTask,"线程2").start();
        //只会打印一次  线程1come in callable
    }
}
class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"come in callable");
        return null;
    }
}