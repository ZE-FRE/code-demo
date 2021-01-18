package cn.zefre.queque.impl;

import cn.zefre.queque.Queue;

/**
 * 基于数组实现的单向队列
 * @author pujian
 * @date 2020/5/26 17:17
 */
public class ArrayQueue<E> implements Queue<E> {

    private static final int DEFAULT_CAPACITY = 10;

    /**
     *存放元素的数组
     */
    private Object[] elementData;

    /**
     *队首下标
     */
    private int head;
    /**
     *指向队尾的下一个元素的下标
     */
    private int tail;

    public ArrayQueue(){
        elementData = new Object[DEFAULT_CAPACITY];
    }

    public ArrayQueue(int initCapacity){
        if (initCapacity < 0){
            throw new RuntimeException("initCapacity is not allowed less than 0");
        }
        elementData = new Object[initCapacity];
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public void offer(E element) {
        //checkFull();
        if ((size()+1) % elementData.length == 0){
            resize();
        }
        elementData[tail] = element;
        tail = (tail + 1) % elementData.length;
    }

    @Override
    public E poll() {
        checkEmpty();
        E e = (E) elementData[head];
        elementData[head] = null;
        head = (head + 1) % elementData.length;
        return e;
    }

    @Override
    public E peek() {
        return (E) elementData[head];
    }

    @Override
    public int size() {
        int diff = tail - head;
        if(diff < 0){
            diff += elementData.length;
        }
        return diff;
    }

    private void checkEmpty(){
        if(head == tail){
            throw new RuntimeException("this ArrayDeque is empty");
        }
    }
    private void checkFull(){
        if ((size()+1) % elementData.length == 0){
            throw new RuntimeException("this ArrayDeque is full");
        }
    }

    private void resize(){
        int size = size();
        int h = head;
        Object[] newElementData = new Object[elementData.length << 1];
        for (int i = 0; i < size; i++) {
            newElementData[i] = elementData[h];
            h = (h + 1) % elementData.length;
        }
        elementData = newElementData;
        head = 0;
        tail = size;
    }

    /**
     * 以下方法为测试用
     */
    public void print() {
        int size = size();
        int h = head;
        System.out.print("this queue elements is ");
        for (int i = 0; i < size; i++) {
            System.out.print(elementData[h] + " ");
            h = (h + 1) % elementData.length;
        }
        System.out.println();
    }
    public int getHead(){ return head; }
    public int getTail(){ return tail; }
}
