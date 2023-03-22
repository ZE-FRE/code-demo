package cn.zefre.queue.impl;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * 基于小顶堆构建的优先级队列
 *
 * @author pujian
 * @date 2023/3/20 14:01
 */
public class PriorityQueue<E extends Comparable<E>> extends AbstractQueue<E> {

    /**
     * 堆
     */
    private Object[] heap;

    private int size;

    /**
     * 最小容量
     */
    private static final int MIN_CAPACITY = 4;
    /**
     * 最大容量
     */
    private static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;

    public PriorityQueue() {
        this.heap = new Object[MIN_CAPACITY];
    }

    public PriorityQueue(Collection<E> coll) {
        Objects.requireNonNull(coll, "coll cannot be null.");
        this.size = coll.size();
        this.heap = new Object[capacity(this.size)];
        System.arraycopy(coll.toArray(), 0, this.heap, 0, this.size);
        initHeap();
    }

    /**
     * 限制容量在合适的区间
     *
     * @param initialCapacity 初始容量
     * @return int
     * @author pujian
     * @date 2023/3/21 9:34
     */
    private int capacity(int initialCapacity) {
        if (initialCapacity < MIN_CAPACITY) {
            return MIN_CAPACITY;
        } else if (initialCapacity > MAX_CAPACITY) {
            return MAX_CAPACITY;
        }
        return initialCapacity;
    }

    /**
     * 扩容
     *
     * @author pujian
     * @date 2023/3/21 10:43
     */
    private void grow() {
        int oldCapacity = this.heap.length;
        int newCapacity = oldCapacity + oldCapacity >>> 1;
        if (newCapacity > MAX_CAPACITY) {
            throw new OutOfMemoryError("容量达到上限");
        }
        Object[] newHeap = new Object[newCapacity];
        System.arraycopy(this.heap, 0, newHeap, 0, this.size);
        this.heap = newHeap;
    }

    private void swap(int i, int j) {
        Object temp = this.heap[i];
        this.heap[i] = this.heap[j];
        this.heap[j] = temp;
    }

    /**
     * 初始化堆
     *
     * @author pujian
     * @date 2023/3/21 10:12
     */
    private void initHeap() {
        for (int i = (size >>> 1) - 1; i >= 0; i--) siftDown(i);
    }

    /**
     * 筛降法，使子树满足堆定义
     *
     * @param parent 子树根节点
     * @author pujian
     * @date 2023/3/21 9:57
     */
    @SuppressWarnings("unchecked")
    private void siftDown(int parent) {
        int left;
        while ((left = (parent << 1) + 1) < size) {
            int right = left + 1;
            // 先比较左、右结点，取小的那个
            int min = right < size && ((E) heap[right]).compareTo((E) heap[left]) < 0 ? right : left;
            if (((E) heap[parent]).compareTo((E) heap[min]) > 0) {
                // 父节点大，交换
                swap(parent, min);
                parent = min;
            } else {
                break;
            }
        }
    }

    /**
     * 从最后一个结点开始向上调整，使其满足堆定义
     *
     * @author pujian
     * @date 2023/3/21 10:05
     */
    @SuppressWarnings("unchecked")
    private void siftUp() {
        int child = size - 1;
        int parent;
        while ((parent = (child - 1) >> 1) >= 0) {
            if (((E) heap[parent]).compareTo((E) heap[child]) > 0) {
                // 父节点大，交换
                swap(parent, child);
                child = parent;
            } else {
                break;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean offer(E element) {
        if (size == heap.length) grow();
        heap[++size] = element;
        siftUp();
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E poll() {
        E element = (E) heap[0];
        if (size == 1) {
            heap[0] = null;
            return element;
        }
        int last = --size;
        heap[0] = heap[last];
        heap[last] = null;
        siftDown(0);
        return element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        return (E) heap[0];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

}
