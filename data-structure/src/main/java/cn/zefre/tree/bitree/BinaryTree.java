package cn.zefre.tree.bitree;

/**
 * 普通二叉树
 * @author pujian
 * @date 2020/5/29 16:49
 */
public class BinaryTree<E> {

    private static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        Node(E data, Node<E> left, Node<E> right){
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public void add(E elem){
        Node<E> newNode = new Node<>(elem, null, null);
    }
}
