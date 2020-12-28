package cn.zefre.concurrent;

import org.junit.Test;

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
     *
     * @author pujian
     * @date 2020/12/8 15:37
     */
    @Test
    public void testBasic() throws InterruptedException {
        int rounds = 0;
        while(rounds++ < 3) {
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
     *
     * @author pujian
     * @date 2020/12/9 13:39
     */
    @Test
    public void testHorseMatch() throws ExecutionException, InterruptedException {

        // 初始化
        List<Horse> horses = new ArrayList<>(core);
        for (int i = 1; i <= core; i++)
            horses.add(new Horse("horse" + i));
        final int winStep = 80;
        barrier = new CyclicBarrier(core, null);

        while(true) {
            TimeUnit.MILLISECONDS.sleep(800);
            System.out.println("bound: " +getStr(winStep, "="));
            List<Future<?>> futures = new ArrayList<>(core);
            horses.forEach(horse -> {
                Future<?> future = executor.submit(() -> {
                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    horse.setSteps(horse.getSteps() + getSteps());
                });
                futures.add(future);
            });
            // 等待执行完毕
            for (Future<?> f : futures) {
                f.get();
            }
            // 打印轨迹
            for (Horse horse : horses) {
                System.out.println(horse.getName() + ":" + getStr(horse.getSteps(), "*"));
            }
            // 结果判断
            for(Horse horse : horses) {
                if(horse.getSteps() >= winStep) {
                    System.out.println(horse.getName() + " won!");
                    System.out.println(horse.getName() + " total steps is " + horse.getSteps());
                    return;
                }
            }
            futures.clear();
        }
    }

    Random random = new Random();
    private int getSteps() {
        return random.nextInt(10);
    }

    private String getStr(int nums, String symbol) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums; i++) {
            sb.append(symbol);
        }
        return sb.toString();
    }

    private static class Horse {
        private String name;
        private int steps;

        public Horse(String name) {
            this.name = name;
        }
        public Horse(String name, int steps) {
            this.name = name;
            this.steps = steps;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getSteps() {
            return steps;
        }
        public void setSteps(int steps) {
            this.steps = steps;
        }
    }
}
