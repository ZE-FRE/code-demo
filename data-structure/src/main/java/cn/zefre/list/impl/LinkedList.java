package cn.zefre.list.impl;

import cn.zefre.list.List;

/**
 * 单链表
 * @Author pujian
 * @Date 2020/5/19
 * @Description
 */
public class LinkedList<E> implements List<E> {

    private static class Node<E> {
        /**
         *数据
         */
        E data;
        /**
         *指向下一个节点的引用
         */
        Node<E> next;

        Node(){}
        Node(E data, Node<E> next){
            this.data = data;
            this.next = next;
        }
    }

    /**
     *头指针
     */
    private Node<E> first;

    @Override
    public int size() {
        int count = 0;
        Node<E> temp = first;
        while (temp != null){
            count++;
            temp = temp.next;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public int indexOf(E elem) {
        int index = 0;
        Node<E> temp = first;
        while (temp != null){
            if (temp.data.equals(elem)){
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     *单链表，不支持本方法，直接返回-1
     * @author pujian
     * @date 2020/5/29 16:17
     * @param elem
     * @return
     */
    @Override
    public int lastIndexOf(E elem) {
        return -1;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        Node<E> temp = first;
        for (int i = 0; i < index; i++){
            temp = temp.next;
        }
        return temp.data;
    }

    @Override
    public void add(int index, E elem) {
        checkIndex(index);
        Node<E> newNode;
        if (isEmpty()){
            newNode = new Node<>(elem, null);
            first = newNode;
        } else {
            Node<E> temp = first;
            for (int i = 0; i < index; i++){
                temp = temp.next;
            }
            newNode = new Node<>(elem, temp.next);
            temp.next = newNode;
        }
    }

    @Override
    public void add(E elem) {
        Node<E> newNode = new Node<>(elem, null);
        if (isEmpty()){
            first = newNode;
        } else {
            Node<E> temp = first;
            while (temp.next != null){
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    @Override
    public void add(int index, List<? extends E> list) {

    }

    @Override
    public void add(List<? extends E> list) {

    }

    @Override
    public void set(int index, E elem) {
        checkIndex(index);
        Node<E> temp = first;
        for (int i = 0; i < index; i++){
            temp = temp.next;
        }
        temp.data = elem;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> temp = first;
        for (int i = 0; i < index - 1; i++){
            temp = temp.next;
        }
        E resultData = temp.data;
        temp.next = temp.next.next;
        temp = null;
        return resultData;
    }

    @Override
    public void removeRange(int start, int end) {

    }

    @Override
    public void clear() {

    }

    /**
     *检查下标
     * @author pujian
     * @date 2020/5/29 16:36
     * @param index
     * @return
     */
    private void checkIndex(int index){
        if (index > size()){
            throw new RuntimeException("下标越界");
        }
    }
}
