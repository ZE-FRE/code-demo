package cn.zefre.tree.bitree;

/**
 * 平衡二叉树
 * 又叫AVL树、Self-Balancing Binary Search Tree、Height-Balanced Binary Search Tree
 *
 * @author pujian
 * @date 2021/8/13 14:36
 */
public class AVLTree<E> {

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

    static class Node<E> {
        E elem;
        /**
         * 平衡因子
         * 结点左子树与右子树高度之差，取值范围：-1  0  1
         */
        BalanceFactor bf;
        Node<E> left;
        Node<E> right;

    }
}
