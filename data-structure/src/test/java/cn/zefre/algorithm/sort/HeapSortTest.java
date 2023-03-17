package cn.zefre.algorithm.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author pujian
 * @date 2023/3/17 16:15
 */
public class HeapSortTest {

    @Test
    public void testSort() {
        Integer[] arr = new Integer[] {22,24,48,1,38,4,8,7,81,64,14,17,38,14,4,2};
        HeapSort.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
