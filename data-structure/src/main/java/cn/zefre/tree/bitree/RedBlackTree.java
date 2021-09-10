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
     * 红色结点
     */
    private static final boolean RED = true;
    /**
     * 黑色结点
     */
    private static final boolean BLACK = false;

    /**
     * 红黑树结点
     *
     * @author pujian
     * @date 2021/9/6 13:47
     */
    static class RBNode<E> implements Node<E> {

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
         * 右结点
         */
        RBNode<E> right;
        /**
         * 双亲结点
         */
        RBNode<E> parent;

        RBNode(E data) {
            this(data, RED);
        }

        RBNode(E data, boolean color) {
            this.data = data;
            this.color = color;
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
     * 红黑树查找结点
     *
     * @param elem 查找元素
     * @author pujian
     * @date 2021/9/10 10:16
     * @return 元素存在返回对应结点，不存在返回null
     */
    public RBNode<E> get(E elem) {
        if (null == elem || null == this.root) return null;
        return get(elem, this.root);
    }

    /**
     * 从指定结点开始查找结点
     *
     * @param elem 查找元素 @NotNull
     * @param startNode 查找起始结点 @NotNull
     * @author pujian
     * @date 2021/9/10 13:19
     * @return 元素存在返回对应结点，不存在返回null
     */
    private RBNode<E> get(E elem, RBNode<E> startNode) {
        RBNode<E> target = startNode;
        while (null != target) {
            if (elem.compareTo(target.data) == 0)
                return target;
            else if (elem.compareTo(target.data) < 0)
                target = target.left;
            else
                target = target.right;
        }
        return null;
    }


    /**
     * 红黑树插入新节点，新增结点默认为红色
     * 2-3-4树插入结点过程：
     * 在叶子结点插入，若插入后节点数等于4，则第二个结点向上分裂，若向上分裂导致上层结点等于4，则继续向上分裂
     *
     * 根据2-3-4树的插入情况，推导出红黑树插入的情况：
     * 1、红黑树为空，则新增一个黑色结点为根节点
     * 2、插入结点的父节点是黑色，则插入后不需要调整(变色、旋转)，直接返回
     * 3、插入结点的父节点是红色(则祖父结点一定是黑色)：
     *    3.1 叔叔结点也是红色，则只需变色，不用旋转
     *    3.2 叔叔结点是黑色，分四种情况：
     *             LL型(右单旋)              LR型(先左旋再右旋)         RL型(先右旋再左旋)          RR型(左单旋)
     *           grandpa(黑)              grandpa(黑)               grandpa(黑)               grandpa(黑)
     *          /       \                /       \                 /       \                 /       \
     *      parent(红)  uncle(黑)     parent(红)  uncle(黑)      uncle(黑)   parent(红)    uncle(黑)  parent(红)
     *      /                           \                                    /                          \
     *   newNode(红)                  newNode(红)                         newNode(红)                 newNode(红)
     *
     *  右旋，parent变黑，grandpa变红       左旋变为第一种情况           右旋变为第四种情况         左旋，parent变黑，grandpa变红
     *
     * @param elem 插入元素
     * @author pujian
     * @date 2021/9/7 15:42
     * @return 插入成功返回true，元素存在返回false
     */
    public boolean add(E elem) {
        if (null == elem) return false;
        if (null == this.root) {
            this.root = new RBNode<>(elem, BLACK);
        }
        /*
         * 定位插入结点的双亲节点
         */
        RBNode<E> parent = null;
        RBNode<E> node = this.root;
        while (node != null) {
            if (elem.compareTo(node.data) == 0)
                return false;
            else if (elem.compareTo(node.data) < 0) {
                parent = node;
                node = node.left;
            } else {
                parent = node;
                node = node.right;
            }
        }
        /*
         * 插入结点
         */
        RBNode<E> newNode = new RBNode<>(elem);
        newNode.parent = parent;
        if (elem.compareTo(parent.data) < 0)
            parent.left = newNode;
        else
            parent.right = newNode;

        // 调整
        adjust(newNode);
        return true;
    }

    /**
     * 调整红黑树
     *
     * @param node 调整节点
     * @author pujian
     * @date 2021/9/8 10:17
     */
    private void adjust(RBNode<E> node) {
        while (node.color == RED) {
            if (this.root == node) {
                this.root.color = BLACK;
                break;
            }
            RBNode<E> parent = node.parent;
            if (parent.color == BLACK)
                break;
            // parent's color is RED
            // assert grandpa is not null and the color is BLACK
            RBNode<E> grandpa = parent.parent;
            RBNode<E> uncle = grandpa.left == parent ? grandpa.right : grandpa.left;
            if (colorOf(uncle) == RED) {
                // 只需变色
                grandpa.color = RED;
                parent.color = BLACK;
                // assert uncle is not null
                uncle.color = BLACK;
                node = grandpa;
            } else { // uncle's color is BLACK
                // 子树旋转后的根节点
                RBNode<E> rootAfterRotating;
                if (grandpa.left == parent) {
                    if (parent.right == node)  // LR型，先左旋变为LL型
                        leftRotate(parent);
                    rootAfterRotating = rightRotate(grandpa);
                } else {
                    if (parent.left == node) { // RL型，先右旋变为RR型
                        rightRotate(parent);
                    }
                    rootAfterRotating = leftRotate(grandpa);
                }
                // 变色
                rootAfterRotating.color = BLACK;
                rootAfterRotating.left.color = RED;
                rootAfterRotating.right.color = RED;
                node = rootAfterRotating;
                if (node.parent == null)
                    this.root = node;
            }
        }
    }

    private boolean colorOf(RBNode<E> node) {
        return null == node ? BLACK : node.color;
    }

    private void setLeft(RBNode<E> node, RBNode<E> leftNode) {
        if (null != node)
            node.left = leftNode;
    }

    private void setRight(RBNode<E> node, RBNode<E> rightNode) {
        if (null != node)
            node.right = rightNode;
    }

    private void setParent(RBNode<E> node, RBNode<E> parent) {
        if (null != node)
            node.parent = parent;
    }

    /**
     * 左旋
     *
     *            pivot                              right
     *          //    \\                           //     \\
     *       left     right       ---->          pivot    node
     *                  \\                       //
     *                  node                   left
     *
     * @param pivot 旋转支点
     * @author pujian
     * @date 2021/9/8 13:51
     * @return 旋转后的根节点
     */
    private RBNode<E> leftRotate(RBNode<E> pivot) {
        RBNode<E> pivotParent = pivot.parent;
        RBNode<E> right = pivot.right;
        setRight(pivot, right.left);
        setParent(right.left, pivot);
        setLeft(right, pivot);
        setParent(pivot, right);
        if (pivotParent != null) {
            if (pivotParent.left == pivot)
                setLeft(pivotParent, right);
            else
                setRight(pivotParent, right);
        }
        setParent(right, pivotParent);
        return right;
    }

    /**
     * 右旋
     *
     *            pivot                          left
     *          //    \\                       //    \\
     *       left     right    ---->        node     pivot
     *      //                                         \\
     *   node                                          right
     *
     * @param pivot 旋转支点
     * @author pujian
     * @date 2021/9/8 13:52
     * @return 旋转后的根节点
     */
    private RBNode<E> rightRotate(RBNode<E> pivot) {
        RBNode<E> pivotParent = pivot.parent;
        RBNode<E> left = pivot.left;
        setLeft(pivot, left.right);
        setParent(left.right, pivot);
        setRight(left, pivot);
        setParent(pivot, left);
        if (pivotParent != null) {
            if (pivotParent.left == pivot)
                setLeft(pivotParent, left);
            else
                setRight(pivotParent, left);
        }
        setParent(left, pivotParent);
        return left;
    }


    /**
     * 红黑树删除
     * 删除操作删除的结点一定是2-3-4树的叶子结点，对应红黑树的倒数第一、二层
     *
     * @param elem 删除元素
     * @author pujian
     * @date 2021/9/10 10:18
     * @return 成功删除返回true，元素不存在返回false
     */
    public boolean remove(E elem) {
        // 查找待删除结点
        RBNode<E> target = get(elem);
        // 元素不存在返回false
        if (null == target) return false;
        // 删除结点
        delete(target);
        return true;
    }

    /**
     * 删除结点
     *
     * @param deletedNode 删除结点 @NotNull
     * @author pujian
     * @date 2021/9/10 13:22
     */
    private void delete(RBNode<E> deletedNode) {
        if (this.root == deletedNode) this.root = null;
        // 叶子结点
        if (null == deletedNode.left && null == deletedNode.right) {
            // assert parent is not null
            RBNode<E> parent = deletedNode.parent;
            if (colorOf(deletedNode) == RED) { // 红色结点，直接删除
                setLeft(parent, null);
                setRight(parent, null);
                setParent(deletedNode, null);
            }
            // 删除结点是黑色结点
            else {

            }
        }
        // 只有右节点，则右节点一定是红色，删除结点一定是黑色，用右节点的值替换当前结点的值，删除右结点
        else if (null == deletedNode.left) {
            // assert target's color is BLACK and right'color is RED
            RBNode<E> right = deletedNode.right;
            deletedNode.data = right.data;
            setRight(deletedNode, null);
            setParent(right, null);
        }
        // 只有左节点，则左节点一定是红色，删除结点一定是黑色，用左节点的值替换当前结点的值，删除左结点
        else if (null == deletedNode.right) {
            // assert target's color is BLACK and left'color is RED
            RBNode<E> left = deletedNode.left;
            deletedNode.data = left.data;
            setLeft(deletedNode, null);
            setParent(left, null);
        }
        // 左、右结点都存在，则删除直接后继
        else {
            RBNode<E> successor = successor(deletedNode);
            deletedNode.data = successor.data;
            // 删除直接后继
            delete(successor);
        }
    }

    /**
     * 获取结点的直接后继
     *
     * @param node 结点 @NotNull
     * @author pujian
     * @date 2021/9/10 13:14
     * @return 结点直接后继
     */
    private RBNode<E> successor(RBNode<E> node) {
        RBNode<E> successor = node.right;
        while (successor.left != null)
            successor = successor.left;
        return successor;
    }


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
