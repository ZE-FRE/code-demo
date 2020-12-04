package cn.zefre.jdk8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pujian
 * @date 2020/10/30 14:06
 */
public class SpliteratorTest {

    @Test
    public void testArrayListSpliterator() throws InterruptedException {

        List<String> names = new ArrayList<>(Arrays.asList("zhangsan", "lisi", "wangwu", "qianliu"));
        Spliterator<String> spliteratorA = names.spliterator();
        Spliterator<String> spliteratorB = spliteratorA.trySplit();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(() -> spliteratorA.forEachRemaining(name -> System.out.println("线程1:" + name)));
        executor.execute(() -> spliteratorB.forEachRemaining(name -> System.out.println("线程2:" + name)));

        Thread.sleep(1000);
    }
}
