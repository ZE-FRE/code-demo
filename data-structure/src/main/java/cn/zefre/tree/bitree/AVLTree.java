package cn.zefre.tree.bitree;

import java.util.List;
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
     * AVL树结点
     */
    static class AVLNode<E> implements Node<E> {
        E data;
        int height;
        AVLNode<E> left;
        AVLNode<E> right;

        AVLNode(E data) {
            this(data, 1);
        }

        AVLNode(E data, int height) {
            this.data = data;
            this.height = height;
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
     * AVL树根节点
     */
    private AVLNode<E> root;


    /**
     * 计算结点的高度
     *
     * @param node 结点
     * @author pujian
     * @date 2021/8/25 13:41
     * @return 结点高度
     */
    private int calculateHeight(AVLNode<?> node) {
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
    private int calculateBalanceFactor(AVLNode<?> node) {
        Objects.requireNonNull(node);
        int leftHeight = null == node.left ? 0 : node.left.height;
        int rightHeight = null == node.right ? 0 : node.right.height;
        return leftHeight - rightHeight;
    }

    /**
     * 查找元素结点
     *
     * @param elem 元素值
     * @author pujian
     * @date 2021/8/20 13:25
     * @return 元素结点
     */
    public AVLNode<E> get(E elem) {
        if (null == elem) return null;
        AVLNode<E> target = this.root;
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
            this.root = new AVLNode<>(elem);
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
    private boolean add(E elem, AVLNode<E> node) {
        boolean addSuccess;
        if (elem.compareTo(node.data) == 0)
            return false;
        else if (elem.compareTo(node.data) < 0) {
            if (null == node.left) {
                node.left = new AVLNode<>(elem);
                addSuccess = true;
            } else addSuccess = add(elem, node.left);
        } else {
            if (null == node.right) {
                node.right = new AVLNode<>(elem);
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
     * 删除结点
     *
     * @param elem 删除元素
     * @author pujian
     * @date 2021/9/2 10:08
     * @return 成功删除返回true，元素不存在返回false
     */
    public boolean remove(E elem) {
        if (null == elem || null == this.root) return false;
        // 扩展一个根节点
        AVLNode<E> extendedRoot = new AVLNode<>(null);
        extendedRoot.right = this.root;
        extendedRoot.height = calculateHeight(extendedRoot);
        boolean deleted = remove(elem, extendedRoot, this.root);
        this.root = extendedRoot.right;
        extendedRoot.right = null;
        return deleted;
    }

    /**
     * 删除结点，并保持平衡
     *
     * @param elem 删除元素
     * @param parentNode 删除节点的双亲结点
     * @param deletedNode 删除节点
     * @author pujian
     * @date 2021/9/2 10:09
     * @return 成功删除返回true，元素不存在返回false
     */
    private boolean remove(E elem, AVLNode<E> parentNode, AVLNode<E> deletedNode) {
        if (null == deletedNode) { // 结点不存在，返回false
            return false;
        }
        boolean deleted = true;
        int comparingValue = elem.compareTo(deletedNode.data);
        if (comparingValue == 0)
            delete(parentNode, deletedNode);
        else if (comparingValue < 0)
            deleted = remove(elem, deletedNode, deletedNode.left);
        else
            deleted = remove(elem, deletedNode, deletedNode.right);

        if (deleted) {
            deletedNode.height = calculateHeight(deletedNode);
            if (Math.abs(calculateBalanceFactor(deletedNode)) > 1)
                balance(deletedNode);
        }
        return deleted;
    }


    /**
     * 删除操作
     *
     * @param parentNode 删除节点的双亲结点
     * @param deletedNode 删除节点
     * @author pujian
     * @date 2021/9/2 10:14
     */
    private void delete(AVLNode<E> parentNode, AVLNode<E> deletedNode) {
        if (null == deletedNode.left && null == deletedNode.right) { // 叶子节点
            if (parentNode.left == deletedNode) parentNode.left = null;
            else parentNode.right = null;
        } else if (null == deletedNode.left) { // 只有右子树，则右子树是叶子节点
            deletedNode.data = deletedNode.right.data;
            deletedNode.right = null;
        } else if (null == deletedNode.right) { // 只有左子树，则左子树是叶子节点
            deletedNode.data = deletedNode.left.data;
            deletedNode.left = null;
        } else { // 左右子树都存在
            // 找直接后继
            AVLNode<E> successor = deletedNode.right;
            if (null == successor.left) { // 直接后继就是删除节点的右结点
                deletedNode.data = successor.data;
                deletedNode.right = successor.right;
                successor.right = null;
            } else {
                while (successor.left != null) {
                    successor = successor.left;
                }
                deletedNode.data = successor.data;
                // 删除successor
                remove(successor.data, deletedNode, deletedNode.right);
            }
        }
    }

    /**
     * 平衡子树
     *
     * @param node 待平衡子树根节点
     * @author pujian
     * @date 2021/8/25 17:29
     */
    private void balance(AVLNode<E> node) {
        int nodeBf = calculateBalanceFactor(node);
        if (nodeBf > 1) { // 左子树高
            if (calculateBalanceFactor(node.left) >= 0) {
                // LL型，右单旋
                rightRotate(node);
            } else if (calculateBalanceFactor(node.left) < 0){
                // LR型，先左旋，再右旋
                leftRotate(node.left);
                rightRotate(node);
            }
        } else if (nodeBf < -1) { // 右子树高
            if (calculateBalanceFactor(node.right) <= 0) {
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
    private void leftRotate(AVLNode<E> node) {
        E originalRootData = node.data;
        AVLNode<E> right = node.right;
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
    private void rightRotate(AVLNode<E> node) {
        E originalRootData = node.data;
        AVLNode<E> left = node.left;
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
     * 按指定顺序遍历AVL树
     *
     * @param orderEnum 遍历顺序
     * @author pujian
     * @date 2021/9/6 17:33
     * @return 遍历结果集
     */
    public List<E> sequence(OrderEnum orderEnum) {
        return binaryTreeOrder.sequence(this.root, orderEnum);
    }

}
