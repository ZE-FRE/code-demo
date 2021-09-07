package cn.zefre.tree.bitree;

import java.util.List;

/**
 * 红黑树实现
 * 一颗红黑树等价于一颗2-3-4树，一颗2-3-4树对应多颗红黑树
 * 2-3-4树是4阶B树
 * 红黑树的五大性质是2-3-4树等价变换为红黑树得来的
 *
 * 红黑树五大性质：
 * 1、结点是黑色或红色
 * 2、根节点是黑色
 * 3、叶子节点(nil、null结点)是黑色
 * 4、红色节点的两个子节点是黑色(即红色节点的双亲不能是红色结点)
 * 5、任一节点到它每一个叶子结点的路径包含相同数量的黑色结点(即红黑树的黑色平衡)
 *
 * @author pujian
 * @date 2021/9/6 13:31
 */
public class RedBlackTree<E extends Comparable<E>> {

    /**
     * 红黑树结点
     *
     * @author pujian
     * @date 2021/9/6 13:47
     */
    static class RBNode<E extends Comparable<E>> implements Node<E> {

        /**
         * 红色结点
         */
        static final boolean RED = true;
        /**
         * 黑色结点
         */
        static final boolean BLACK = false;

        /**
         * 结点值
         */
        E data;
        /**
         * 结点颜色
         * true：红
         * false：黑
         */
        boolean color;
        /**
         * 左结点
         */
        RBNode<E> left;
        /**
         * 右节点
         */
        RBNode<E> right;

        RBNode(E data) {
            this.data = data;
            this.color = RED;
        }

        @Override
        public E getData() {
            return this.data;
        }

        @Override
        public Node<E> left() {
            return this.left;
        }

        @Override
        public Node<E> right() {
            return this.right;
        }
    }

    /**
     * 二叉树遍历工具
     */
    private BinaryTreeOrder<E> binaryTreeOrder = new BinaryTreeOrder<>();

    /**
     * 红黑树根节点
     */
    private RBNode<E> root;

    /**
     * 按指定顺序遍历红黑树
     *
     * @param orderEnum 遍历顺序
     * @author pujian
     * @date 2021/9/6 17:46
     * @return 遍历结果集
     */
    public List<E> sequence(OrderEnum orderEnum) {
        return binaryTreeOrder.sequence(this.root, orderEnum);
    }

}
