package cn.zefre.queue;


import cn.zefre.queque.impl.ArrayQueque;
import org.junit.Test;

/**
 * @author pujian
 * @date 2020/5/27 15:31
 */
public class ArrayQueueTest {

    @Test
    public void testInit(){
        ArrayQueque<String> aq = new ArrayQueque<>(3);
        System.out.println(aq.size());
    }

    @Test
    public void testOffer(){
        ArrayQueque<String> aq = new ArrayQueque<>(3);
        aq.offer("1");
        aq.offer("2");
        aq.print();
        //aq.offer("3");队列已满，再次入队报错
    }

    @Test
    public void testPoll(){
        ArrayQueque<String> aq = new ArrayQueque<>(4);
        aq.offer("1");
        aq.offer("2");
        aq.offer("3");
        aq.print();

        System.out.println("#######################");

        String e1 = aq.poll();
        String e2 = aq.poll();
        String e3 = aq.poll();
        System.out.println("e1=" + e1 + ",e2=" + e2 + ",e3=" + e3);
        aq.print();
        //aq.poll(); 队列为空，再次出队报错
    }

    @Test
    public void testPeek(){
        ArrayQueque<String> aq = new ArrayQueque<>(4);
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

    @Test
    public void testMulti(){
        ArrayQueque<String> aq = new ArrayQueque<>(8);
        aq.offer("1");
        aq.offer("2");
        aq.offer("3");
        aq.offer("4");
        aq.offer("5");
        aq.offer("6");
        aq.offer("7");

        aq.poll();
        aq.poll();
        aq.poll();

        aq.offer("8");
        aq.offer("9");

        aq.poll();
        aq.poll();

        aq.offer("10");

        aq.print();

        System.out.println("head=" + aq.getHead() + ",tail=" + aq.getTail());

    }

    @Test
    public void testResize(){
        ArrayQueque<String> aq = new ArrayQueque<>(3);
        aq.offer("1");
        aq.offer("2");
        aq.poll();
        aq.offer("3");
        System.out.println("head=" + aq.getHead() + ",tail=" + aq.getTail());

        aq.offer("4");
        System.out.println("head=" + aq.getHead() + ",tail=" + aq.getTail());
    }
}
