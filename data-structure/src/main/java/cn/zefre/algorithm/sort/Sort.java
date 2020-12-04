package cn.zefre.algorithm.sort;

import java.util.Arrays;

/**
 * 排序算法
 * @author pujian
 * @date 2020/11/17 09:05
 */
public class Sort implements ISort {

    /**
     * 交换排序
     * @param arr
     */
    public static void switchSort(int[] arr) {
        for(int i = 0; i < arr.length - 1; i++) {
            for(int j = i + 1; j < arr.length; j++) {
                if(arr[i] > arr[j]) {
                    // 交换
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            for(int j = arr.length - 1; j > i; j--) {
                if(arr[j] < arr[j-1]) {
                    // 交换
                    arr[j] = arr[j] + arr[j-1];
                    arr[j-1] = arr[j] - arr[j-1];
                    arr[j] = arr[j] - arr[j-1];
                }
            }
        }
    }

    /**
     * 选择排序
     * @param arr
     */
    public static void selectSort(int[] arr) {
        for(int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for(int j = i; j < arr.length; j++) {
                if(arr[j] < arr[min])
                    min = j;
            }
            if(i != min) {
                // 交换
                arr[i] = arr[i] + arr[min];
                arr[min] = arr[i] - arr[min];
                arr[i] = arr[i] - arr[min];
            }
        }
    }

    /**
     * 直接插入排序
     * @param arr
     */
    public static void insertSort(int[] arr) {
        for(int i = 1; i < arr.length; i++) {
            int j = i - 1;
            // 待插入的元素
            int num = arr[i];
            while (j >= 0 && arr[j] >= num) {
                // 移动元素
                arr[j+1] = arr[j];
                j--;
            }
            // 插入元素
            arr[j+1] = num;
        }
    }

    /**
     * 希尔排序
     * @param arr
     */
    public static void shellSort(int[] arr) {
        for(int gap = arr.length / 2; gap > 0; gap /= 2) {
            for(int j = gap; j < arr.length; j++) {
                for(int k = j; k >= gap && arr[k] <= arr[k-gap]; k -= gap) {
                    // 交换元素
                    arr[k] = arr[k] + arr[k-gap];
                    arr[k-gap] = arr[k] - arr[k-gap];
                    arr[k] = arr[k] - arr[k-gap];
                }
            }
        }
    }


    /**
     * 二路归并排序
     * @author pujian
     * @date 2020/11/17 09:06
     * @param arr
     * 时间复杂度：最坏：O(nlogn) 平均：O(nlogn) 最好：O(nlogn)
     * 空间复杂度：O(nlogn)
     * 网上常规实现空间复杂度应为O(n)
     * 本实现由于每层申请的空间之和都为n，总共有log2n层，所以总的空间占用为nlogn
     */
    public static void mergeSortLR(int[] arr) {
        if(arr.length <= 1)
            return;
        int mid = (arr.length - 1) / 2;
        int[] leftArr = new int[mid+1];
        int[] rightArr = new int[arr.length-leftArr.length];
        System.arraycopy(arr, 0, leftArr, 0, leftArr.length);
        System.arraycopy(arr, leftArr.length, rightArr, 0, rightArr.length);
        // 对左边部分排序
        mergeSortLR(leftArr);
        // 对右边部分排序
        mergeSortLR(rightArr);
        // 将左、右两个有序数组合并为一个有序数组
        int leftIndex = 0, rightIndex = 0, mergeIndex = 0;
        while (leftIndex < leftArr.length && rightIndex < rightArr.length) {
            arr[mergeIndex++] = leftArr[leftIndex] <= rightArr[rightIndex] ? leftArr[leftIndex++] : rightArr[rightIndex++];
        }
        while (leftIndex < leftArr.length) {
            // 将左边数组剩余元素复制到主干中
            arr[mergeIndex++] = leftArr[leftIndex++];
        }
        while (rightIndex < rightArr.length) {
            // 将右边数组剩余元素复制到主干中
            arr[mergeIndex++] = rightArr[rightIndex++];
        }

    }

    /*
     * 1 2 3 4 5 6 7 8
     * lo = 0  mid = 3  hi = 7
     * [1 2 3 4] [5 6 7 8]
     *              lo = 4 mid = (4 + 7) / 2 = 5 hi = 7
     *            [5 6]  [7 8]
     *                    lo = 6 mid = (6 + 7) / 2 = 6 hi = 7
     *                    [7] [8] lo = 7 mid = (7+7)/2 = 7 hi = 7
     *                    lo = 6 mid = (6+6)/2 = 6 hi = 6
     */
    /**
     * 本实现换一个思路，每次不用申请左、右两部分数组，而是申请一个存放左、右两个数组合并结果的临时数组，其大小为hi-lo+1
     * 直接通过arr[lo-mid]、arr[mid+1 - hi]分别访问左、右两个数组
     * 空间复杂度也是O(nlogn)
     * @param arr
     * @param lo
     * @param hi
     */
    public static void mergeSortInTemp(int[] arr, int lo, int hi) {
        if(lo == hi)
            return;
        int mid = (lo + hi) / 2;
        // 对左边部分数组排序
        mergeSortInTemp(arr, lo, mid);
        // 对右边部分数组排序
        mergeSortInTemp(arr, mid+1, hi);
        // 合并左、右两部分
        int[] temp = new int[hi-lo+1];
        int leftIndex = lo, rightIndex = mid+1, mergeIndex = 0;
        while (leftIndex <= mid && rightIndex <= hi) {
            temp[mergeIndex++] = arr[leftIndex] <= arr[rightIndex] ? arr[leftIndex++] : arr[rightIndex++];
        }
        while (leftIndex <= mid) {
            // 将左半部分数组剩余元素复制到temp
            temp[mergeIndex++] = arr[leftIndex++];
        }
        while (rightIndex <= hi) {
            // 将右半部分数组剩余元素复制到temp
            temp[mergeIndex++] = arr[rightIndex++];
        }
        // 将temp复制到arr
        System.arraycopy(temp, 0, arr, lo, temp.length);
    }

    /**
     * 在上一步基础上将temp数组移出来，不要每次都申请空间，temp直接通过外部传入
     * 空间复杂度提升到O(n)
     * @param arr
     * @param temp
     * @param lo
     * @param hi
     */
    public static void mergeSortOutTemp(int[] arr, int[] temp, int lo, int hi) {
        if(lo == hi)
            return;
        int mid = (lo + hi) / 2;
        // 对左边部分数组排序
        mergeSortOutTemp(arr, temp, lo, mid);
        // 对右边部分数组排序
        mergeSortOutTemp(arr, temp, mid+1, hi);
        // 开始合并左、右两部分
        int leftIndex = lo, rightIndex = mid+1, mergeIndex = 0;
        while (leftIndex <= mid && rightIndex <= hi) {
            temp[mergeIndex++] = arr[leftIndex] <= arr[rightIndex] ? arr[leftIndex++] : arr[rightIndex++];
        }
        while (leftIndex <= mid) {
            temp[mergeIndex++] = arr[leftIndex++];
        }
        while (rightIndex <= hi) {
            temp[mergeIndex++] = arr[rightIndex++];
        }
        // 将temp复制到arr
        System.arraycopy(temp, 0, arr, lo, hi-lo+1);
    }


    /**
     * 在上一步基础上，将merge操作提出来封装成一个方法
     * @param arr
     * @param temp
     * @param lo
     * @param hi
     */
    public static void mergeSort(int[] arr, int[] temp, int lo, int hi) {
        if(lo == hi)
            return;
        int mid = (lo + hi) / 2;
        mergeSort(arr, temp, lo, mid);
        mergeSort(arr, temp,mid+1, hi);
        merge(arr, temp, lo, mid, hi);
    }

    public static void merge(int[] arr, int[] temp, int lo, int mid, int hi) {
        // 左边部分的最大值比右边部分的最小值还小，直接返回即可
        // 有这一步处理，对一个有序的序列进行排序，时间复杂度可达到O(n)
        if(arr[mid] <= arr[mid+1])
            return;
        int leftIndex = lo, rightIndex = mid+1, mergeIndex = 0;
        while (leftIndex <= mid && rightIndex <= hi) {
            temp[mergeIndex++] = arr[leftIndex] <= arr[rightIndex] ? arr[leftIndex++] : arr[rightIndex++];
        }
        while (leftIndex <= mid) {
            temp[mergeIndex++] = arr[leftIndex++];
        }
        while (rightIndex <= hi) {
            temp[mergeIndex++] = arr[rightIndex++];
        }
        // 将temp复制到arr
        System.arraycopy(temp, 0, arr, lo, hi-lo+1);
    }


    /**
     *  迭代方式实现归并排序
     * @param arr
     * @param temp
     */
    public static void mergeSortIterative(int[] arr, int[] temp) {
        int length = arr.length;
        for(int i = 1; i < length; i *= 2) {
            for(int j = 0; j < length - i; j += i*2) {
                int lo = j;
                int mid = j + i - 1;
                int hi = Math.min(j+i*2-1, length-1);
                merge(arr, temp, lo, mid, hi);
            }
        }
    }


    public static void quickSort(int[] arr, int lo, int hi) {
        if(lo >= hi)
            return;
        int partition = partition(arr, lo, hi);
        quickSort(arr, lo, partition-1);
        quickSort(arr, partition+1, hi);
    }

    public static void quickSortByInsert(int[] arr, int lo, int hi) {
        if(lo >= hi)
            return;
        int partition = partitionByInsert(arr, lo, hi);
        quickSort(arr, lo, partition-1);
        quickSort(arr, partition+1, hi);
    }

    /**
     * 快排经典分区
     * @param arr
     * @param lo
     * @param hi
     * @return
     * 图解过程参考：https://blog.51cto.com/13733462/2113397
     */
    private static int partition(int[] arr, int lo, int hi) {
        int key = arr[lo];
        while(lo < hi) {
            while(lo < hi && arr[hi] >= key)
                hi--;
            // arr[hi] < key，将其放入左边
            arr[lo] = arr[hi];
            while(lo < hi && arr[lo] <= key)
                lo++;
            // arr[lo] > key，将其放入右边
            arr[hi] = arr[lo];
            // 左、右完成一轮后，将arr[lo]的值置为key，开始下一轮
            arr[lo] = key;
        }
        return lo;
    }

    /**
     * 通过插入排序的方式分区
     * 以arr[lo]为基准值，遍历到arr[hi]
     * 当某次循环arr[i]比基准值小，则依次将lo~(i-1)之间的元素向右移动一位
     * 然后将这个元素(即arr[i])插入到lo位置。即arr[lo] = arr[i]
     * @param arr
     * @param lo
     * @param hi
     * @return
     */
    private static int partitionByInsert(int[] arr, int lo, int hi) {
        int key = arr[lo];
        int partition = lo;
        for(int i = lo; i <= hi; i++) {
            if(key > arr[i]) {
                int temp = arr[i];
                // 向右移动元素
                for(int j = i; j > lo; j--)
                    arr[j] = arr[j-1];
                // 插入
                arr[lo] = temp;
                // 分区位置移动一位
                partition++;
            }
        }
        return partition;
    }


    @Override
    public String sortTest(int[] arr) {
        int[] temp = new int[arr.length];
        mergeSortOutTemp(arr, temp, 0, arr.length-1);
        return Arrays.toString(arr);
    }
}
