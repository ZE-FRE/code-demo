package cn.zefre.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author pujian
 * @date 2022/9/28 15:06
 */
public class CountDownLatchTest {

    private ExecutorService executor = Executors.newFixedThreadPool(4);

    @Test
    public void testSingleWaiter() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + " finished its work");
            latch.countDown();
        };
        executor.execute(() -> {
            for (int i = 0; i < 3; i++) {
                executor.execute(task);
                try {
                    TimeUnit.MILLISECONDS.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        String mainThreadName = Thread.currentThread().getName();
        System.out.println(mainThreadName + " is waiting for the signal that all workers has finished its work");
        latch.await();
        System.out.println(mainThreadName + " has received the signal");
    }

    @Test
    public void testMultiWaiter() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + " is waiting for the signal...");
            try {
                latch.await();
                System.out.println(Thread.currentThread().getName() + " has received the signal");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 4; i++) {
            executor.execute(task);
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("======= " + Thread.currentThread().getName() + " is sending the signal =======");
        latch.countDown();
        TimeUnit.SECONDS.sleep(1);
    }

}
