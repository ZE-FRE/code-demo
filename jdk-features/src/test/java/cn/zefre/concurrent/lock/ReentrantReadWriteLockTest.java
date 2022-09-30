package cn.zefre.concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author pujian
 * @date 2020/6/28 20:23
 */
public class ReentrantReadWriteLockTest {

    // 读写锁
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    // 读锁
    private Lock rlock = rwLock.readLock();
    // 写锁
    private Lock wlock = rwLock.writeLock();
    // 线程池
    private ExecutorService executor = Executors.newFixedThreadPool(5);


    /**
     * 测试读锁，结果为不互斥
     *
     * @author pujian
     * @date 2020/6/28 20:35
     */
    @Test
    public void testRead() throws InterruptedException {
        // 执行线程一
        executor.execute(() -> {
            try {
                rlock.tryLock(1, TimeUnit.SECONDS);
                System.out.println("线程一获取读锁");
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rlock.unlock();
                System.out.println("线程一释放读锁");
            }
        });

        // 线程二
        executor.execute(() -> {
            try {
                rlock.tryLock(1, TimeUnit.SECONDS);
                System.out.println("线程二获取读锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rlock.unlock();
                System.out.println("线程二释放读锁");
            }
        });

        Thread.sleep(2000);

    }


    /**
     * 测试读写是否互斥，结果为互斥
     *
     * @author pujian
     * @date 2020/6/28 20:35
     */
    @Test
    public void testReadWrite() throws InterruptedException {
        // 线程一
        executor.execute(() -> {
            try {
                if (rlock.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println("线程一获取读锁");
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rlock.unlock();
                System.out.println("线程一释放读锁");
            }
        });
        // 线程二
        executor.execute(() -> {
            boolean hasWriteLock = false;
            try {
                if (wlock.tryLock(1, TimeUnit.SECONDS)) {
                    hasWriteLock = true;
                    System.out.println("线程二获取写锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (hasWriteLock) {
                    wlock.unlock();
                    System.out.println("线程二释放写锁");
                }
            }
        });

        Thread.sleep(3000);

    }


    /**
     * 写锁降级
     *
     * @author pujian
     * @date 2022/9/28 16:42
     */
    @Test
    public void testWriteLockFallback() throws InterruptedException {
        Runnable task = () -> {
            try {
                wlock.lock();
                System.out.println(Thread.currentThread().getName() + " 获取到了写锁，写下了一段话");
            } finally {
                try {
                    rlock.lock();
                    wlock.unlock();
                    System.out.println(Thread.currentThread().getName() + " 获取到了读锁，读取了一段话");
                } finally {
                    rlock.unlock();
                }
            }
        };
        executor.execute(task);
        TimeUnit.MILLISECONDS.sleep(200);
    }

}
