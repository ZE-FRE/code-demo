package cn.zefre.tree.bitree;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

/**
 * @author pujian
 * @date 2021/8/10 21:34
 */
public class BinarySortTreeTest {

    private static final BinarySortTree<Integer> bitSortTree = new BinarySortTree<>();

    /*
     * 初始化一颗二叉排序树
     *                      34
     *              24              39
     *        18         28               64
     *    6           27    32        42      66
     *       14                          48
     *
     */
    static {
        Integer[] nums = new Integer[]{34, 24, 18, 39, 28, 6, 64, 14, 42, 48, 32, 66, 27};
        for (Integer num : nums) {
            bitSortTree.add(num);
        }
    }

    @Test
    public void testInOrder() {
        List<Integer> integers = bitSortTree.obtainByOrder(OrderEnum.IN_ORDER);
        List<Integer> expected = Arrays.asList(6, 14, 18, 24, 27, 28, 32, 34, 39, 42, 48, 64, 66);
        Assert.assertEquals(expected, integers);
    }

    @Test
    public void testFind() {
        BinaryTree.Node<Integer> node = bitSortTree.find(6);
        Assert.assertEquals(Integer.valueOf(6), node.data);
        Assert.assertNull(node.left);
        Assert.assertEquals(Integer.valueOf(14), node.right.data);
    }

    @Test
    public void testGet() {
        BinaryTree.Node<Integer> node = bitSortTree.get(6);
        Assert.assertEquals(Integer.valueOf(6), node.data);
        Assert.assertNull(node.left);
        Assert.assertEquals(Integer.valueOf(14), node.right.data);
    }

    @Test
    public void testInsert() {
        BinarySortTree<Integer> bsTree = new BinarySortTree<>();
        for (Integer num : Arrays.asList(34, 24, 18, 39, 28, 6, 64, 14, 42, 48, 32, 66, 27)) {
            bsTree.insert(num);
        }
        List<Integer> expected = Arrays.asList(6, 14, 18, 24, 27, 28, 32, 34, 39, 42, 48, 64, 66);
        Assert.assertEquals(expected, bsTree.obtainByOrder(OrderEnum.IN_ORDER));
    }

    @Test
    public void testRemove() {
        BinarySortTree<Integer> binarySortTree = new BinarySortTree<>(bitSortTree);
        Assert.assertFalse(binarySortTree.remove(null));
        // 测试删除不存在结点
        Assert.assertFalse(binarySortTree.remove(100));
        // 测试删除结点是根节点
        binarySortTree.remove(34);
        Assert.assertEquals(Integer.valueOf(32), binarySortTree.root().getData());
        // 测试删除结点无左子树
        binarySortTree.remove(39);
        Assert.assertEquals(Integer.valueOf(64), binarySortTree.root.right.getData());
        // 测试删除结点无右子树
        binarySortTree.remove(18);
        Assert.assertEquals(Integer.valueOf(6), binarySortTree.root.left.left.getData());
        // 测试删除结点左、右子树都存在
        // ①测试删除结点直接前驱是左结点(左节点无右子树)
        binarySortTree.remove(28);
        Assert.assertEquals(Integer.valueOf(27), binarySortTree.root.left.right.getData());
        // ②测试删除结点直接前驱不是左节点(左节点有右子树)
        binarySortTree.remove(64);
        Assert.assertEquals(Integer.valueOf(48), binarySortTree.root.right.getData());
        // 测试删除完成后树结构
        Assert.assertEquals(Arrays.asList(6, 14, 24, 27, 32, 42, 48, 66), binarySortTree.obtainByOrder(OrderEnum.IN_ORDER));

    }

    @Test
    public void testDelete() {
        BinarySortTree<Integer> binarySortTree = new BinarySortTree<>(bitSortTree);
        Assert.assertFalse(binarySortTree.delete(null));
        // 测试删除不存在结点
        Assert.assertFalse(binarySortTree.delete(100));
        // 测试删除结点是根节点
        binarySortTree.delete(34);
        Assert.assertEquals(Integer.valueOf(39), binarySortTree.root().getData());
        // 测试删除叶子结点
        binarySortTree.delete(66);
        Assert.assertNull(binarySortTree.get(64).right);
        // 测试删除结点无左子树
        binarySortTree.delete(42);
        Assert.assertEquals(Integer.valueOf(48), binarySortTree.get(64).left.getData());
        // 测试删除结点无右子树
        binarySortTree.delete(18);
        Assert.assertEquals(Integer.valueOf(6), binarySortTree.get(24).left.getData());
        // 测试删除结点左、右子树都存在
        // ①测试删除结点直接前驱不是左节点(左节点有右子树)
        binarySortTree.delete(24);
        Assert.assertEquals(Integer.valueOf(27), binarySortTree.root.left.getData());
        // ②测试删除结点直接后继是右结点(右节点无左子树)
        binarySortTree.delete(27);
        Assert.assertEquals(Integer.valueOf(28), binarySortTree.root.left.getData());
        // 测试删除完成后树结构
        Assert.assertEquals(Arrays.asList(39, 28, 64, 6, 32, 48, 14), binarySortTree.obtainByOrder(OrderEnum.SEQUENCE));

    }
}
