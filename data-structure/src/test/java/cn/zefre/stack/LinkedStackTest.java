package cn.zefre.stack;

import cn.zefre.stack.impl.LinkedStack;
import org.junit.Test;

/**
 * @author pujian
 * @date 2020/5/19
 */
public class LinkedStackTest {

    @Test
    public void testPush(){
        LinkedStack<String> ls = new LinkedStack<>();
        ls.push("1");
        ls.push("2");
        ls.push("3");
        ls.push("4");
        System.out.println("当前容量 = " + ls.size());
        ls.print();
    }

    @Test
    public void testPop(){
        LinkedStack<String> ls = new LinkedStack<>();
        ls.push("1");
        ls.push("2");
        ls.push("3");
        ls.push("4");

        String e1 = ls.pop();
        String e2 = ls.pop();
        System.out.println("当前容量 = " + ls.size());
        System.out.println("e1 = " + e1 + ",e2 = " + e2);
        ls.print();
    }

    @Test
    public void testIsEmpty(){
        LinkedStack<String> ls = new LinkedStack<>();
        System.out.println(ls.isEmpty());

        ls.push("1");
        System.out.println(ls.isEmpty());

        ls.pop();
        // 测试当栈为空时出栈操作
        ls.pop();
    }
}
