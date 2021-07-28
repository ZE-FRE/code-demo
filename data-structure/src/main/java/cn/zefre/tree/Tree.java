package cn.zefre.tree;

/**
 * N：节点
 *
 * @author pujian
 * @date 2021/7/21 15:43
 */
public interface Tree<N> {

    /**
     * 若树不是空树，则清空树
     *
     * @author pujian
     * @date 2021/7/21 16:44
     */
    void clear();

    /**
     * 是否是空树
     *
     * @author pujian
     * @date 2021/7/21 16:02
     * @return 是返回true，否则返回false
     */
    boolean isEmpty();

    /**
     * 返回树的深度
     *
     * @author pujian
     * @date 2021/7/21 15:59
     * @return 树的深度
     */
    int depth();

    /**
     * 返回树的根节点
     *
     * @author pujian
     * @date 2021/7/21 15:57
     * @return 树的根节点
     */
    N root();

//    /**
//     * 返回节点node的值
//     *
//     * @param node
//     * @author pujian
//     * @date 2021/7/21 16:46
//     * @return 节点node的值
//     */
//    <T> T value(N node);
//
//    /**
//     * 给树的node赋新值
//     *
//     * @param node
//     * @param data 节点新值
//     * @author pujian
//     * @date 2021/7/21 15:58
//     */
//    <T> void assign(N node, T data);

    /**
     * 若node不是根节点，则返回它的双亲，否则返回空
     *
     * @param node
     * @author pujian
     * @date 2021/7/21 15:56
     * @return 返回双亲
     */
    N parent(N node);

    /**
     * 若node非叶节点，则返回它的最左孩子，否则返回空
     *
     * @param node
     * @author pujian
     * @date 2021/7/21 15:54
     * @return 指定节点的最左孩子
     */
    N leftChild(N node);

    /**
     * 若node有右兄弟，则返回它的右兄弟，否则返回空
     *
     * @param node
     * @author pujian
     * @date 2021/7/21 16:00
     * @return 返回右兄弟
     */
    N rightSibling(N node);

    /**
     * 在节点node插入一颗子树作为它的第i棵子树
     *
     * @param node 节点
     * @param i 1 <= i <= degree + 1
     * @param insertedTree 一颗不与当前树相交的子树
     * @author pujian
     * @date 2021/7/21 16:06
     */
    void insertChild(N node, int i, Tree<N> insertedTree);

    /**
     * 删除节点node的第i棵子树
     *
     * @param node
     * @param i 1 <= i<= degree
     * @author pujian
     * @date 2021/7/21 16:14
     * @return 返回被删除的子树
     */
    Tree<N> deleteChild(N node, int i);

    /**
     * 获取树节点数
     *
     * @author pujian
     * @date 2021/7/23 15:00
     * @return 节点数
     */
    int nodeCount();

    /**
     * 获取节点的度
     *
     * @param node
     * @author pujian
     * @date 2021/7/26 14:48
     * @return 节点的度
     */
    int degree(N node);
}
