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
     * @return
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
}
