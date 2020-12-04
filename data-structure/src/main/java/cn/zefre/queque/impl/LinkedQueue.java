package cn.zefre.queque.impl;

import cn.zefre.queque.Queue;

/**
 * 链队列
 * @author pujian
 * @date 2020/5/29 10:49
 */
public class LinkedQueue<E> implements Queue<E> {

    private static class Node<E> {
         E data;
         Node<E> next;
         Node(E data, Node<E> next){
            this.data = data;
            this.next = next;
        }
    }

    /**
     *队头指针
     */
    private Node<E> head;
    /**
     *队尾指针
     */
    private Node<E> tail;

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void offer(E element) {
        Node<E> node = new Node<>(element, null);
        if (null == tail) {
            head = tail = node;
        }else {
            tail.next = node;
            tail = node;
        }
    }

    @Override
    public E poll() {
        checkEmpty();
        Node<E> temp = head;
        E value = temp.data;
        head = head.next;
        temp = null;
        return value;
    }

    @Override
    public E peek() {
        checkEmpty();
        return head.data;
    }

    @Override
    public int size() {
        int size = 0;
        Node<E> node = head;
        while (node != null){
            size++;
            node = node.next;
        }
        return size;
    }

    private void checkEmpty(){
        if (isEmpty()){
            throw new RuntimeException("this queue is empty");
        }
    }

    /**
     *测试用
     * @author pujian
     * @date 2020/5/29 11:23
     * @return
     */
    public void print(){
        Node<E> node = head;
        while (node != null){
            System.out.print(node.data + " ");
            node = node.next;
        }
        System.out.println();
    }
}
