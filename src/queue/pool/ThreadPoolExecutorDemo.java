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
                new LinkedBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);

        printThreadPoolStatus(threadPoolExecutor);
        for (int i = 1; i <= 10; i++) {
            threadPoolExecutor.execute(new TaskDemo());
        }

    }

    /**
     * 监控当前线程池
     * @param threadPool
     */
    public static void printThreadPoolStatus(ThreadPoolExecutor threadPool) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("=========================");
            System.out.println("ThreadPool Size: "+ threadPool.getPoolSize());
            System.out.println("Active Threads: "+ threadPool.getActiveCount());
            System.out.println("Number of Tasks "+ threadPool.getCompletedTaskCount());
            System.out.println("Number of Tasks in Queue:"+ threadPool.getQueue().size());
            System.out.println("=========================");
        }, 0, 1, TimeUnit.SECONDS);
    }
    
}
class TaskDemo implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"\t =====执行任务");
    }
}