package cn.zefre.tree.bitree;

import cn.zefre.tree.Tree;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author pujian
 * @date 2021/7/20 17:23
 */
public class BinaryTreeTest {

    private static BinaryTree<String> binaryTree = null;

    /*
     * 初始化一棵二叉树
     * 树结构如图：
     *                  a
     *          b               c
     *     null   d          e    null
     *          f  null   null g
     *
     */
    static {
        BinaryTreeTest.binaryTree = new BinaryTree<>();
        BinaryTree.Node<String> root = binaryTree.add("a", null, false);
        BinaryTree.Node<String> bNode = binaryTree.add("b", root, true);
        BinaryTree.Node<String> cNode = binaryTree.add("c", root, false);
        BinaryTree.Node<String> dNode = binaryTree.add("d", bNode, false);
        BinaryTree.Node<String> eNode = binaryTree.add("e", cNode, true);
        binaryTree.add("f", dNode, true);
        binaryTree.add("g", eNode, false);
    }

    @Test
    public void testInit() {
        BinaryTree<String> newBinaryTree = new BinaryTree<>(BinaryTreeTest.binaryTree);
        BinaryTree.Node<String> root = newBinaryTree.get("a");
        Assert.assertEquals("a", root.getData());
        Assert.assertEquals("b", root.left.getData());
        Assert.assertEquals("c", root.right.getData());
    }

    @Test
    public void testGet() {
        BinaryTree.Node<String> leftNode = BinaryTreeTest.binaryTree.get("b");
        Assert.assertEquals("b", leftNode.getData());
        Assert.assertEquals("d", leftNode.right.getData());
    }

    @Test
    public void testPreOrder() {
        List<String> list = BinaryTreeTest.binaryTree.obtainByOrder(OrderEnum.PRE_ORDER);
        List<String> expectedList = new ArrayList<>(Arrays.asList("a", "b", "d", "f", "c", "e", "g"));
        Assert.assertEquals(list, expectedList);
    }

    @Test
    public void testInOrder() {
        List<String> list = BinaryTreeTest.binaryTree.obtainByOrder(OrderEnum.IN_ORDER);
        List<String> expectedList = new ArrayList<>(Arrays.asList("b", "f", "d", "a", "e", "g", "c"));
        Assert.assertEquals(list, expectedList);
    }

    @Test
    public void testPostOrder() {
        List<String> list = BinaryTreeTest.binaryTree.obtainByOrder(OrderEnum.POST_ORDER);
        List<String> expectedList = new ArrayList<>(Arrays.asList("f", "d", "b", "g", "e", "c", "a"));
        Assert.assertEquals(list, expectedList);
    }

    @Test
    public void testSequenceOrder() {
        List<String> list = BinaryTreeTest.binaryTree.obtainByOrder(OrderEnum.SEQUENCE);
        List<String> expectedList = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g"));
        Assert.assertEquals(list, expectedList);
    }

    @Test
    public void testClear() {
        BinaryTree<String> newBinaryTree = new BinaryTree<>(BinaryTreeTest.binaryTree);
        List<BinaryTree.Node<String>> nodes = newBinaryTree.order(OrderEnum.SEQUENCE);
        BinaryTree.Node<String> aNode = nodes.get(0);
        BinaryTree.Node<String> bNode = nodes.get(1);
        BinaryTree.Node<String> cNode = nodes.get(2);
        BinaryTree.Node<String> dNode = nodes.get(3);
        BinaryTree.Node<String> eNode = nodes.get(4);
        BinaryTree.Node<String> fNode = nodes.get(5);
        BinaryTree.Node<String> gNode = nodes.get(6);
        Assert.assertEquals(aNode.left, bNode);
        Assert.assertEquals(aNode.right, cNode);

        Assert.assertEquals(bNode.right, dNode);

        Assert.assertEquals(cNode.left, eNode);

        Assert.assertEquals(dNode.left, fNode);

        Assert.assertEquals(eNode.right, gNode);

        // clear the binary tree
        newBinaryTree.clear();

        Assert.assertNull(aNode.left);
        Assert.assertNull(aNode.right);
        Assert.assertNull(bNode.right);
        Assert.assertNull(cNode.left);
        Assert.assertNull(dNode.left);
        Assert.assertNull(eNode.left);
    }

    @Test
    public void testDepth() {
        int depth = BinaryTreeTest.binaryTree.depth();
        Assert.assertEquals(4, depth);
    }

    @Test
    public void testParent() {
        BinaryTree.Node<String> fNode = BinaryTreeTest.binaryTree.get("f");
        BinaryTree.Node<String> dNode = BinaryTreeTest.binaryTree.parent(fNode);
        Assert.assertEquals("d", dNode.getData());
    }

    @Test
    public void testLeftChild() {
        BinaryTree.Node<String> cNode = BinaryTreeTest.binaryTree.get("c");
        BinaryTree.Node<String> eNode = BinaryTreeTest.binaryTree.leftChild(cNode);
        Assert.assertEquals("e", eNode.getData());
    }

    @Test
    public void testRightSibling() {
        BinaryTree.Node<String> cNode = BinaryTreeTest.binaryTree.get("c");
        BinaryTree.Node<String> nullNode = BinaryTreeTest.binaryTree.rightSibling(cNode);
        Assert.assertNull(nullNode);
        BinaryTree.Node<String> bNode = BinaryTreeTest.binaryTree.get("b");
        Assert.assertEquals(cNode, BinaryTreeTest.binaryTree.rightSibling(bNode));
    }

    @Test
    public void testInsertAndDeleteChild() {
        BinaryTree<String> newBinaryTree = new BinaryTree<>(BinaryTreeTest.binaryTree);
        BinaryTree.Node<String> bNode = newBinaryTree.get("b");
        Assert.assertEquals(1, newBinaryTree.degree(bNode));
        Tree<BinaryTree.Node<String>> dfTree = newBinaryTree.deleteChild(bNode, 1);

        Assert.assertEquals(new ArrayList<>(Arrays.asList("a", "b", "c", "e", "g")), newBinaryTree.obtainByOrder(OrderEnum.SEQUENCE));

        BinaryTree.Node<String> root = dfTree.root();
        Assert.assertEquals("d", root.getData());
        Assert.assertEquals("f", root.left.getData());

        newBinaryTree.insertChild(bNode, 1,  dfTree);
        List<String> expectedList = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g"));
        Assert.assertEquals(expectedList, newBinaryTree.obtainByOrder(OrderEnum.SEQUENCE));

    }

}
