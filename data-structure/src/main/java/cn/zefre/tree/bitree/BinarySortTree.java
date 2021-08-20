package cn.zefre.tree.bitree;

import java.util.Objects;

/**
 * 二叉排序树(二叉搜索树)
 * 元素不允许重复
 *
 * @author pujian
 * @date 2021/8/9 16:00
 */
public class BinarySortTree<E extends Comparable<E>> extends BinaryTree<E> {

    /**
     *
     * 如下代码，obj指向的还是同一块地址
     * public static void main(String[] args) {
     *     Object obj = new Object();
     *     System.out.println(obj);
     *     updateReference(obj);
     *     System.out.println(obj);
     * }
     * private static void updateReference(Object obj) {
     *     obj = new Object();
     * }
     *
     * java中引用类型是引用传递，但是引用本身的值，还是值传递
     * 所以在方法内部给引用(形参)赋值，只是改变了形参的值
     * 同理，在c/c++中，方法参数是一个指针(形参)，但方法内部修改这个指针的指向，只是修改了形参指针的值
     * 方法外部的指针并不会受影响，通过指针的指针才能改变方法外部的指针的值
     * 即指针可以改变普通变量的值，指针的指针能改变指针的值
     *
     *
     * Node *node1 = (Node*) malloc(sizeof(Node));
     * Node *node2 = node1;
     * Node **node3 = &node2;
     * *node3 = NULL;
     * 这时*node3 == node2 == NULL
     *
     * @author pujian
     * @date 2021/8/11 17:03
     */
    private static class Wrapper<E> {
        private Node<E> node;
        public Wrapper() {}
        public Wrapper(Node<E> node) {
            this.node = node;
        }
    }

    public BinarySortTree() {}

    public BinarySortTree(BinarySortTree<E> bitSortTree) {
        super(bitSortTree);
    }

    /**
     * 迭代方式查找元素结点
     * 若找到元素，返回对应结点，否则返回null
     *
     * @param elem 查找元素
     * @author pujian
     * @date 2021/8/13 14:05
     * @return 元素对应结点
     */
    public Node<E> find(E elem) {
        if (null == elem) return null;
        Node<E> node = this.root;
        while (null != node) {
            if (elem.compareTo(node.data) == 0)
                return node;
            else if (elem.compareTo(node.data) < 0)
                node = node.left;
            else
                node = node.right;
        }
        return null;
    }

    /**
     * 递归方式查找元素对应的结点
     * 若找到元素，返回对应结点，否则返回null
     *
     * @param elem 查找元素
     * @author pujian
     * @date 2021/8/10 13:11
     * @return 元素对应结点
     */
    public Node<E> get(E elem) {
        if (null == elem) return null;
        return get(elem, this.root);
    }

    /**
     * 递归查找元素对应结点
     *
     * @param elem 查找元素
     * @param node 查找结点
     * @author pujian
     * @date 2021/8/10 13:20
     * @return 找到返回对应结点，否则返回null
     */
    private Node<E> get(E elem, Node<E> node) {
        // 未查找到元素结点
        if (null == node) return null;
        // 找到了对应元素结点
        if (elem.compareTo(node.data) == 0)
            return node;
        // 小于当前结点，在左子树递归查找
        else if (elem.compareTo(node.data) < 0)
            return get(elem, node.left);
        // 大于当前结点，在右子树递归查找
        else
            return get(elem, node.right);
    }

    /**
     * 根据指定元素进行查找
     *
     * @param elem @NotNull 查找元素
     * @param target @NotNull 找到元素时为对应结点，未找到时为最后访问结点
     * @param previous @NotNull 找到元素时为目标结点的双亲(根结点无双亲)，未找到时为最后访问结点
     * @author pujian
     * @date 2021/8/11 17:06
     * @return 找到元素返回true，否则返回false
     * @throws NullPointerException target或previous为null
     */
    private boolean get(E elem, Wrapper<E> target, Wrapper<E> previous) {
        if (null == target.node) return false;
        if (elem.compareTo(target.node.data) == 0)
            return true;
        // 记录双亲结点
        previous.node = target.node;
        if (elem.compareTo(target.node.data) < 0)
            target.node = target.node.left;
        else
            target.node = target.node.right;
        return get(elem, target, previous);
    }

