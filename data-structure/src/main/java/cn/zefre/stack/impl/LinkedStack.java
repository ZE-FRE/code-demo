package cn.zefre.stack.impl;

import cn.zefre.stack.Stack;

/**
 * 链栈
 * @author pujian
 * @date 2020/5/18
 */
public class LinkedStack<E> implements Stack<E> {

    private static class LinkedList<E> {
        /**
         *元素
         */
        private E elementData;

        /**
         *指向下一个元素的指针
         */
        private LinkedList<E> next;
    }

    /**
     *栈顶指针
     */
    private LinkedList<E> top;

    /**
     *栈的当前大小
     */
    private int count;

    @Override
    public int size() { return count; }

    @Override
    public boolean isEmpty() { return count == 0; }

    @Override
    public void push(E element) {
        LinkedList<E> nextPtr = new LinkedList<>();
        nextPtr.elementData = element;
        nextPtr.next = top;
        top = nextPtr;
        count++;
    }

    @Override
    public E pop() {
        checkEmpty();
        LinkedList<E> temp = top;
        E ele = top.elementData;
        top = top.next;
        temp.next = null;
        count--;
        return ele;
    }

    /**
     *检查是否为空栈
     * @author pujian
     * @date 2020/5/18 17:21
     * @return
     */
    private void checkEmpty(){
        if(isEmpty()){
            throw new RuntimeException("栈是空的!");
        }
    }

    /**
     * 用于测试，遍历输出栈
     * @Author pujian
     * @Date 2020/5/19 10:52
     * @return
     */
    public void print(){
        LinkedList<E> it = top;
        while (null != it){
            System.out.println(it.elementData);
            it = it.next;
        }
    }
}
