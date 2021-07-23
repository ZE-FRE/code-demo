package cn.zefre.tree;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Optional;

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

    @SuppressWarnings("unchecked")
    public ChildrenBasedTree(Class<E> clazz) {
        this.tree = (Node<E>[]) Array.newInstance(clazz, INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ChildrenBasedTree(Tree<ChildrenBasedTree.Node<E>> tree) {
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
                .map(ChildNode::getChildIndex)
                .map(index -> tree[index])
                .orElse(null);
    }

    @Override
    public Node<E> rightSibling(Node<E> node) {
        Node<E> parent = parent(node);
        return Optional.ofNullable(parent)
                .map(Node::getFirstChild)
                .map(ChildNode::getNext)
                .map(ChildNode::getChildIndex)
                .map(index -> tree[index])
                .orElse(null);
    }

    @Override
    public void insertChild(Node<E> node, int degree, Tree<Node<E>> insertedTree) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(insertedTree);
        if (nodeCount + insertedTree.nodeCount() > tree.length) {
            // 扩容
            // TODO
        }
        // TODO

    }

    @Override
    public void deleteChild(Tree<Node<E>> node, int i) {

    }

    @Override
    public int nodeCount() {
        return this.nodeCount;
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
        int childIndex;
        /**
         * 下一个孩子节点
         */
        ChildNode next;
    }

}