    /**
     * 插入元素（迭代）
     *
     * @param elem 待插入元素
     * @author pujian
     * @date 2021/8/20 16:29
     * @return 插入成功返回true，元素存在则返回false
     */
    public boolean add(E elem) {
        Objects.requireNonNull(elem);
        Node<E> newNode = new Node<>(elem);
        if (null == this.root) {
            this.root = newNode;
            return true;
        }
        Node<E> parent = null;
        Node<E> node = this.root;
        while (node != null) {
            if (elem.compareTo(node.data) == 0)
                return false;
            parent = node;
            if (elem.compareTo(node.data) < 0)
                node = node.left;
            else
                node = node.right;
        }
        if (elem.compareTo(parent.data) < 0)
            parent.left = newNode;
        else
            parent.right = newNode;
        return true;
    }

    /**
     * 插入元素
     *
     * @param elem 待插入元素
     * @author pujian
     * @date 2021/8/20 16:26
     */
    public void insert(E elem) {
        Objects.requireNonNull(elem);
        if (null == this.root)
            this.root = new Node<>(elem);
        else
            insert(elem, this.root);
    }

    /**
     * 插入元素（递归）
     *
     * @param elem 待插入元素
     * @param node 结点
     * @author pujian
     * @date 2021/8/20 16:24
     * @return 原结点
     */
    private Node<E> insert(E elem, Node<E> node) {
        if (null == node)
            return new Node<>(elem);
        else if (elem.compareTo(node.data) == 0)
            return node;
        else if (elem.compareTo(node.data) < 0)
            node.left = insert(elem, node.left);
        else
            node.right = insert(elem, node.right);
        return node;
    }

    /**
     * 删除结点
     * 待删除结点分以下几种情况：
     * ① 叶子节点
     * ② 只有右子树
     * ③ 只有左子树
     * ④ 左右子树都有
     * 左右子树都存在时可以找它的直接前驱或直接后继来替代它
     *
     * @param elem 删除元素
     * @author pujian
     * @date 2021/8/10 20:41
     * @return 删除了结点返回true，否则返回false
     */
    public boolean remove(E elem) {
        if (null == elem || null == this.root) return false;
        Wrapper<E> target = new Wrapper<>(this.root);
        Wrapper<E> parent = new Wrapper<>();
        // 未找到删除结点
        if (!get(elem, target, parent)) return false;

        // 删除结点
        Node<E> deletedNode = target.node;
        // 删除节点的双亲
        Node<E> parentNode = parent.node;
        // new一个结点作为新结点，屏蔽掉删除结点是否是根结点的差异
        Node<E> extendedRoot = new Node<>(null);
        extendedRoot.left = this.root;
        // 删除结点是根节点
        if (parentNode == null) parentNode = extendedRoot;
        // 删除节点的继任
        Node<E> successor;
        if (deletedNode.left == null) { // 删除结点无左子树
            successor = deletedNode.right;
            deletedNode.right = null;
        } else if (deletedNode.right == null) { // 删除结点无右子树
            successor = deletedNode.left;
            deletedNode.left = null;
        } else { // 删除结点左、右子树都存在
            /*
             * 按中序遍历找直接前驱
             * 中序遍历下结点直接前驱是它的左孩子或左孩子的最后一个右孩子
             */
            // 直接前驱
            Node<E> directPrev = deletedNode.left;
            // 直接前驱的双亲
            Node<E> directPrevParent = deletedNode;
            while (directPrev.right != null) {
                directPrevParent = directPrev;
                directPrev = directPrev.right;
            }
            if (directPrevParent != deletedNode) {
                directPrevParent.right = directPrev.left;
                directPrev.left = deletedNode.left;
            }
            directPrev.right = deletedNode.right;
            successor = directPrev;
            deletedNode.left = null;
            deletedNode.right = null;
        }
        // 双亲结点连接继任结点
        if (parentNode.left == deletedNode)
            parentNode.left = successor;
        else
            parentNode.right = successor;
        // 恢复原根结点
        this.root = extendedRoot.left;
        extendedRoot.left = null;
        return true;
    }

}
