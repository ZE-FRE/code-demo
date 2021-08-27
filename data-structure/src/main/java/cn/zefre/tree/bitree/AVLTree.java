package cn.zefre.tree.bitree;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 平衡二叉树
 * 又叫AVL树、Self-Balancing Binary Search Tree、Height-Balanced Binary Search Tree
 *
 * @author pujian
 * @date 2021/8/13 14:36
 */
public class AVLTree<E extends Comparable<E>> {

    /**
     * AVL树结点
     */
    static class Node<E> {
        E data;
        int height;
        Node<E> left;
        Node<E> right;

        Node(E data) {
            this(data, 1);
        }

        Node(E data, int height) {
            this.data = data;
            this.height = height;
        }

        public E getData() {
            return this.data;
        }

    }

    /**
     * 计算结点的高度
     *
     * @param node 结点
     * @author pujian
     * @date 2021/8/25 13:41
     * @return 结点高度
     */
    private int calculateHeight(Node<?> node) {
        Objects.requireNonNull(node);
        int leftHeight = null == node.left ? 0 : node.left.height;
        int rightHeight = null == node.right ? 0 : node.right.height;
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * 计算结点的平衡因子
     *
     * @param node 结点
     * @author pujian
     * @date 2021/8/25 16:42
     * @return 结点平衡因子
     */
    private int calculateBalanceFactor(Node<?> node) {
        Objects.requireNonNull(node);
        int leftHeight = null == node.left ? 0 : node.left.height;
        int rightHeight = null == node.right ? 0 : node.right.height;
        return leftHeight - rightHeight;
    }

    /**
     * 根节点
     */
    private Node<E> root;

    /**
     * 查找元素结点
     *
     * @param elem 元素值
     * @author pujian
     * @date 2021/8/20 13:25
     * @return 元素结点
     */
    public Node<E> get(E elem) {
        if (null == elem) return null;
        Node<E> target = this.root;
        while (null != target) {
            if (target.data.compareTo(elem) == 0)
                return target;
            else if (elem.compareTo(target.data) < 0)
                target = target.left;
            else
                target = target.right;
        }
        return null;
    }

    /**
     * 插入元素
     *
     * @param elem 待插入元素
     * @author pujian
     * @date 2021/8/25 13:50
     * @return elem为null或elem已存在返回false，否则返回true
     */
    public boolean add(E elem) {
        if (null == elem) return false;
        if (null == this.root) {
            this.root = new Node<>(elem);
            return true;
        }
        return add(elem, this.root);
    }

    /**
     * 插入一个元素，插入后保持树的平衡
     *
     * @param elem 待插入元素
     * @param node 结点
     * @author pujian
     * @date 2021/8/25 17:30
     * @return 插入成功返回true，若存在相同元素，返回false
     */
    private boolean add(E elem, Node<E> node) {
        boolean addSuccess;
        if (elem.compareTo(node.data) == 0)
            return false;
        else if (elem.compareTo(node.data) < 0) {
            if (null == node.left) {
                node.left = new Node<>(elem);
                addSuccess = true;
            } else addSuccess = add(elem, node.left);

        } else {
            if (null == node.right) {
                node.right = new Node<>(elem);
                addSuccess = true;
            }
            else addSuccess = add(elem, node.right);
        }
        if (addSuccess) {
            node.height = calculateHeight(node);
            if (Math.abs(calculateBalanceFactor(node)) > 1)
                balance(node);
        }
        return addSuccess;
    }

    /**
     * 平衡子树
     *
     * @param node 待平衡子树根节点
     * @author pujian
     * @date 2021/8/25 17:29
     */
    private void balance(Node<E> node) {
        int nodeBf = calculateBalanceFactor(node);
        if (nodeBf > 1) { // 左子树高
            if (calculateBalanceFactor(node.left) > 0) {
                // LL型，右单旋
                rightRotate(node);
            } else if (calculateBalanceFactor(node.left) < 0){
                // LR型，先左旋，再右旋
                leftRotate(node.left);
                rightRotate(node);
            }
        } else if (nodeBf < -1) { // 右子树高
            if (calculateBalanceFactor(node.right) < 0) {
                // RR型，左单旋
                leftRotate(node);
            } else if (calculateBalanceFactor(node.right) > 0){
                // RL型，先右旋，再左旋
                rightRotate(node.right);
                leftRotate(node);
            }
        }
    }

    /**
     * 左旋
     *
     * @param node 被旋转子树根节点
     * @author pujian
     * @date 2021/8/25 17:28
     */
    private void leftRotate(Node<E> node) {
        E originalRootData = node.data;
        Node<E> right = node.right;
        node.data = right.data;
        node.right = right.right;
        right.data = originalRootData;
        right.right = right.left;
        right.left = node.left;
        node.left = right;
        node.left.height = calculateHeight(node.left);
        node.height = calculateHeight(node);
    }

    /**
     * 右旋
     *
     * @param node 被旋转子树根节点
     * @author pujian
     * @date 2021/8/25 17:28
     */
    private void rightRotate(Node<E> node) {
        E originalRootData = node.data;
        Node<E> left = node.left;
        node.data = left.data;
        node.left = left.left;
        left.data = originalRootData;
        left.left = left.right;
        left.right = node.right;
        node.right = left;
        node.right.height = calculateHeight(node.right);
        node.height = calculateHeight(node);
    }

    /**
     * 中序遍历
     *
     * @author pujian
     * @date 2021/8/25 17:38
     * @return 中序遍历元素集合
     */
    public List<E> inOrder() {
        List<E> resultList = new ArrayList<>();
        inOrder(this.root, resultList);
        return resultList;
    }

    /**
     * 中序遍历
     *
     * @param node 当前遍历结点
     * @param list 中序遍历元素集合
     * @author pujian
     * @date 2021/8/25 17:38
     */
    private void inOrder(Node<E> node, List<E> list) {
        if (null == node) return;
        inOrder(node.left, list);
        list.add(node.data);
        inOrder(node.right, list);
    }

    /**
     * 层序遍历
     *
     * @author pujian
     * @date 2021/8/27 10:41
     * @return 层序遍历元素集合
     */
    public List<E> sequenceOrder() {
        List<Node<E>> resultList = new ArrayList<>();
        if (null != this.root) {
            resultList.add(this.root);
        }
        Node<E> node;
        for (int i = 0; i < resultList.size(); i++) {
            node = resultList.get(i);
            if (null != node.left)
                resultList.add(node.left);
            if (null != node.right)
                resultList.add(node.right);
        }
        return resultList.stream().map(Node::getData).collect(Collectors.toList());
    }

}
