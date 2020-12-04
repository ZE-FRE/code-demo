package cn.zefre.queue;

import cn.zefre.queque.impl.LinkedQueue;
import org.junit.Test;

/**
 * @author pujian
 * @date 2020/5/29 11:20
 */
public class LinkedQueueTest {

    @Test
    public void testInit(){
        LinkedQueue<String> lq = new LinkedQueue<>();
        System.out.println("开始####### 构造了一个空的队列");
        System.out.println("队列初始是否为空？ " + lq.isEmpty());
        lq.offer("1");
        lq.offer("2");
        lq.offer("3");
        lq.offer("4");

        System.out.println("this queue size is " + lq.size());
        lq.print();
        System.out.println("队列现在是否为空？ "  + lq.isEmpty());
    }

    @Test
    public void testPoll(){
        LinkedQueue<String> lq = new LinkedQueue<>();
        lq.offer("1");
        lq.offer("2");
        lq.offer("3");
        lq.offer("4");
        lq.print();

        String e1 = lq.poll();
        String e2 = lq.poll();
        System.out.println("e1=" + e1 + ",e2=" + e2);
        lq.print();
    }

    @Test
    public void testPeek() {
        LinkedQueue<String> lq = new LinkedQueue<>();
        lq.offer("1");
        lq.offer("2");
        lq.offer("3");
        lq.offer("4");

        String e1 = lq.peek();
        lq.poll();
        String e2 = lq.peek();
        lq.poll();
        String e3 = lq.peek();
        System.out.println("e1=" + e1 + ",e2=" + e2 + ",e3=" + e3);
        lq.print();
    }
}
