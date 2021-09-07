package cn.zefre.tree.bitree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 二叉树遍历工具
 *
 * @author pujian
 * @date 2021/9/6 16:59
 */
public class BinaryTreeOrder<E> {

    /**
     * 根据指定顺序遍历二叉树，得到元素集合
     *
     * @param root 二叉树根节点
     * @param order 遍历顺序 {@link cn.zefre.tree.bitree.OrderEnum}
     * @author pujian
     * @date 2021/7/21 14:42
     * @return 遍历结果元素集合
     */
    public List<E> sequence(Node<E> root, OrderEnum order) {
        List<Node<E>> list = order(root, order);
        return list.stream().map(Node::getData).collect(Collectors.toList());
    }

    /**
     * 根据指定顺序遍历二叉树，得到结点集合
     *
     * @param root 二叉树根节点
     * @param order 遍历顺序 {@link cn.zefre.tree.bitree.OrderEnum}
     * @author pujian
     * @date 2021/7/21 10:41
     * @return 遍历结果结点集合
     */
    public List<Node<E>> order(Node<E> root, OrderEnum order) {
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
                orderList = sequenceOrder(root);
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
        preOrder(node.left(), preOrderList);
        preOrder(node.right(), preOrderList);
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
        inOrder(node.left(), preOrderList);
        preOrderList.add(node);
        inOrder(node.right(), preOrderList);
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
        postOrder(node.left(), preOrderList);
        postOrder(node.right(), preOrderList);
        preOrderList.add(node);
    }

    /**
     * 层序遍历二叉树
     *
     * @param root 二叉树根节点
     * @author pujian
     * @date 2021/7/27 10:25
     * @return 层序遍历结果集
     */
    private List<Node<E>> sequenceOrder(Node<E> root) {
        List<Node<E>> sequenceList = new ArrayList<>();
        if (null != root) {
            sequenceList.add(root);
        }
        for (int i = 0; i < sequenceList.size(); i++) {
            Node<E> node = sequenceList.get(i);
            if (null != node.left())
                sequenceList.add(node.left());
            if (null != node.right())
                sequenceList.add(node.right());
        }
        return sequenceList;
    }

}
