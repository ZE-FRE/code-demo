package cn.zefre.tree.bitree;

import java.util.function.Consumer;

/**
 * 线索二叉树
 * 以二叉链表实现的二叉树，假如有n个结点，则必有n+1个空指针域，这些空指针域浪费了空间
 * 同时另一方面又考虑，当需要知道或访问某结点在某顺序下(先序、中序、后序)的直接前驱或直接后继时(后面省略"直接"二字)
 * 需要进行部分遍历，例如若想知道某结点的中序前驱，需要遍历左子树，中序前驱为左子树的根结点(左子树无右子树的情况)或左子树最右下角那个结点
 * 如果为了更快速地知道某结点的前驱或后继，简单粗暴的方法就是给结点增加一个指向前驱的指针和一个指向后继的指针
 * 但是这又增加了空指针域的数量，加剧了空间的浪费。
 * 结合上面两个方面的考量，即：1、将空指针域利用起来  2、更方便地访问结点的前驱和后继
 * 可以将n+1个空指针域用于存储结点的前驱和后继指针，即：将空的左指针指向前驱，将空的右指针指向后继
 * 为了避免歧义，需要标志位用于标识左/右指针是指向孩子结点还是指向前驱或后继。
 * <p>
 * 概念：
 * 1、指向前驱或后继的指针称为线索
 * 2、以{@link ThreadNode}这种结点结构构成的二叉链表作为二叉树的存储结构，称为线索链表
 * 3、加上线索的二叉树称为线索二叉树
 * 4、对二叉树以某种次序遍历使其变为线索二叉树的过程称为线索化
 * p.s 二叉树的遍历，就是将非线性的二叉树这一数据结构用线性方式访问
 * <p>
 * n个结点有n+1个空指针域的证明：
 * 方法一：n个结点，则有2n个指针域，其中除了根结点没有指向它的指针外，其他结点都有一个指针指向它，所以用掉了n-1个指针域，因此空指针域等于2n-(n-1)=n+1
 * 方法二：设叶子节点为n0，有一个孩子的结点为n1，有两个孩子的结点为n2，则空指针域的数量就等于2n0+n1。已知n，现在需要求2n0+n1，即用n来表示上式
 * ① n=n0+n1+n2
 * ② n0=n2+1，即n2=n0-1
 * 将②式带入①式得n=n0+n1+n0-1，合并移项得：2n0+n1=n+1，所以n个结点有n+1个空指针域。
 *
 * @author pujian
 * @date 2023/3/28 10:48
 */
public class ThreadedBinaryTree<E> {

    /**
     * 表示指针指向孩子结点
     */
    private static final boolean LINK = false;
    /**
     * 表示线索
     */
    private static final boolean THREAD = true;


    public static class ThreadNode<T> {
        T data;
        /**
         * 左右孩子
         */
        ThreadNode<T> left, right;
        /**
         * 左右标志，LINK OR THREAD
         */
        boolean leftTag, rightTag;

        public ThreadNode(T data) {
            this.data = data;
        }

        public ThreadNode(boolean leftTag, boolean rightTag) {
            this.leftTag = leftTag;
            this.rightTag = rightTag;
        }
    }

    /**
     * 二叉树根结点
     */
    private ThreadNode<E> root;
    /**
     * 二叉链表头结点
     */
    private ThreadNode<E> head;

    public ThreadedBinaryTree(ThreadNode<E> root) {
        this.root = root;
    }

    /**
     * 中序线索化二叉树
     * 线索化的过程中，为线索链表增加一个头结点
     * 头结点的左指针指向根结点，右指针指向中序遍历下的最后一个结点
     * 中序遍历下第一个结点的左指针和最后一个结点的右指针都指向头结点
     * 这样就形成了一个双向线索链表
     *
     * @author pujian
     * @date 2023/3/28 16:17
     */
    public void inOrderThreading() {
        if (root == null || head != null) return;
        // 新建一个头结点，它的left指向根结点，right要指向中序下最后一个结点
        ThreadNode<E> head = new ThreadNode<>(LINK, THREAD);
        head.left = root;
        ThreadNode<E> prev = head;
        // 线索化二叉树，返回中序下最后一个结点
        prev = inOrderThreading(root, prev);
        // 最后一个结点的后继是头结点
        prev.rightTag = THREAD;
        prev.right = head;
        // 头结点的right指向最后一个结点
        head.right = prev;
        this.head = head;
    }

    /**
     * 中序线索化二叉树
     *         a
     *     /      \
     *    b        c
     *  /  \      / \
     * d    e    f   g
     *  \  /    /     \
     *  h i    j       k
     *
     * @param node 当前结点
     * @param prev 上一个访问的结点
     * @return 上一个访问的结点
     * @author pujian
     * @date 2023/3/29 11:00
     */
    private ThreadNode<E> inOrderThreading(ThreadNode<E> node, ThreadNode<E> prev) {
        if (node.left != null) {
            // 线索化左子树，返回左子树中序下最后一个结点
            prev = inOrderThreading(node.left, prev);
            // 左子树最后一个结点的后继是当前结点
            prev.rightTag = THREAD;
            prev.right = node;
        } else {
            // 左结点是空指针域，需要线索化，将其指向前驱
            node.leftTag = THREAD;
            node.left = prev;
        }
        prev = node;
        if (node.right != null) {
            // 线索化右子树
            prev = inOrderThreading(node.right, prev);
        }
        return prev;
    }

    /**
     * 从中序第一个结点开始顺后继遍历线索二叉树
     *
     * @param visitor 遍历时需要做的操作
     * @author pujian
     * @date 2023/3/29 11:48
     */
    public void inOrderFromFirst(Consumer<E> visitor) {
        if (head == null) inOrderThreading();
        ThreadNode<E> node = head.left;
        while (node != head) {
            // 找到以当前结点为根结点的子树的第一个结点
            while (node.leftTag == LINK) node = node.left;
            visitor.accept(node.data);
            // 继续遍历后继结点
            while (node.rightTag == THREAD && node.right != head) {
                node = node.right;
                visitor.accept(node.data);
            }
            node = node.right;
        }
    }

    /**
     * 从中序最后一个结点开始顺前驱遍历线索二叉树
     *
     * @param visitor 遍历时需要做的操作
     * @author pujian
     * @date 2023/3/29 11:30
     */
    public void inOrderFromLast(Consumer<E> visitor) {
        if (head == null) inOrderThreading();
        ThreadNode<E> node = head.right;
        while (node != head) {
            visitor.accept(node.data);
            // 当前结点的左结点是线索，则左结点就是它的前驱
            while (node.leftTag == THREAD && node.left != head) {
                node = node.left;
                visitor.accept(node.data);
            }
            // 当前结点的左结点不是线索，则去找它的前驱结点
            node = node.left;
            while (node.rightTag == LINK) node = node.right;
        }
    }

}
