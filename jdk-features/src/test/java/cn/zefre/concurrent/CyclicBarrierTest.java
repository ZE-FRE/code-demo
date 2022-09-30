package cn.zefre.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author pujian
 * @date 2020/12/8 15:20
 */
public class CyclicBarrierTest {

    int core = Runtime.getRuntime().availableProcessors();

    ExecutorService executor = Executors.newFixedThreadPool(core);

    CyclicBarrier barrier = new CyclicBarrier(core, () -> System.out.println("完成一轮"));

    /**
     * @author pujian
     * @date 2020/12/8 15:37
     */
    @Test
    public void testBasic() throws InterruptedException {
        int rounds = 0;
        while (rounds++ < 3) {
            for (int i = core; i > 0; i--)
                executor.execute(() -> {
                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " invoke at time " + System.currentTimeMillis());
                });
        }

        TimeUnit.SECONDS.sleep(1);
    }

    /**
     * reset后，await的线程会捕获到{@link BrokenBarrierException}异常
     *
     * @author pujian
     * @date 2022/9/28 15:45
     */
    @Test
    public void testReset() throws InterruptedException {
        Runnable task = () -> {
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + " 等待时barrier被打破");
            }
        };
        executor.execute(task);
        executor.execute(task);
        executor.execute(task);
        TimeUnit.MILLISECONDS.sleep(500);
        barrier.reset();
        Assertions.assertFalse(barrier.isBroken());
        TimeUnit.MILLISECONDS.sleep(500);
    }

    /**
     * 超时后，超时的线程打破barrier，抛出{@link TimeoutException}异常
     * 其他await线程捕获到{@link BrokenBarrierException}异常
     *
     * @author pujian
     * @date 2022/9/28 15:55
     */
    @Test
    public void testTimeout() throws InterruptedException {
        Runnable timeoutTask = () -> {
            try {
                barrier.await(1, TimeUnit.SECONDS);
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println(Thread.currentThread().getName() + " 超时");
            }
        };
        Runnable normalTask = () -> {
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + " 等待时barrier被打破");
            }
        };
        executor.execute(normalTask);
        executor.execute(normalTask);
        executor.execute(timeoutTask);
        TimeUnit.SECONDS.sleep(2);
        // barrier被打破
        Assertions.assertTrue(barrier.isBroken());
    }

    /**
     * @author pujian
     * @date 2020/12/9 13:39
     */
    @Test
    public void testHorseMatch() throws InterruptedException, BrokenBarrierException {
        // 初始化
        List<Horse> horses = new ArrayList<>(core);
        for (int i = 1; i <= core; i++) {
            horses.add(new Horse("horse" + i));
        }
        final int winStep = 80;
        CyclicBarrier horseBarrier = new CyclicBarrier(core + 1);

        int loop = 0;
        while (true) {
            TimeUnit.MILLISECONDS.sleep(800);
            System.out.println("开始第" + loop++ + "轮奔跑");
            for (Horse horse : horses) {
                executor.execute(() -> {
                    try {
                        horseBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    // 奔跑
                    horse.steps = horse.steps + getSteps();
                });
            }
            TimeUnit.MILLISECONDS.sleep(200);
            // 开始赛马
            horseBarrier.await();
            TimeUnit.MILLISECONDS.sleep(800);
            // 结果判断
            for (Horse horse : horses) {
                if (horse.steps >= winStep) {
                    System.out.println(horse.name + " won!");
                    System.out.println(horse.name + " total steps is " + horse.steps);
                    return;
                }
            }
        }

    }

    Random random = new Random();

    private int getSteps() {
        return random.nextInt(10);
    }

    static class Horse {
        private String name;
        private int steps;

        public Horse(String name) {
            this.name = name;
        }
    }

}
