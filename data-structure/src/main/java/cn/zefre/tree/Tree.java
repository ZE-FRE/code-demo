package cn.zefre.tree;

/**
 * N：节点
 *
 * @author pujian
 * @date 2021/7/21 15:43
 */
public interface Tree<N> {

    /**
     * 销毁树
     *
     * @author pujian
     * @date 2021/7/21 16:44
     */
    default void destroy() {
        clear();
    }

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
     * @return
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
     * 给节点node新增一颗子树
     *
     * @param node 节点
     * @param degree node节点的度
     * @param insertedTree 一颗不与当前树相交的子树
     * @author pujian
     * @date 2021/7/21 16:06
     */
    void insertChild(N node, int degree, Tree<N> insertedTree);

    /**
     * 删除节点node的第i棵子树
     *
     * @param node
     * @param i
     * @author pujian
     * @date 2021/7/21 16:14
     */
    void deleteChild(Tree<N> node, int i);

    /**
     * 获取节点数
     *
     * @author pujian
     * @date 2021/7/23 15:00
     * @return 节点数
     */
    int nodeCount();
}
