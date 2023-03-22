package cn.zefre.queue.impl;

import cn.zefre.queue.Queue;

import java.util.Collection;
import java.util.Objects;

/**
 * 基于数组实现的单向队列
 *
 * @author pujian
 * @date 2020/5/26 17:17
 */
public class ArrayQueue<E> implements Queue<E> {

    /**
     * 最小容量
     */
    private static final int MIN_CAPACITY = 8;
    /**
     * 最大容量，2的30次方
     */
    private static final int MAX_CAPACITY = (Integer.MAX_VALUE >>> 1) + 1;

    /**
     * 存放元素的数组
     */
    private Object[] elementData;

    /**
     * 队首下标
     */
    private int head;
    /**
     * 指向队尾的下一个元素的下标
     * 由于尾指针指向的位置不存储数据
     * 因此elementData里最多存储elementData.length - 1个数据
     */
    private int tail;

    public ArrayQueue() {
        elementData = new Object[MIN_CAPACITY];
    }

    public ArrayQueue(int initCapacity) {
        if (initCapacity < 0) {
            throw new IllegalArgumentException("初始容量不能小于0");
        }
        elementData = new Object[capacityAdjustment(initCapacity)];
    }

    public ArrayQueue(Collection<E> coll) {
        Objects.requireNonNull(coll, "coll cannot be null.");
        int size = coll.size();
        elementData = new Object[capacityAdjustment(size)];
        System.arraycopy(coll.toArray(), 0, elementData, 0, size);
        tail = size;
    }

    /**
     * 调整容量为2的幂次方
     *
     * @param initialCapacity 初始容量
     * @return int
     * @author pujian
     * @date 2023/3/21 9:34
     */
    private int capacityAdjustment(int initialCapacity) {
        if (initialCapacity < MIN_CAPACITY) {
            return MIN_CAPACITY;
        } else if (initialCapacity > MAX_CAPACITY) {
            return MAX_CAPACITY;
        } else {
            initialCapacity |= initialCapacity >>> 1;
            initialCapacity |= initialCapacity >>> 2;
            initialCapacity |= initialCapacity >>> 4;
            initialCapacity |= initialCapacity >>> 8;
            initialCapacity |= initialCapacity >>> 16;
            initialCapacity++;
            return initialCapacity;
        }
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public void offer(E element) {
        if (((tail + 1) & (elementData.length - 1)) == head) {
            grow();
        }
        elementData[tail] = element;
        tail = (tail + 1) & (elementData.length - 1);
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        // noinspection unchecked
        E element = (E) elementData[head];
        elementData[head] = null;
        head = (head + 1) & (elementData.length - 1);
        return element;
    }

    @Override
    public E peek() {
        // noinspection unchecked
        return (E) elementData[head];
    }

    @Override
    public int size() {
        int diff = tail - head;
        if (diff < 0) {
            diff += elementData.length;
        }
        return diff;
    }

    /**
     * 扩容
     *
     * @author pujian
     * @date 2023/3/21 16:18
     */
    private void grow() {
        int oldCapacity = elementData.length;
        // 新容量是原来的两倍
        int newCapacity = oldCapacity << 1;
        if (newCapacity < 0) {
            throw new OutOfMemoryError("容量达到上限");
        }
        // 队列里元素的个数
        int size = oldCapacity - 1;
        int h = head;
        Object[] newElementData = new Object[newCapacity];
        for (int i = 0; h != tail; i++) {
            newElementData[i] = elementData[h];
            h = (h + 1) & size;
        }
        elementData = newElementData;
        head = 0;
        tail = size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int h = head;
        while (h != tail){
            sb.append(elementData[h]).append(",");
            h = (h + 1) & (elementData.length - 1);
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

}
