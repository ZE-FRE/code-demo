package cn.zefre.tree.bitree;

import cn.zefre.tree.bitree.ThreadedBinaryTree.ThreadNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pujian
 * @date 2023/3/29 15:25
 */
public class ThreadedBinaryTreeTest {

    private static ThreadedBinaryTree<Character> threadedBinaryTree;

    /**
     * 初始化这样一棵树
     *         f
     *     /      \
     *    c        i
     *  /   \    /   \
     * a     e  h     j
     *  \   /  /       \
     *   b d  g         k
     *
     * @author pujian
     * @date 2023/3/29 15:30
     */
    @BeforeAll
    public static void init() {
        ThreadNode<Character> root = new ThreadNode<>('f');
        ThreadNode<Character> cNode = new ThreadNode<>('c');
        ThreadNode<Character> iNode = new ThreadNode<>('i');
        root.left = cNode;
        root.right = iNode;
        ThreadNode<Character> aNode = new ThreadNode<>('a');
        ThreadNode<Character> eNode = new ThreadNode<>('e');
        cNode.left = aNode;
        cNode.right = eNode;
        ThreadNode<Character> bNode = new ThreadNode<>('b');
        ThreadNode<Character> dNode = new ThreadNode<>('d');
        aNode.right = bNode;
        eNode.left = dNode;

        ThreadNode<Character> hNode = new ThreadNode<>('h');
        ThreadNode<Character> jNode = new ThreadNode<>('j');
        iNode.left = hNode;
        iNode.right = jNode;
        ThreadNode<Character> gNode = new ThreadNode<>('g');
        ThreadNode<Character> kNode = new ThreadNode<>('k');
        hNode.left = gNode;
        jNode.right = kNode;

        threadedBinaryTree = new ThreadedBinaryTree<>(root);
    }

    @Test
    public void testInOrderFromFirst() {
        threadedBinaryTree.inOrderThreading();
        List<Character> list = new ArrayList<>();
        threadedBinaryTree.inOrderFromFirst(list::add);
        Character[] expected = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'};
        Assertions.assertArrayEquals(expected, list.toArray());
    }

    @Test
    public void testInOrderFromLast() {
        threadedBinaryTree.inOrderThreading();
        List<Character> list = new ArrayList<>();
        threadedBinaryTree.inOrderFromLast(list::add);
        Character[] expected = {'k', 'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'};
        Assertions.assertArrayEquals(expected, list.toArray());
    }

}
