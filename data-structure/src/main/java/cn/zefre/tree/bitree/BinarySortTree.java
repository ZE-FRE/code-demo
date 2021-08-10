package cn.zefre.tree.bitree;

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
     * @return 元素对应结点
     */
    public Node<E> get(E elem) {
        if (null == elem) return null;
        Node<E> result = this.root;
        return get(elem, result) ? result : null;
    }

    /**
     * 递归查找元素对应结点
     * 若找到元素，用node返回该元素对应结点
     * 否则用node返回最后访问结点
     *
     * @param elem 查找元素
     * @param node 若找到元素，则为元素对应结点，否则为最后访问结点
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
     * 根据指定元素查找对应结点和它的双亲
     *
     * @param elem 查找元素
     * @param node 查找结点
     * @param previousNode 查找成功时返回双亲结点，否则返回最后访问结点
     * @author pujian
     * @date 2021/8/10 13:20
     * @return 找到元素返回对应结点，否则返回null
     */
    private Node<E> get(E elem, Node<E> node, Node<E> previousNode) {
        // 未查找到元素结点
        if (null == node) return null;
        // 找到了对应元素结点
        if (elem.compareTo(node.data) == 0)
            return node;
        // 记录双亲结点
        previousNode = node;
        // 小于当前结点，在左子树递归查找
        if (elem.compareTo(node.data) < 0)
            return get(elem, node.left, previousNode);
        // 大于当前结点，在右子树递归查找
        else
            return get(elem, node.right, previousNode);
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
        if (null == elem) return false;
        Node<E> lastNode = this.root;
        // 判断是否已存在
        if (get(elem, lastNode))
            return false;
        Node<E> newNode = new Node<>(elem);
        // 当前树是空树，构建此树
        if (null == lastNode) {
            this.root = newNode;
        } else {
            if (elem.compareTo(lastNode.data) < 0)
                lastNode.left = newNode;
            else
                lastNode.right = newNode;
        }
        return true;
    }


    /**
     * 若元素存在，则删除元素对应结点
     *
     * @param elem 删除元素
     * @author pujian
     * @date 2021/8/10 20:41
     * @return 删除了结点返回true，否则返回false
     */
    public boolean remove(E elem) {
        if (null == elem) return false;
        return remove(this.root, elem);
    }

    /**
     * 递归找到指定元素，找到后删除对应结点
     *
     * @author pujian
     * @date 2021/8/10 21:25
     * @param node 删除结点
     * @param elem 删除元素
     * @return 成功删除了该元素结点返回true，否则返回false
     */
    private boolean remove(Node<E> node, E elem) {
        // 未找到删除结点
        if (null == node) return false;
        if (elem.compareTo(node.data) == 0)
            delete(node);
        else if (elem.compareTo(node.data) < 0)
            delete(node.left);
        else
            delete(node.right);
        return true;
    }

    /**
     * 删除结点
     * 待删除结点分以下几种情况：
     * ① 叶子节点
     * ② 只有右子树
     * ③ 只有左子树
     * ④ 左右子树都有
     * 左右都子树存在时可以找它的直接前驱或直接后继来替代它
     *
     *
     * @author pujian
     * @date 2021/8/10 21:27
     * @param deletedNode 待删除结点
     */
    private void delete(Node<E> deletedNode) {
        Node<E> temp = deletedNode;
        if (null == deletedNode.left) {
            // 待删除结点无左子树
            deletedNode = deletedNode.right;
            temp.right = null;
        } else if (null == deletedNode.right) {
            // 待删除结点无右子树
            deletedNode = deletedNode.left;
            temp.left = null;
        } else {
            // 待删除子树左、右子树都存在
            /* 按中序遍历找直接前驱
             * 中序遍历下结点直接前驱是它的左孩子或左孩子的最后一个右孩子
             */
            // 直接前驱的双亲
            Node<E> previousParent = deletedNode;
            // 直接前驱
            Node<E> previousNode = deletedNode.left;
            while (previousNode.right != null) {
                previousParent = previousNode;
                previousNode = previousNode.right;
            }
            if (previousParent == deletedNode) {
                // 直接前驱是它的左孩子（即左孩子没有右子树）
                deletedNode = deletedNode.left;
            } else {
                previousParent.right = null;
                previousNode.left = deletedNode.left;
                previousNode.right = deletedNode.right;
                deletedNode = previousNode;
            }
            temp.left = null;
        }
    }

}
