package cn.zefre.tree.bitree;

/**
 * 二叉结点接口
 *
 * @author pujian
 * @date 2021/9/6 16:57
 */
public interface Node<E> {

    /**
     * 获取结点值
     *
     * @author pujian
     * @date 2021/9/6 17:36
     * @return 结点值
     */
    E getData();

    /**
     * 获取左结点
     *
     * @author pujian
     * @date 2021/9/6 17:37
     * @return 左节点
     */
    Node<E> left();

    /**
     * 获取右结点
     *
     * @author pujian
     * @date 2021/9/6 17:37
     * @return 右结点
     */
    Node<E> right();

}
