package cn.zefre.tree.bitree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 二叉树
 *
 * @author pujian
 * @date 2020/5/29 16:49
 */
public class BinaryTree<E> {

    static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        Node() {}

        Node(E data) {
            this.data = data;
        }

        E getData() {
            return data;
        }
    }

    /**
     * 根节点
     */
    public Node<E> root;

    public BinaryTree() { }

    public BinaryTree(BinaryTree<? extends E> bTree) {
        if (null != bTree)
            this.root = this.init(bTree.root);
    }

    private Node<E> init(Node<? extends E> node) {
        if (null == node) return null;
        Node<E> newNode = new Node<>(node.data);
        newNode.left = init(node.left);
        newNode.right = init(node.right);
        return newNode;
    }

    public void destroy() {
        destroy(root);
    }

    private void destroy(Node<E> node) {
        if (null == node) return;
        destroy(node.left);
        destroy(node.right);
        node.left = node.right = null;
    }

    public Node<E> add(E elem, Node<E> node, boolean addLeft){
        if (null == node) {
            this.root = new Node<>(elem);
            return this.root;
        }

        Node<E> newNode = new Node<>(elem);
        Node<E> temp;
        if (addLeft) {
            temp = node.left;
            node.left = newNode;
        } else {
            temp = node.right;
            node.right = newNode;
        }
        if (null != temp) {
            newNode.left = temp.left;
            newNode.right = temp.right;
            temp.left = null;
            temp.right = null;
        }
        return newNode;
    }

    public Node<E> get(E elem) {
        return this.get(elem, root);
    }

    private Node<E> get(Object elem, Node<E> node) {
        if (null == elem || null == node) return null;
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
    public List<E> obtain(OrderEnum order) {
        List<Node<E>> list = order(order);
        return list.stream().map(Node::getData).collect(Collectors.toList());
    }

    /**
     * 根据指定顺序遍历二叉树，得到节点集合
     *
     * @param order 遍历顺序 {@link cn.zefre.tree.bitree.OrderEnum}
     * @author pujian
     * @date 2021/7/21 10:41
     * @return 遍历结果节点集合
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
            default:
                break;
        }
        return orderList;
    }

    /**
     * 先序遍历二叉树
     *
     * @param node 遍历节点
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
     * @param node 遍历节点
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
     * @param node 遍历节点
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

}
