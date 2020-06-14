package queue.pool;

import java.util.concurrent.*;

/**
 * @author 李嘉
 * @create 2020-06-14-15:14
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 5, 1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);

        printThreadPoolStatus(threadPoolExecutor);
        for (int i = 1; i <= 10; i++) {
            Thread.sleep(270);
            System.out.println("添加任务"+i);
            threadPoolExecutor.execute(new TaskDemo());

        }

    }

    public static void printThreadPoolStatus(ThreadPoolExecutor threadPool) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("=========================");
            System.out.println("ThreadPool Size: "+ threadPool.getPoolSize());
            System.out.println("Active Threads: "+ threadPool.getActiveCount());
            System.out.println("Number of Tasks "+ threadPool.getCompletedTaskCount());
            System.out.println("Number of Tasks in Queue:"+ threadPool.getQueue().size());
            System.out.println("=========================");
        }, 0, 800, TimeUnit.MILLISECONDS);
    }
    
}
class TaskDemo implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"\t =====执行");
    }
}