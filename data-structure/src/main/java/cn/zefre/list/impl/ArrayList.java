package cn.zefre.list.impl;

import cn.zefre.list.List;

import java.io.Serializable;
import java.util.RandomAccess;

/**
 * 顺序表
 * @Author pujian
 * @Date 2020/4/27
 * @Description
 */
public class ArrayList<E> implements List<E>, RandomAccess, Serializable {

    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] DEFAULT_ELEMENT_DATA = {};

    /**
     *顺序表大小
     */
    private int size;

    /**
     *存放元素的数组
     */
    private Object[] elementData;

    public ArrayList(){
        elementData = DEFAULT_ELEMENT_DATA;
    }

    public ArrayList(int capacity){
        elementData = new Object[capacity];
    }

    public ArrayList(List<? extends E> list){
    }

    /**
     * 扩充容量
     * @Author pujian
     * @Date 2020/4/27 10:22
     * @Param size 新的容量大小
     * @return
     */
    private void increaseCapacity(int size){
        if(size > elementData.length){
            Object[] newElementData = new Object[size];
            System.arraycopy(elementData, 0, newElementData, 0, this.size);
            elementData = newElementData;
        }
    }

    /**
     * 确保能存放size个元素，若不能存放，则扩充
     * @Author pujian
     * @Date 2020/4/27 10:23
     * @Param size
     * @return
     */
    private void ensure(int size){
        int oldSize = elementData.length;
        if(oldSize < size){
            int newSize = oldSize + (oldSize >> 1);
            if(newSize < size)
                newSize = size;
            increaseCapacity(size);
        }
    }

    /**
     * 检查下标是否越界
     * @Author pujian
     * @Date 2020/4/27 10:29
     * @Param index
     * @return
     */
    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index:" + index);
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int indexOf(E elem) {
        if(null == elem){
            for (int i = 0; i < size; i++){
                if(null == elementData[i])
                    return i;
            }
        } else{
            for (int i = 0; i < size; i++){
                if(elem.equals(elementData[i]))
                    return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E elem) {
        if(null == elem){
            for (int i = size - 1; i >= 0; i--){
                if(null == elementData[i])
                    return i;
            }
        } else{
            for (int i = size - 1; i >= 0; i--){
                if(elem.equals(elementData[i]))
                    return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) elementData[index];
    }

    @Override
    public void add(int index, E elem) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("index:" + index);
        }
        ensure(elementData.length + 1);
        System.arraycopy(elementData, index, elementData, index+1, size-index);
        elementData[index] = elem;
        size++;
    }

    @Override
    public void add(E elem) {
        ensure(elementData.length + 1);
        elementData[size++] = elem;
    }

    @Override
    public void add(int index, List<? extends E> list) {

    }

    @Override
    public void add(List<? extends E> list) {

    }

    @Override
    public void set(int index, E elem){
        checkIndex(index);
        elementData[index] = elem;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E oldValue = (E) elementData[index];
        int numMoved = size - index - 1;
        if(numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    @Override
    public void removeRange(int start, int end) {
        checkIndex(start);
        if(end > size || end < 0){
            throw new IndexOutOfBoundsException("end:" + end);
        }
        if(start > end){
            throw new IndexOutOfBoundsException("start:" + start + " > " + "end:" + end);
        }

        int numMoved = end - start;
        if(numMoved > 0){
            System.arraycopy(elementData, end, elementData, start, numMoved);
            int newSize = size - (end - start);
            for(int i = newSize; i < size; i++){
                elementData[i] = null;
            }
            this.size = newSize;
        }

    }

    @Override
    public void clear() {
        elementData = DEFAULT_ELEMENT_DATA;
        size = 0;
    }
}
