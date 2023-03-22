package cn.zefre.queue;


import cn.zefre.queue.impl.ArrayQueue;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

/**
 * @author pujian
 * @date 2020/5/27 15:31
 */
public class ArrayQueueTest {

    @Test
    public void testInit() {
        ArrayQueue<String> aq = new ArrayQueue<>(16);
        Assertions.assertEquals(0, aq.size());
    }

    @Test
    public void testOffer() {
        ArrayQueue<Integer> aq = new ArrayQueue<>(3);
        aq.offer(0);
        aq.offer(1);
        aq.offer(2);
        aq.offer(3);
        aq.offer(4);
        aq.offer(5);
        aq.offer(6);
        // 扩容
        aq.offer(7);
        System.out.println(aq);
    }

    @Test
    public void testPoll() {
        ArrayQueue<Integer> aq = new ArrayQueue<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        aq.poll();
        aq.poll();
        aq.poll();
        aq.offer(0);
        aq.offer(1);
        aq.offer(2);
        System.out.println(aq);
        // 扩容
        aq.offer(3);
        aq.offer(4);
        aq.offer(5);
        aq.offer(6);
        System.out.println(aq);
    }

    @Test
    public void testPeek() {
        ArrayQueue<String> aq = new ArrayQueue<>(4);
        aq.offer("1");
        aq.offer("2");
        aq.offer("3");

        String e1 = aq.peek();
        aq.poll();
        String e2 = aq.peek();
        aq.poll();
        String e3 = aq.peek();
        aq.poll();
        System.out.println("e1=" + e1 + ",e2=" + e2 + ",e3=" + e3);
    }

}
