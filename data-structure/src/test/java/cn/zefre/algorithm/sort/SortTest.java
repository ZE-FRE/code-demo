package cn.zefre.algorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class SortTest {

    final static int[] ARR_SEED = new int[]{22,24,48,1,38,4,8,7,81,64,14,17,38,14,4,2};

    @Test
    public void testSort() {
        int[] arr;
        System.out.print("原始数组：");
        printArr(ARR_SEED);

        System.out.print("交换排序：");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.switchSort(arr);
        printArr(arr);

        System.out.print("冒泡排序：");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.bubbleSort(arr);
        printArr(arr);

        System.out.print("选择排序：");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.selectSort(arr);
        printArr(arr);

        System.out.print("插入排序：");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.insertSort(arr);
        printArr(arr);

        System.out.print("希尔排序：");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.shellSort(arr);
        printArr(arr);

        System.out.print("归并排序1：");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.mergeSortLR(arr);
        printArr(arr);

        System.out.print("归并排序2：");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.mergeSortInTemp(arr, 0, arr.length-1);
        printArr(arr);

        System.out.print("归并排序3：");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        int[] temp = new int[arr.length];
        Sort.mergeSortOutTemp(arr, temp, 0, arr.length-1);
        printArr(arr);

        System.out.print("归并排序4：");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.mergeSort(arr, temp, 0, arr.length-1);
        printArr(arr);

        System.out.print("归并排序5：");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.mergeSortIterative(arr, temp);
        printArr(arr);

        System.out.print("快速排序：");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.quickSort(arr, 0, arr.length-1);
        printArr(arr);
    }

    @Test
    public void testQuickSort() {
        System.out.print("原始数组：");
        printArr(ARR_SEED);
        System.out.println("快速排序：");
        int[] arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.quickSort(arr, 0, arr.length-1);
        printArr(arr);
        System.out.println("======");
        arr = Arrays.copyOf(ARR_SEED, ARR_SEED.length);
        Sort.quickSortByInsert(arr, 0, arr.length-1);
        printArr(arr);
    }

    /**
     * 打印数组
     */
    public static void printArr(int[] arr) {
        for(int a : arr) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

}
