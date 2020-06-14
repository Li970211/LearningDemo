package threadDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest {


    public static void main(String[] args) {
       Test test = new Test();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    test.add();
                    test.addByAtomic();
                }
            }, "线程"+i).start();

        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+"执行结束后："+test.getA());
        System.out.println(Thread.currentThread().getName()+"利用原子类执行结束后："+test.atomicInteger);

//        AtomicInteger atomicInteger = new AtomicInteger(3);
//        atomicInteger.getAndIncrement();
//        AtomicBoolean atomicBoolean = new AtomicBoolean();
//        System.out.println(atomicBoolean.get());
//
//        boolean b = atomicBoolean.compareAndSet(false, true);
//        System.out.println("cas比较结果为"+b+"--此时值是"+atomicBoolean.get());
//        atomicBoolean.getAndSet(false);
//        System.out.println(atomicBoolean.get());

    }
}

class Test {

    private volatile int a = 0;

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addByAtomic(){
        atomicInteger.getAndIncrement();
    }

    public  void add() {
        a = a + 1;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
