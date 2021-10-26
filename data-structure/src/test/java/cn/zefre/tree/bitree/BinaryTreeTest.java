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

    private final static BinaryTree<String> binaryTree = new BinaryTree<>();

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
        BinaryTree.BitNode<String> root = binaryTree.add("a", null, false);
        BinaryTree.BitNode<String> bNode = binaryTree.add("b", root, true);
        BinaryTree.BitNode<String> cNode = binaryTree.add("c", root, false);
        BinaryTree.BitNode<String> dNode = binaryTree.add("d", bNode, false);
        BinaryTree.BitNode<String> eNode = binaryTree.add("e", cNode, true);
        binaryTree.add("f", dNode, true);
        binaryTree.add("g", eNode, false);
    }

    @Test
    public void testInit() {
        BinaryTree<String> newBinaryTree = new BinaryTree<>(binaryTree);
        BinaryTree.BitNode<String> root = newBinaryTree.get("a");
        Assert.assertEquals("a", root.data);
        Assert.assertEquals("b", root.left.data);
        Assert.assertEquals("c", root.right.data);
    }

    @Test
    public void testGet() {
        BinaryTree.BitNode<String> leftNode = binaryTree.get("b");
        Assert.assertEquals("b", leftNode.data);
        Assert.assertEquals("d", leftNode.right.data);
    }

    @Test
    public void testPreOrder() {
        List<String> list = binaryTree.sequence(OrderEnum.PRE_ORDER);
        List<String> expectedList = new ArrayList<>(Arrays.asList("a", "b", "d", "f", "c", "e", "g"));
        Assert.assertEquals(expectedList, list);
    }

    @Test
    public void testInOrder() {
        List<String> list = binaryTree.sequence(OrderEnum.IN_ORDER);
        List<String> expectedList = new ArrayList<>(Arrays.asList("b", "f", "d", "a", "e", "g", "c"));
        Assert.assertEquals(expectedList, list);
    }

    @Test
    public void testPostOrder() {
        List<String> list = binaryTree.sequence(OrderEnum.POST_ORDER);
        List<String> expectedList = new ArrayList<>(Arrays.asList("f", "d", "b", "g", "e", "c", "a"));
        Assert.assertEquals(expectedList, list);
    }

    @Test
    public void testSequenceOrder() {
        List<String> list = binaryTree.sequence(OrderEnum.SEQUENCE);
        List<String> expectedList = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g"));
        Assert.assertEquals(expectedList, list);
    }

    /*@Test
    public void testClear() {
        BinaryTree<String> newBinaryTree = new BinaryTree<>(binaryTree);
        List<Node<String>> nodes = newBinaryTree.order(OrderEnum.SEQUENCE);
        Node<String> aNode = nodes.get(0);
        Node<String> bNode = nodes.get(1);
        Node<String> cNode = nodes.get(2);
        Node<String> dNode = nodes.get(3);
        Node<String> eNode = nodes.get(4);
        Node<String> fNode = nodes.get(5);
        Node<String> gNode = nodes.get(6);
        Assert.assertEquals(aNode.left(), bNode);
        Assert.assertEquals(aNode.right(), cNode);

        Assert.assertEquals(bNode.right(), dNode);

        Assert.assertEquals(cNode.left(), eNode);

        Assert.assertEquals(dNode.left(), fNode);

        Assert.assertEquals(eNode.right(), gNode);

        // clear the binary tree
        newBinaryTree.clear();

        Assert.assertNull(aNode.left());
        Assert.assertNull(aNode.right());
        Assert.assertNull(bNode.right());
        Assert.assertNull(cNode.left());
        Assert.assertNull(dNode.left());
        Assert.assertNull(eNode.left());
    }*/

    @Test
    public void testDepth() {
        int depth = binaryTree.depth();
        Assert.assertEquals(4, depth);
    }

    @Test
    public void testParent() {
        BinaryTree.BitNode<String> fNode = binaryTree.get("f");
        BinaryTree.BitNode<String> dNode = binaryTree.parent(fNode);
        Assert.assertEquals("d", dNode.data);
    }

    @Test
    public void testLeftChild() {
        BinaryTree.BitNode<String> cNode = binaryTree.get("c");
        BinaryTree.BitNode<String> eNode = binaryTree.leftChild(cNode);
        Assert.assertEquals("e", eNode.data);
    }

    @Test
    public void testRightSibling() {
        BinaryTree.BitNode<String> cNode = binaryTree.get("c");
        BinaryTree.BitNode<String> nullNode = binaryTree.rightSibling(cNode);
        Assert.assertNull(nullNode);
        BinaryTree.BitNode<String> bNode = binaryTree.get("b");
        Assert.assertEquals(cNode, binaryTree.rightSibling(bNode));
    }

    @Test
    public void testInsertAndDeleteChild() {
        BinaryTree<String> newBinaryTree = new BinaryTree<>(binaryTree);
        BinaryTree.BitNode<String> bNode = newBinaryTree.get("b");
        Assert.assertEquals(1, newBinaryTree.degree(bNode));
        Tree<BinaryTree.BitNode<String>> dfTree = newBinaryTree.deleteChild(bNode, 1);

        Assert.assertEquals(Arrays.asList("a", "b", "c", "e", "g"), newBinaryTree.sequence(OrderEnum.SEQUENCE));

        BinaryTree.BitNode<String> root = dfTree.root();
        Assert.assertEquals("d", root.data);
        Assert.assertEquals("f", root.left.data);

        newBinaryTree.insertChild(bNode, 1,  dfTree);
        Assert.assertEquals(Arrays.asList("a", "b", "c", "d", "e", "f", "g"), newBinaryTree.sequence(OrderEnum.SEQUENCE));

    }

}
