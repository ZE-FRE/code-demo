package cn.zefre.concurrent;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pujian
 * @date 2021/1/13 17:09
 */
public class ThreadPoolTest {

    static class MyThreadFactory implements ThreadFactory {
        private AtomicInteger atomic = new AtomicInteger(1);
        private String namePrefix = "demo-pool-thread-";
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, namePrefix + atomic.getAndIncrement());
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

    @Test
    public void test() throws InterruptedException {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(4);
        ExecutorService executorService = new ThreadPoolExecutor(2, 4,
                500L, TimeUnit.MICROSECONDS, queue, new MyThreadFactory());

        /*for (int i = 0; i < 15; i++) {
            System.out.println("  queue size = " + queue.size() + "  ");
            try {
                executorService.submit(new Task());
            } catch (RejectedExecutionException e) {
                System.err.println("the thread poll is full");
            } finally {
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }*/
        /*
         * test shutdown
         */
        /*((ThreadPoolExecutor) executorService).allowCoreThreadTimeOut(true);
        executorService.submit(new Task());
        executorService.shutdown();
        // 让main线程等待自己
        Thread.currentThread().join();*/
        //TimeUnit.SECONDS.sleep(4);


        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " execute");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                Thread.currentThread().interrupt();
                sayHello();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread t1 = new Thread(runnable, "t1");
        t1.start();
        TimeUnit.MILLISECONDS.sleep(1000);
    }

    private void sayHello() throws InterruptedException {
        if(Thread.interrupted()) {
            throw new InterruptedException("线程被中断！");
        }
        System.out.println("hello");
    }


    /**
     * 测试在可中断方法之前调用中断方法，可中断方法是否仍然执行并捕获中断异常
     *
     * @author pujian
     * @date 2021/5/26 14:39
     * @return
     */
    @Test
    public void interruptBeforeInterruptedMethod() {
        System.out.println("thread interrupted status:" + Thread.interrupted());

        Thread.currentThread().interrupt();

        System.out.println("thread interrupted status:" + Thread.currentThread().isInterrupted());

        try {
            System.out.println("thread will sleep 100 milli seconds");
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("thread still was interrupted");
        }
        System.out.println("thread interrupted status:" + Thread.currentThread().isInterrupted());
    }
}
