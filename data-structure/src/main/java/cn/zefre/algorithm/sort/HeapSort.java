package cn.zefre.algorithm.sort;

import lombok.experimental.UtilityClass;

/**
 * 堆排序
 *
 * @author pujian
 * @date 2023/3/17 14:34
 */
@UtilityClass
public class HeapSort {

    /**
     * 堆排序
     *
     * @param arr 待排序数组
     * @author pujian
     * @date 2023/3/17 17:46
     */
    public static <E extends Comparable<E>> void sort(E[] arr) {
        initHeap(arr);
        for (int i = arr.length - 1; i >= 1; i--) {
            // 将根结点与最后一个结点交换
            E last = arr[i];
            arr[i] = arr[0];
            arr[0] = last;
            // 因为根结点变了，所以可能导致该数组不满足堆的定义
            // 此时左子树和右子树都满足堆的定义
            // 使用筛降法将根结点沉到合适位置，使其满足堆的定义
            siftDown(arr, i, 0);
        }
    }

    /**
     * 初始化堆
     *
     * @param arr 待初始化数组
     * @author pujian
     * @date 2023/3/17 17:45
     */
    private static <E extends Comparable<E>> void initHeap(E[] arr) {
        for (int i = (arr.length - 1) >>> 1; i >= 0; i--) {
            siftDown(arr, arr.length, i);
        }
    }

    /**
     * 使以指定下标结点为根结点的子树满足堆定义
     * 使用筛降法，将根节点沉到合适的位置
     *
     * @param arr    堆
     * @param length 数组长度
     * @param index  要筛降元素的下标
     * @author pujian
     * @date 2023/3/17 17:05
     */
    private static <E extends Comparable<E>> void siftDown(E[] arr, int length, int index) {
        E value = arr[index];
        int left;
        while ((left = (index << 1) + 1) < length) {
            int right = left + 1;
            // 左结点、右结点中小的那一个
            int min = right < length && arr[left].compareTo(arr[right]) > 0 ? right : left;
            // 父结点与子结点中小的那一个比较，若父结点小，则已满足堆定义；
            // 若父结点大，则交换
            if (value.compareTo(arr[min]) < 0) {
                break;
            }
            arr[index] = arr[min];
            index = min;
        }
        arr[index] = value;
    }

}
