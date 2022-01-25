package cn.zefre.concurrent.lock;

import org.junit.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pujian
 * @date 2020/6/16 15:57
 */
public class ReentrantLockTest {

    private final Lock lock = new ReentrantLock();

    /**
     * 线程池
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(3);


    @Test
    public void testLock() {
        executorService.execute(() -> {
            Thread currentThread = Thread.currentThread();
            try{
                currentThread.interrupt();
                lock.lock();
                System.out.println(currentThread.getName() + "中断状态为：" + currentThread.isInterrupted());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试lock.lockInterruptibly();抛出异常
     */
    @Test
    public void testTryInterrupt() {
        executorService.execute(() -> {
            try {
                Thread.currentThread().interrupt();
                System.out.println("中断状态值为：" + Thread.currentThread().isInterrupted());
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("lockInterruptibly()方法清除了中断状态。中断状态值为：" + Thread.interrupted());
            }
        });

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试在指定时间内尝试获取锁
     * 抛出异常，将不会释放锁，必须手动lock.unlock()释放锁
     */
    @Test
    public void testTryLock(){
        System.out.println("开始任务！");
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                        System.out.println(Thread.currentThread().getName() + "执行了！");
                        Thread.sleep(1000);
                        int t = 3/0;
                    } else {
                        System.out.println(Thread.currentThread().getName() + "获取锁超时！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.unlock();
                    } catch (IllegalMonitorStateException e) {
                        System.out.println("释放锁失败，线程没有获得锁！");
                    }
                }
            });
        }

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *lock()方法与lockInterruptibly()方法的区别在于中断，lock()方法会一直等待获取锁
     * 而lockInterruptibly()方法在调用之前线程中断状态为true或调用过程中被其他线程中断，则抛出异常
     */


    /**
     * 测试ReentrantLock类的其他方法
     * 从测试结果可以看出：
     * 1、可重入锁，获取了几次锁，就要释放几次
     * 2、condition.await()会释放锁，从任务二调用这段代码后，任务三成功执行可以看出，如果这个方法没释放锁，则任务三获取不到锁
     */
    @Test
    public void testAnotherMethod() {
        final ReentrantLock rlock = new ReentrantLock(true);
        // 绑定一个Condition
        final Condition condition = rlock.newCondition();

        // 执行任务一
        executorService.execute(() -> {
            String currentThreadName = Thread.currentThread().getName();
            try {
                System.out.println("当前锁是否是公平锁？====" + rlock.isFair());
                rlock.tryLock();
                rlock.tryLock();
                rlock.tryLock();
                rlock.tryLock();
                System.out.println(currentThreadName + "保持锁定次数：" + rlock.getHoldCount());
                System.out.println(currentThreadName + "目前是否在等待获取锁？====" + rlock.hasQueuedThread(Thread.currentThread()));
                System.out.println(currentThreadName + "是否保持锁定？====" + rlock.isHeldByCurrentThread());
                System.out.println("锁定是否被任意线程保持？====" + rlock.isLocked());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rlock.unlock();
                rlock.unlock();
                rlock.unlock();
                rlock.unlock();
            }
        });
        // 执行任务二
        executorService.execute(() -> {
            try {
                if(rlock.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println("任务二获取锁成功！");
                    System.out.println("任务二await");
                    condition.await();
                } else{
                    System.out.println("任务二未获取到锁！");
                }
                System.out.println("任务二执行完毕！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rlock.unlock();
            }
        });
        // 执行任务三
        executorService.execute(() -> {
            String currentThreadName = Thread.currentThread().getName();
            System.out.println("正在获取此锁定的线程数：" + rlock.getQueueLength());
            rlock.lock();
            System.out.println(currentThreadName + "目前是否在等待获取锁？====" + rlock.hasQueuedThread(Thread.currentThread()));
            System.out.println("目前是否有线程在等待condition?====" + rlock.hasWaiters(condition));
            System.out.println("目前正在等待condition的线程数：" + rlock.getWaitQueueLength(condition));

            condition.signal();
            rlock.unlock();
        });

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *测试condition.await()方法是否修改了线程中断状态
     * 从测试结果可以看出，并没有修改线程中断状态
     */
    @Test
    public void testInterrupt() {
        final Condition condition = lock.newCondition();
        Thread thread = new Thread(() -> {
            try {
                System.out.println("子线程开始执行！");
                lock.lock();
                condition.await();
                System.out.println("子线程执行完毕！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("子线程调用condition.await()方法线程中断状态为：" + thread.isInterrupted());
        lock.lock();
        condition.signal();
        lock.unlock();
    }

}
