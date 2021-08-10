package cn.zefre.tree.bitree;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

/**
 * @author pujian
 * @date 2021/8/10 21:34
 */
public class BinarySortTreeTest {

    private BinarySortTree<Integer> bitSortTree = new BinarySortTree<>();

    /*
     * 初始化一颗二叉排序树
     *                      34
     *              24              39
     *        18         28               64
     *    6           27    32        42      66
     *       14                          48
     *
     */
    {
        Integer[] nums = new Integer[]{34, 24, 18, 39, 28, 6, 64, 14, 42, 48, 32, 66, 27};
        for (Integer num : nums) {
            bitSortTree.add(num);
        }
    }

    @Test
    public void testPreOrder() {
        List<Integer> integers = bitSortTree.obtainByOrder(OrderEnum.IN_ORDER);
        System.out.println(integers);
    }

    @Test
    public void testGet() {
        BinaryTree.Node<Integer> node = bitSortTree.get(6);
        Assert.assertEquals(node.data, new Integer(6));
        Assert.assertNull(node.left.data);
        Assert.assertEquals(node.right.data, new Integer(14));
    }
}
