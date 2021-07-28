package cn.zefre.tree;

import lombok.Data;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 基于双亲孩子表示法实现树
 *
 * @author pujian
 * @date 2021/7/22 15:21
 */
@Data
public class ChildrenBasedTree<E> implements Tree<ChildrenBasedTree.Node<E>> {

    private static final int INITIAL_CAPACITY = 10;

    /**
     * 用数组存放树节点
     */
    private Node<E>[] tree;

    /**
     * 节点数
     */
    private int nodeCount;

    /**
     * 根节点
     */
    private Node<E> root;

    public ChildrenBasedTree(Class<E> clazz) {
        this(clazz, INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ChildrenBasedTree(Class<E> clazz, int initialSize) {
        this.tree = (Node<E>[]) Array.newInstance(clazz, Math.max(INITIAL_CAPACITY, initialSize));
    }

    @SuppressWarnings("unchecked")
    public ChildrenBasedTree(Tree<ChildrenBasedTree.Node<E>> tree) {
        this((Class<E>) Object.class);
        if (null != tree && !tree.isEmpty()) {
            Class<?> elemClass = tree.root().data.getClass();
            this.tree = (Node<E>[]) Array.newInstance(elemClass, tree.nodeCount());
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isEmpty() {
        return null == root;
    }

    @Override
    public int depth() {
        return 0;
    }

    @Override
    public Node<E> root() {
        return root;
    }

    @Override
    public Node<E> parent(Node<E> node) {
        Objects.requireNonNull(node);
        return -1 == node.parentIndex ? null : tree[node.parentIndex];
    }

    @Override
    public Node<E> leftChild(Node<E> node) {
        Objects.requireNonNull(node);
        return Optional.ofNullable(node.firstChild)
                .map(ChildNode::getIndex)
                .map(index -> tree[index])
                .orElse(null);
    }

    @Override
    public Node<E> rightSibling(Node<E> node) {
        Node<E> parent = parent(node);
        return Optional.ofNullable(parent)
                .map(Node::getFirstChild)
                .map(ChildNode::getNext)
                .map(ChildNode::getIndex)
                .map(index -> tree[index])
                .orElse(null);
    }

    @Override
    public void insertChild(Node<E> node, int i, Tree<Node<E>> insertedTree) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(insertedTree);
        checkIndex(node, i);

    }

    @Override
    public Tree<Node<E>> deleteChild(Node<E> node, int i) {
        if (null == node)
            return null;
        checkIndex(node, i);
        ChildNode prevChild;
        ChildNode deletedNode;
        if (i == 1) {
            deletedNode = node.firstChild;
            node.firstChild = deletedNode.next;
        } else {
            prevChild = node.firstChild;
            for (int c = 1; c < i - 1; c++) {
                prevChild = prevChild.next;
            }
            deletedNode = prevChild.next;
            prevChild.next = deletedNode.next;
        }
        deletedNode.next = null;
        for (int c = 1; c <= degree(tree[deletedNode.index]); c++) {
            deleteChild(tree[deletedNode.index], c);
        }
        tree[deletedNode.index] = null;
        return null;
    }

    @Override
    public int nodeCount() {
        return this.nodeCount;
    }

    @Override
    public int degree(Node<E> node) {
        Objects.requireNonNull(node);
        int degree = 0;
        ChildNode child = node.firstChild;
        while (null != child) {
            degree++;
            child = child.next;
        }
        return degree;
    }

    private void checkIndex(Node<E> node, int i) {
        if (i < 1 || i > degree(node))
            throw new IllegalArgumentException("unexpected value of i: " + i);
    }

    /**
     * 层序遍历
     *
     * @author pujian
     * @date 2021/7/26 16:43
     * @return 层序遍历元素结果集
     */
    public List<E> sequence() {
        if (null == root)
            return null;
        else if (null == root.firstChild)
            return new ArrayList<>(Collections.singletonList(root.data));
        List<Node<E>> sequenceList = new ArrayList<>(nodeCount);
        sequenceList.add(root);
        sequence(root, sequenceList);
        return sequenceList.stream().map(Node::getData).collect(Collectors.toList());
    }

    private void sequence(Node<E> node, List<Node<E>> sequenceList) {
        List<Node<E>> childNodes = new LinkedList<>();
        ChildNode child = node.firstChild;
        while (null != child) {
            childNodes.add(tree[child.index]);
            child = child.next;
        }
        sequenceList.addAll(childNodes);
        childNodes.forEach(childNode -> sequence(childNode, sequenceList));
    }

    @Data
    static class Node<E> {
        /**
         * 元素值
         */
        E data;
        /**
         * 父节点下标
         */
        int parentIndex;
        /**
         * 第一个孩子节点
         */
        ChildNode firstChild;
    }

    @Data
    static class ChildNode {
        /**
         * 节点在数组的下标
         */
        int index;
        /**
         * 下一个孩子节点
         */
        ChildNode next;
    }

}
