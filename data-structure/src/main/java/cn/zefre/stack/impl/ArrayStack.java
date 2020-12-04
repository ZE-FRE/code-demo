package cn.zefre.stack.impl;

import cn.zefre.stack.Stack;

/**
 * 栈的顺序存储结构实现
 * @Author pujian
 * @Date 2020/5/18
 * @Description
 */
public class ArrayStack<E> implements Stack<E> {

    private static final int defaultCapacity = 10;

    /**
     *元素数组
     */
    private Object[] elementData;

    /**
     *栈顶下标
     */
    private int top;

    public ArrayStack(){
        elementData = new Object[defaultCapacity];
        top = -1;
    };

    public ArrayStack(int initCapacity){
        elementData = new Object[initCapacity];
        top = -1;
    }

    @Override
    public int size() { return top == -1 ? 0 : top + 1; }

    @Override
    public boolean isEmpty() { return top == -1; }

    @Override
    public void push(E element) {
        checkFull();
        elementData[++top] = element;
    }

    @Override
    public E pop() {
        checkEmpty();
        return (E) elementData[top--];
    }

    /**
     *检查是否栈满
     * @Author pujian
     * @Date 2020/5/18 16:58
     * @return
     */
    private void checkFull(){
        if (top == elementData.length - 1){
            throw new RuntimeException("栈满！");
        }
    }

    /**
     *检查是否为空栈
     * @Author pujian
     * @Date 2020/5/18 17:02
     * @return
     */
    private void checkEmpty(){
        if(isEmpty()){
            throw new RuntimeException("栈是空的!");
        }
    }

    /**
     *测试用，打印栈
     * @Author pujian
     * @Date 2020/5/19 11:01
     * @return
     */
    public void print(){
        for (int i = top; i > -1 ; i--) {
            System.out.println(elementData[i]);
        }
    }

    /**
     *测试用
     * @Author pujian
     * @Date 2020/5/19 11:01
     * @return
     */
    public int getTop(){
        return top;
    }
}
