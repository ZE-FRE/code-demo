package cn.zefre.stack;

import cn.zefre.stack.impl.ArrayStack;
import org.junit.Test;

import java.util.*;

/**
 * @author pujian
 * @date 2020/5/19
 */
public class ArrayStackTest {

    @Test
    public void testPush(){
        ArrayStack<Integer> as = new ArrayStack<>(2);
        as.push(1);
        as.push(2);
        System.out.println("top = " + as.getTop());
        as.print();
    }

    @Test
    public void testPop(){
        ArrayStack<Integer> as = new ArrayStack<>(2);
        as.push(1);
        as.push(2);
        System.out.println("top = " + as.getTop());

        Integer e1 = as.pop();
        System.out.println("top = " + as.getTop());
        Integer e2 = as.pop();
        System.out.println("e1 = " + e1 + ", e2 = " + e2);
        System.out.println("top = " + as.getTop());

        // 测试报错
        as.pop();
    }

    @Test
    public void testStackFull() {
        ArrayStack<Integer> as = new ArrayStack<>(2);
        as.push(1);
        as.push(2);
        as.print();
        as.push(3);
    }

    @Test
    public void testHashSet() {
        Collection<String> c = new ArrayList<>(4);
        c.add("first");
        c.add("second");
        c.add("third");
        c.add("fourth");
        c.add("fifth");

        Set<String> set = new HashSet<>(c);
        for (String s : set) {
            System.out.println(s);
        }
    }

    @Test
    public void testLinkedHashSet() {
        Collection<String> c = new ArrayList<>(4);
        c.add("a");
        c.add("b");
        c.add("c");
        c.add("d");
        c.add("e");

        Set<String> set = new LinkedHashSet<>(c);
        set.add("f");
        for (String s : set) {
            System.out.println(s);
        }
    }

    @Test
    public void testLinkedHashMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("1", "jackie");
        map.put("2", "tom");
        map.put("3", "mouse");
        map.put("4", "cindy");
        Set<String> keys = map.keySet();
        for(String key : keys) {
            System.out.println(key);
        }
        for(String value : map.values()) {
            System.out.println(value);
        }
    }

}
