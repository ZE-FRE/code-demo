package cn.zefre;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author pujian
 * @date 2022/9/30 14:55
 */
public class LockSupportTest {

    @Test
    public void testParkAndUnpark() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            System.out.println("thread1 invoking...");
            LockSupport.park();
            System.out.println("thread1 unparked");
        }, "thread1");
        thread1.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("唤醒thread1");
        LockSupport.unpark(thread1);
        thread1.join();
    }

}
