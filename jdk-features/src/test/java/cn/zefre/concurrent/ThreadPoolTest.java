package cn.zefre.concurrent;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pujian
 * @date 2021/1/13 17:09
 */
public class ThreadPoolTest {

    static class MyThreadFactory implements ThreadFactory {
        private AtomicInteger atomic = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            String namePrefix = "demo-pool-thread-";
            String threadName = namePrefix + atomic.getAndIncrement();
            System.out.println("新建线程" + threadName);
            return new Thread(r, threadName);
        }
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " start run!");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(Thread.currentThread().getName() + " run complete!");
        }
    }

    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(4);
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4,
            500L, TimeUnit.MICROSECONDS, queue, new MyThreadFactory());

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 15; i++) {
            System.out.println("  queue size = " + queue.size() + "  ");
            try {
                threadPool.submit(new Task());
            } catch (RejectedExecutionException e) {
                System.err.println("the thread pool is full");
                System.out.println(e.getMessage());
            } finally {
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }

        System.out.println("当前线程数：" + threadPool.getPoolSize());
        threadPool.allowCoreThreadTimeOut(true);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("当前线程数：" + threadPool.getPoolSize());
        /*
         * test shutdown
         */
        threadPool.submit(new Task());
        threadPool.shutdown();

        TimeUnit.SECONDS.sleep(4);
    }

    @Test
    public void testException() {
        Assertions.assertEquals(0, threadPool.getActiveCount());

        threadPool.execute(() -> {
            System.out.println("开始执行任务");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int a = 1 / 0;
        });

        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(1, threadPool.getActiveCount());

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(threadPool.getActiveCount());

    }

    /**
     * 测试任务失败时，运行当前任务的线程会退出，并新建一个线程
     *
     * @author pujian
     * @date 2023/1/16 17:55
     */
    @Test
    public void testTaskFailed() {
        threadPool.execute(() -> {
            int a = 1 / 0;
        });
    }

}

