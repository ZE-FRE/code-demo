package cn.zefre.tree.bitree;

import java.util.Objects;

/**
 * 平衡二叉树
 * 又叫AVL树、Self-Balancing Binary Search Tree、Height-Balanced Binary Search Tree
 *
 * @author pujian
 * @date 2021/8/13 14:36
 */
public class AVLTree<E extends Comparable<E>> {

    /**
     * 平衡因子枚举
     */
    private enum BalanceFactor {
        /**
         * 左子树高
         */
        LH(-1),
        /**
         * 左右子树等高
         */
        EH(0),
        /**
         * 右子树高
         */
        RH(1);

        /**
         * 差值
         */
        private int value;

        BalanceFactor(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * AVL树结点
     */
    static class Node<E> {
        E data;
        /**
         * 平衡因子
         * 结点左子树与右子树高度之差，取值范围：-1  0  1
         */
        BalanceFactor bf;
        Node<E> left;
        Node<E> right;

        Node(E data) {
            this(data, BalanceFactor.EH);
        }

        Node(E data, BalanceFactor bf) {
            this.data = data;
            this.bf = bf;
        }

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

}
