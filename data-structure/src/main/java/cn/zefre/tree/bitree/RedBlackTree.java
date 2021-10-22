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
 * 2、根结点是黑色
 * 3、叶子结点(nil、null结点)是黑色
 * 4、红色结点的两个子结点是黑色(即红色结点的双亲不能是红色结点)
 * 5、任一结点到它每一个叶子结点的路径包含相同数量的黑色结点(即红黑树的黑色平衡)
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
     * 红黑树根结点
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
        if (null == elem) return null;
        RBNode<E> target = this.root;
        while (null != target) {
            int compare = elem.compareTo(target.data);
            if (compare < 0)
                target = target.left;
            if (compare > 0)
                target = target.right;
            else
                return target;
        }
        return null;
    }

    /**
     * 红黑树插入新结点，新增结点默认为红色
     * 2-3-4树插入结点过程：
     * 在叶子结点插入，若插入后结点数等于4，则第二个结点向上分裂，若向上分裂导致上层结点等于4，则继续向上分裂
     *
     * 根据2-3-4树的插入，推导出红黑树插入的情况：
     * 1、红黑树为空，则新增一个黑色结点为根结点
     * 2、插入结点的父结点是黑色，则插入后不需要调整(变色、旋转)，直接返回
     * 3、插入结点的父结点是红色(则祖父结点一定是黑色)：
     *    3.1 叔叔结点也是红色，则只需变色，不用旋转
     *    3.2 叔叔结点是黑色(可能是null结点，null结点也是黑色)，分四种情况：LL型、LR型、RL型、RR型
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
         * 定位插入结点的双亲结点
         */
        RBNode<E> parent;
        RBNode<E> node = this.root;
        do {
            parent = node;
            int compare = elem.compareTo(node.data);
            if (compare < 0)
                node = node.left;
             else if (compare > 0)
                node = node.right;
             else
                 return false;
        } while (node != null);
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
        adjustAfterInsertion(newNode);
        return true;
    }

    /**
     * 插入后调整红黑树
     * 需要调整的情况：
     * 1、 叔叔结点也是红色，则只需变色，不用旋转
     * 2、 叔叔结点是黑色(可能是null结点，null结点也是黑色)，分四种情况：
     *                                                       LL型
     *           grandpa(黑)                      parent(红)                                         parent(黑)
     *          //      \\          右旋         //      \\            parent变黑，grandpa变红        //      \\
     *      parent(红)  uncle(黑)   ---->   newNode(红)  grandpa(黑)          ---->             newNode(红)  grandpa(红)
     *       //                                            \\                                                 \\
     *   newNode(红)                                      uncle(黑)                                            uncle(黑)
     *
     *                                                       LR型
     *           grandpa(黑)                           grandpa(黑)
     *          //      \\        左旋变为LL型         //      \\
     *      parent(红)  uncle(黑)    ---->        newNode(红)  uncle(黑)    将操作指针指向parent(红)，重复LL型调整
     *          \\                                 //
     *         newNode(红)                    parent(红)
     *
     *                                                      RR型
     *          grandpa(黑)                           parent(红)                                         parent(黑)
     *         //       \\           左旋           //       \\          parent变黑，grandpa变红         //       \\
     *      uncle(黑)  parent(红)    ---->      grandpa(黑)  newNode(红)       ---->                grandpa(红)  newNode(红)
     *                   \\                       //                                                  //
     *                  newNode(红)            uncle(黑)                                          uncle(黑)
     *
     *                                                      RL型
     *           grandpa(黑)                          grandpa(黑)
     *          //      \\         右旋变为RR型       //       \\
     *      uncle(黑)  parent(红)    ---->        uncle(黑)  newNode(红)    将操作指针指向parent(红)，重复RR型调整
     *                  //                                      \\
     *               newNode(红)                             parent(红)
     *
     * @param node 调整结点
     * @author pujian
     * @date 2021/9/8 10:17
     */
    private void adjustAfterInsertion(RBNode<E> node) {
        while (node.parent.color == RED) {
            RBNode<E> parent = node.parent;
            RBNode<E> grandpa = parent.parent;
            RBNode<E> uncle = parent == grandpa.left ? grandpa.right : grandpa.left;
            if (colorOf(uncle) == RED) { // parent和uncle都是红色，只需变色(递归)
                grandpa.color = RED;
                parent.color = BLACK;
                uncle.color = BLACK;
                node = grandpa;
                if (node == root) {
                    root.color = BLACK;
                    break;
                }
            } else { // parent是红色，uncle是黑色，需要变色、旋转。当发生一次旋转后红黑树就平衡了
                if (parent == grandpa.left) {
                    if (node == parent.right) {  // LR型，先左旋变为LL型
                        node = parent;
                        leftRotate(parent);
                    }
                    rightRotate(grandpa);
                } else {
                    if (node == parent.left) { // RL型，先右旋变为RR型
                        node = parent;
                        rightRotate(parent);
                    }
                    leftRotate(grandpa);
                }
                // 变色(父变黑，祖父变红)
                // assert null != node.parent
                node.parent.color = BLACK;
                grandpa.color = RED;
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
     *               //  \\                     //  \\
     *              ?    node                 left   ?
     *
     * @param pivot 旋转支点
     * @author pujian
     * @date 2021/9/8 13:51
     */
    private void leftRotate(RBNode<E> pivot) {
        RBNode<E> right = pivot.right;
        pivot.right = right.left;
        if (null != pivot.right)
            pivot.right.parent = pivot;
        if (null == pivot.parent) // pivot是根结点
            this.root = right;
        else if (pivot == pivot.parent.left)
            pivot.parent.left = right;
        else
            pivot.parent.right = right;
        right.parent = pivot.parent;
        right.left = pivot;
        pivot.parent = right;
    }

    /**
     * 右旋
     *
     *            pivot                          left
     *          //    \\                       //    \\
     *       left     right    ---->        node     pivot
     *      //  \\                                  //  \\
     *   node    ?                                 ?    right
     *
     * @param pivot 旋转支点
     * @author pujian
     * @date 2021/9/8 13:52
     */
    private void rightRotate(RBNode<E> pivot) {
        RBNode<E> left = pivot.left;
        pivot.left = left.right;
        if (null != pivot.left)
            pivot.left.parent = pivot;
        if (null == pivot.parent) // pivot是根结点
            this.root = left;
        else if (pivot == pivot.parent.left)
            pivot.parent.left = left;
        else
            pivot.parent.right = left;
        left.parent = pivot.parent;
        left.right = pivot;
        pivot.parent = left;
    }


    /**
     * 红黑树删除
     * 在2-3-4树中，删除结点最终都转换为删除叶子结点(最后一层，对应红黑树的倒数第一、二层)
     * 在红黑树中，删除结点最终都转换为删除叶子结点(因为二叉排序树的性质)
     *
     *
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
        deleteNode(target);
        return true;
    }

    /**
     * 删除结点
     *
     * @param deletedNode 删除结点
     * @author pujian
     * @date 2021/9/10 13:22
     */
    private void deleteNode(RBNode<E> deletedNode) {
        if (null != deletedNode.left && null != deletedNode.right) { // 左右结点都存在，转换为删除直接后继
            RBNode<E> successor = successor(deletedNode);
            deletedNode.data = successor.data;
            deletedNode = successor;
        }
        RBNode<E> child = null == deletedNode.left ? deletedNode.right : deletedNode.left;
        if (null != child) { // 若只有左孩子或右孩子，转换为删除孩子结点
            deletedNode.data = child.data;
            deletedNode = child;
        }

        if (deletedNode == root) { // 红黑树只有一个根节点，直接删除
            root = null;
            return;
        }

        // 删除结点是黑色，需要先调整再删除
        if (colorOf(deletedNode) == BLACK)
            adjustBeforeDeletion(deletedNode);
        // 删除结点
        if (null != deletedNode.parent) {
            if (deletedNode == deletedNode.parent.left)
                deletedNode.parent.left = null;
            else
                deletedNode.parent.right = null;
            deletedNode.parent = null;
        }
    }

    private void adjustBeforeDeletion(RBNode<E> node) {

    }

    /**
     * 获取中序遍历下结点的直接后继
     *
     * @param node 结点 @NotNull
     * @author pujian
     * @date 2021/9/10 13:14
     * @return 结点直接后继
     */
    private RBNode<E> successor(RBNode<E> node) {
        if (null == node)
            return null;
        else if (null != node.right) { // 结点右子树存在，则直接后继在右子树上
            RBNode<E> successor = node.right;
            while (successor.left != null)
                successor = successor.left;
            return successor;
        } else { // 结点右子树不存在，则直接后继是祖先结点，向上查找(找直接前驱的逆过程)
            RBNode<E> parent = node.parent;
            RBNode<E> successor = node;
            while (parent != null && successor == parent.right) {
                successor = parent;
                parent = parent.parent;
            }
            return successor;
        }
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
