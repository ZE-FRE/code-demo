package cn.zefre;

import org.junit.Test;

/**
 * @author pujian
 * @date 2020/11/14 10:56
 */
public class CommonTest {

    @Test
    public void testRelativeSortArray() {
        int[] arr1 = new int[]{2,5,4,81,3,2,5,4,1,8,1,3,6,24};
        int[] arr2 = new int[]{2,3,4,5,1};
        //int[] arr1 = new int[]{2,3,1,3,2,4,6,7,9,2,19};
        //int[] arr2 = new int[]{2,1,4,3,9,6};
        relativeSortArray(arr1, arr2);
        printArr(arr1);
    }

    @Test
    public void testRemoveKdigits() {
        String num = "2851439";
        int k = 3;
        System.out.println(removeKdigits(num, k));
    }

    /**
     * leetcode#1122 数组的相对排序
     * @author pujian
     * @date 2020/11/14 11:36
     * @param arr1
     * @param arr2
     * @return
     * 给你两个数组，arr1 和 arr2，arr2 中的元素各不相同，arr2 中的每个元素都出现在 arr1 中
     * 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。
     * 未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
     * 注：即arr1包含arr2所有元素，arr2元素重复
     * 示例：
     * 输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
     * 输出：[2,2,2,1,4,3,3,9,6,7,19]
     *
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    private int[] relativeSortArray(int[] arr1, int[] arr2) {
        if(null == arr1 || null == arr2)
            return arr1;

        // arr1数组已经排好序的下标
        int index = 0;
        for(int a2 : arr2) {
            for(int i = index; i < arr1.length; i++) {
                if(a2 == arr1[i]) {
                    if(i != index) {
                        // arr1中找到相同元素，进行交换
                        int temp = arr1[index];
                        arr1[index] = arr1[i];
                        arr1[i] = temp;
                    }
                    index++;
                }
            }
        }
        // 对arr1中剩余元素升序排序
        if(index < arr1.length - 1) {
            for(int i = index; i < arr1.length - 1; i++) {
                for(int j = i + 1; j < arr1.length; j++) {
                    if(arr1[i] > arr1[j]) {
                        int temp = arr1[i];
                        arr1[i] = arr1[j];
                        arr1[j] = temp;
                    }
                }
            }
        }
        return arr1;
    }

    private void printArr(int[] arr) {
        for(int num : arr) {
            System.out.print(num + " ");
        }
    }

    /**
     * leetcode#402 移掉K位数字
     * @author pujian
     * @date 2020/11/15 11:26
     * @param num
     * @param k
     * @return
     * 给定一个以字符串表示的非负整数 num，移除这个数中的 k 位数字，使得剩下的数字最小。
     * 注意:
     * num 的长度小于 10002 且 ≥ k。num 不会包含任何前导零。
     * 示例 1 :
     * 输入: num = "1432219", k = 3
     * 输出: "1219"
     * 解释: 移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219。
     *
     * 示例 2 :
     * 输入: num = "10200", k = 1
     * 输出: "200"
     * 解释: 移掉首位的 1 剩下的数字为 200. 注意输出不能有任何前导零。
     *
     * 示例 3 :
     * 输入: num = "10", k = 2
     * 输出: "0"
     * 解释: 从原数字移除所有的数字，剩余为空就是0。
     */
    private String removeKdigits(String num, int k) {
        if(null == num || "".equals(num)) {
            return "0";
        }
        if(num.contains("0")) {
            int zeroIndex = num.indexOf("0");
            String zeroBeforeStr = num.substring(0, zeroIndex);
            String zeroAfterStr = num.substring(zeroIndex+1);
            if(zeroBeforeStr.length() < k) {
                k = k - zeroBeforeStr.length();
                return removeKdigits(zeroAfterStr, k);
            } else if(zeroBeforeStr.length() == k) {
                return zeroAfterStr;
            } else {
                return removeKdigits(zeroBeforeStr, k) + zeroAfterStr;
            }
        } else {
            while (k > 0) {
                for(int i = 9; i >= 1; i--) {
                    String c = String.valueOf(i);
                    if(num.contains(c)) {
                        num = num.replaceFirst(c, "");
                        k--;
                        break;
                    }
                }
            }
        }
        return num;
    }
}
