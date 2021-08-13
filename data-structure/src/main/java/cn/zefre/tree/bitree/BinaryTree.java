package cn.zefre.tree.bitree;

import cn.zefre.tree.Tree;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * 二叉树
 *
 * @author pujian
 * @date 2020/5/29 16:49
 */
public class BinaryTree<E> implements Tree<BinaryTree.Node<E>> {

    static class Node<E> {
        protected E data;
        protected Node<E> left;
        protected Node<E> right;

        Node(E data) {
            this.data = data;
        }

        E getData() {
            return data;
        }
    }

    /**
     * 根结点
     */
    protected Node<E> root;

    public BinaryTree() { }

    public BinaryTree(Node<E> root) {
        this.root = root;
    }

    public BinaryTree(BinaryTree<E> bTree) {
        if (null != bTree)
            this.root = this.init(bTree.root);
    }

    private Node<E> init(Node<E> node) {
        if (null == node) return null;
        Node<E> newNode = new Node<>(node.data);
        newNode.left = init(node.left);
        newNode.right = init(node.right);
        return newNode;
    }

    @Override
    public void clear() {
        clear(root);
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public int depth() {
        if (isEmpty())
            return 0;

        int depth = 0;
        Queue<Node<E>> currentLayer = new LinkedList<>();
        currentLayer.add(this.root);
        Queue<Node<E>> nextLayer = new LinkedList<>();
        Node<E> node;
        while (null != (node = currentLayer.poll())) {
            if (null != node.left)
                nextLayer.offer(node.left);
            if (null != node.right)
                nextLayer.offer(node.right);
            if (currentLayer.isEmpty()) {
                depth++;
                // swap
                Queue<Node<E>> temp = currentLayer;
                currentLayer = nextLayer;
                nextLayer = temp;
            }
        }
        return depth;
    }

    @Override
    public Node<E> root() {
        return this.root;
    }

    @Override
    public Node<E> parent(Node<E> node) {
        if (null == node || isEmpty())
            return null;
        List<Node<E>> sequenceList = new LinkedList<>();
        sequenceList.add(root);
        for (int i = 0; i < sequenceList.size(); i++) {
            Node<E> parentNode = sequenceList.get(i);
            if (node.equals(parentNode.left) || node.equals(parentNode.right))
                return parentNode;
            if (null != parentNode.left)
                sequenceList.add(parentNode.left);
            if (null != parentNode.right)
                sequenceList.add(parentNode.right);
        }
        return null;
    }

    @Override
    public Node<E> leftChild(Node<E> node) {
        return null == node ? null : node.left;
    }

    @Override
    public Node<E> rightSibling(Node<E> node) {
        Node<E> parent = parent(node);
        return null == parent ? null : parent.right == node ? null : parent.right;
    }

    @Override
    public void insertChild(Node<E> node, int i, Tree<Node<E>> insertedTree) {
        if (null == node || isEmpty())
            return;
        if (i <= 0 || i > degree(node) + 1)
            throw new IllegalArgumentException("0 < i <= " + degree(node) + 1);
        if (i == 1) {
            if (null != node.left) {
                throw new UnsupportedOperationException("结点左子树不为空，不允许在此位置插入子树");
            }
            node.left = insertedTree.root();
        } else {
            if (null != node.right) {
                throw new UnsupportedOperationException("结点右子树不为空，不允许在此位置插入子树");
            }
            node.right = insertedTree.root();
        }
    }

    @Override
    public Tree<Node<E>> deleteChild(Node<E> node, int i) {
        if (null == node || isEmpty())
            return null;
        if (i <= 0 || i > degree(node))
            throw new IllegalArgumentException("0 < i <= " + degree(node));
        Node<E> deletedTree;
        boolean deleteLeft = i == 1 && null != node.left;
        if (deleteLeft) {
            deletedTree = node.left;
            node.left = null;
        } else {
            deletedTree = node.right;
            node.right = null;
        }
        return new BinaryTree<>(deletedTree);
    }

    @Override
    public int nodeCount() {
        List<Node<E>> nodes = sequenceOrder();
        return nodes.size();
    }

    @Override
    public int degree(Node<E> node) {
        int degree = 0;
        if (null != node) {
            if (null != node.left)
                degree++;
            if (null != node.right)
                degree++;
        }
        return degree;
    }

    private void clear(Node<E> node) {
        if (null == node) return;
        clear(node.left);
        clear(node.right);
        node.left = node.right = null;
    }

    public Node<E> add(E elem, Node<E> node, boolean addLeft) {
        if (null == node) {
            this.root = new Node<>(elem);
            return this.root;
        }

        Node<E> newNode = new Node<>(elem);
        Node<E> temp;
        if (addLeft) {
            temp = node.left;
            node.left = newNode;
            newNode.left = temp;
        } else {
            temp = node.right;
            node.right = newNode;
            newNode.right = temp;
        }
        return newNode;
    }

    public Node<E> get(E elem) {
        if (null == elem) return null;
        return this.get(elem, root);
    }

    private Node<E> get(E elem, Node<E> node) {
        if (null == node) return null;
        if (elem.equals(node.data)) return node;
        Node<E> result = get(elem, node.left);
        if (null != result) return result;
        return get(elem, node.right);
    }

    /**
     * 根据指定顺序遍历二叉树，得到元素集合
     *
     * @param order 遍历顺序 {@link cn.zefre.tree.bitree.OrderEnum}
     * @author pujian
     * @date 2021/7/21 14:42
     * @return 遍历结果元素集合
     */
    public List<E> obtainByOrder(OrderEnum order) {
        List<Node<E>> list = order(order);
        return list.stream().map(Node::getData).collect(Collectors.toList());
    }

    /**
     * 根据指定顺序遍历二叉树，得到结点集合
     *
     * @param order 遍历顺序 {@link cn.zefre.tree.bitree.OrderEnum}
     * @author pujian
     * @date 2021/7/21 10:41
     * @return 遍历结果结点集合
     */
    public List<Node<E>> order(OrderEnum order) {
        List<Node<E>> orderList = new ArrayList<>();
        switch (order) {
            case PRE_ORDER:
                preOrder(root, orderList);
                break;
            case IN_ORDER:
                inOrder(root, orderList);
                break;
            case POST_ORDER:
                postOrder(root, orderList);
                break;
            case SEQUENCE:
                orderList = sequenceOrder();
            default:
                break;
        }
        return orderList;
    }

    /**
     * 先序遍历二叉树
     *
     * @param node 遍历结点
     * @param preOrderList 遍历结果集合
     * @author pujian
     * @date 2021/7/21 10:28
     */
    private void preOrder(Node<E> node, List<Node<E>> preOrderList) {
        if (null == node) return;
        preOrderList.add(node);
        preOrder(node.left, preOrderList);
        preOrder(node.right, preOrderList);
    }

    /**
     * 中序遍历二叉树
     *
     * @param node 遍历结点
     * @param preOrderList 遍历结果集合
     * @author pujian
     * @date 2021/7/21 10:31
     */
    private void inOrder(Node<E> node, List<Node<E>> preOrderList) {
        if (null == node) return;
        inOrder(node.left, preOrderList);
        preOrderList.add(node);
        inOrder(node.right, preOrderList);
    }

    /**
     * 后序遍历二叉树
     *
     * @param node 遍历结点
     * @param preOrderList 遍历结果集合
     * @author pujian
     * @date 2021/7/21 10:31
     */
    private void postOrder(Node<E> node, List<Node<E>> preOrderList) {
        if (null == node) return;
        postOrder(node.left, preOrderList);
        postOrder(node.right, preOrderList);
        preOrderList.add(node);
    }

    /**
     * 层序遍历二叉树
     *
     * @author pujian
     * @date 2021/7/27 10:25
     * @return 层序遍历结果集
     */
    private List<Node<E>> sequenceOrder() {
        List<Node<E>> sequenceList = new LinkedList<>();
        if (null != this.root) {
            sequenceList.add(this.root);
        }
        for (int i = 0; i < sequenceList.size(); i++) {
            Node<E> node = sequenceList.get(i);
            if (null != node.left)
                sequenceList.add(node.left);
            if (null != node.right)
                sequenceList.add(node.right);
        }
        return sequenceList;
    }

}
