package cn.zefre.tree.bitree;

import java.util.Objects;

/**
 * 二叉排序树
 * 元素不允许重复
 *
 * @author pujian
 * @date 2021/8/9 16:00
 */
public class BinarySortTree<E extends Comparable<E>> extends BinaryTree<E> {

    /**
     * 查找元素对应的结点
     * 若找到元素，则返回对应结点，否则返回null
     *
     * @param elem 查找元素
     * @author pujian
     * @date 2021/8/10 13:11
     * @return 元素对应结点或null
     */
    public Node<E> get(E elem) {
        if (null == elem) return null;
        Node<E> result = this.root;
        return get(elem, result) ? result : null;
    }

    /**
     * 递归查找元素对应结点
     *
     * @param elem 查找元素
     * @param node 若找到元素，则为元素对应结点，否则为树最后访问结点
     * @author pujian
     * @date 2021/8/10 13:20
     * @return 找到elem返回true，否则返回false
     */
    private boolean get(E elem, Node<E> node) {
        // 未查找到元素结点
        if (null == node) return false;
        // 找到了对应元素结点
        if (elem.compareTo(node.data) == 0)
            return true;
        // 小于当前结点，准备在左子树递归查找
        else if (elem.compareTo(node.data) < 0)
            node = node.left;
        // 大于当前结点，准备在右子树递归查找
        else
            node = node.right;
        // 递归查找
        return get(elem, node);
    }

    /**
     * 递归查找元素对应结点
     *
     * @param elem 查找元素
     * @param node 若找到元素，则为元素对应结点，否则为树最后访问结点
     * @param lastNode 上一次
     * @author pujian
     * @date 2021/8/10 13:20
     * @return 找到elem返回true，否则返回false
     */
    private Node<E> get(E elem, Node<E> node, Node<E> lastNode) {
        // 未查找到元素结点
        if (null == node) return lastNode;
        // 找到了对应元素结点
        if (elem.compareTo(node.data) == 0)
            return lastNode;
        // 小于当前结点，在左子树递归查找
        else if (elem.compareTo(node.data) < 0) {
            lastNode = node;
            node = node.left;
        }
        // 大于当前结点，在右子树递归查找
        else {
            lastNode = node;
            node = node.right;
        }
        return get(elem, node, lastNode);
    }

    /**
     * 若此二叉排序树中不存在相同元素，插入给定元素
     *
     * @param elem 待插入元素
     * @author pujian
     * @date 2021/8/9 16:05
     * @return 若不存在相同元素，则插入并返回true，否则返回false
     */
    public boolean add(E elem) {
        Node<E> lastNode = this.root;
        // 判断是否已存在
        if (get(elem, lastNode))
            return false;
        Node<E> newNode = new Node<>(elem);
        // 当前树是空树，构建此树
        if (null == lastNode) {
            this.root = newNode;
        } else {
            if (elem.compareTo(lastNode.left.data) < 0)
                lastNode.left = newNode;
            else
                lastNode.right = newNode;
        }
        return true;
    }


    public boolean remove(E elem) {
        Node<E> removedNode = this.root;
        Node<E> parentNode = null;
        parentNode = get(elem, removedNode, parentNode);
        if (null != removedNode) { // 找到了删除结点
            if (removedNode == this.root) { // 删除结点是根节点

            } else {

            }
        }
        return true;
    }

}
