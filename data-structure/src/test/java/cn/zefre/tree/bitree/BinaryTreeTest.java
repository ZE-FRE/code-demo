package cn.zefre.tree.bitree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author pujian
 * @date 2021/7/20 17:23
 */
public class BinaryTreeTest {

    BinaryTree<String> binaryTree = null;

    /**
     * 初始化一棵二叉树
     * 树结构如图：
     *                  a
     *          b               c
     *     null   d          e    null
     *          f  null   null g
     *
     * @author pujian
     * @date 2021/7/21 10:52
     */
    @Before
    public void getNewBinaryTree() {
        BinaryTree<String> binaryTree = new BinaryTree<>();
        BinaryTree.Node<String> root = binaryTree.add("a", null, false);
        BinaryTree.Node<String> bNode = binaryTree.add("b", root, true);
        BinaryTree.Node<String> cNode = binaryTree.add("c", root, false);
        BinaryTree.Node<String> dNode = binaryTree.add("d", bNode, false);
        BinaryTree.Node<String> eNode = binaryTree.add("e", cNode, true);
        binaryTree.add("f", dNode, true);
        binaryTree.add("g", eNode, false);

        this.binaryTree = binaryTree;
    }

    @Test
    public void testInit() {
        BinaryTree<String> newBinaryTree = new BinaryTree<>(this.binaryTree);
        BinaryTree.Node<String> root = newBinaryTree.get("a");
        Assert.assertEquals("a", root.data);
        Assert.assertEquals("b", root.left.data);
        Assert.assertEquals("c", root.right.data);
    }

    @Test
    public void testGet() {
        BinaryTree.Node<String> leftNode = this.binaryTree.get("b");
        Assert.assertEquals("b", leftNode.data);
        Assert.assertEquals("d", leftNode.right.data);
    }

    @Test
    public void testPreOrder() {
        List<String> list = this.binaryTree.obtain(OrderEnum.PRE_ORDER);
        List<String> expectedList = new ArrayList<>(Arrays.asList("a", "b", "d", "f", "c", "e", "g"));
        Assert.assertEquals(list, expectedList);
    }

    @Test
    public void testInOrder() {
        List<String> list = this.binaryTree.obtain(OrderEnum.IN_ORDER);
        List<String> expectedList = new ArrayList<>(Arrays.asList("b", "f", "d", "a", "e", "g", "c"));
        Assert.assertEquals(list, expectedList);
    }

    @Test
    public void testPostOrder() {
        List<String> list = this.binaryTree.obtain(OrderEnum.POST_ORDER);
        List<String> expectedList = new ArrayList<>(Arrays.asList("f", "d", "b", "g", "e", "c", "a"));
        Assert.assertEquals(list, expectedList);
    }

}
