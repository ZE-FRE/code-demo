package cn.zefre.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author pujian
 * @date 2021/6/24 13:37
 */
@Slf4j
public class ThreadTest {

    /**
     * 测试park方法是否会相应中断
     * 测试结果：
     * park会响应中断信号
     *
     * @author pujian
     * @date 2021/6/24 17:36
     */
    @Test
    public void testParkRespondInterrupt() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            log.info("t1 is being started!");
            log.info("interrupted status: " + Thread.currentThread().isInterrupted());
            LockSupport.park();
            log.info("interrupted status: " + Thread.currentThread().isInterrupted());

        }, "t1");

        t1.start();
        TimeUnit.MILLISECONDS.sleep(2000);

        t1.interrupt();
        t1.join();
    }

    private void sayHello() throws InterruptedException {
        if(Thread.interrupted()) {
            throw new InterruptedException("线程被中断！");
        }
        System.out.println("hello");
    }

    @Test
    public void testHandleInterruptedException() throws InterruptedException {
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

    /**
     * 测试在可中断方法之前调用中断方法，可中断方法是否仍然执行并捕获中断异常
     *
     * @author pujian
     * @date 2021/5/26 14:39
     */
    @Test
    public void interruptBeforeInterruptedMethod() {
        System.out.println("thread interrupted status:" + Thread.interrupted());

        Thread.currentThread().interrupt();

        System.out.println("thread interrupted status:" + Thread.currentThread().isInterrupted());

        try {
            System.out.println("the main thread will sleep 100 milli seconds");
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("thread still was interrupted");
        }
        System.out.println("thread interrupted status:" + Thread.currentThread().isInterrupted());
    }
}
