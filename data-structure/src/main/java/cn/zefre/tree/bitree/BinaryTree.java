package cn.zefre.tree.bitree;

import cn.zefre.tree.Tree;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树
 *
 * @author pujian
 * @date 2020/5/29 16:49
 */
public class BinaryTree<E> implements Tree<BinaryTree.BitNode<E>> {

    static class BitNode<E> {
        E data;
        BitNode<E> left;
        BitNode<E> right;

        BitNode(E data) {
            this.data = data;
        }
    }

    /**
     * 根结点
     */
    protected BitNode<E> root;

    public BinaryTree() { }

    public BinaryTree(BitNode<E> root) {
        this.root = root;
    }

    public BinaryTree(BinaryTree<E> bTree) {
        if (null != bTree)
            this.root = this.init(bTree.root);
    }

    private BitNode<E> init(BitNode<E> node) {
        if (null == node) return null;
        BitNode<E> newNode = new BitNode<>(node.data);
        newNode.left = init(node.left);
        newNode.right = init(node.right);
        return newNode;
    }

    @Override
    public void clear() {
        clear(root);
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public int depth() {
        if (isEmpty())
            return 0;

        int depth = 0;
        Queue<BitNode<E>> currentLayer = new LinkedList<>();
        currentLayer.add(this.root);
        Queue<BitNode<E>> nextLayer = new LinkedList<>();
        BitNode<E> node;
        while (null != (node = currentLayer.poll())) {
            if (null != node.left)
                nextLayer.offer(node.left);
            if (null != node.right)
                nextLayer.offer(node.right);
            if (currentLayer.isEmpty()) {
                depth++;
                // swap
                Queue<BitNode<E>> temp = currentLayer;
                currentLayer = nextLayer;
                nextLayer = temp;
            }
        }
        return depth;
    }

    @Override
    public BitNode<E> root() {
        return this.root;
    }

    @Override
    public BitNode<E> parent(BitNode<E> node) {
        if (null == node || isEmpty())
            return null;
        List<BitNode<E>> sequenceList = new ArrayList<>();
        sequenceList.add(root);
        for (int i = 0; i < sequenceList.size(); i++) {
            BitNode<E> parentNode = sequenceList.get(i);
            if (node.equals(parentNode.left) || node.equals(parentNode.right))
                return parentNode;
            if (null != parentNode.left)
                sequenceList.add(parentNode.left);
            if (null != parentNode.right)
                sequenceList.add(parentNode.right);
        }
        return null;
    }

    @Override
    public BitNode<E> leftChild(BitNode<E> node) {
        return null == node ? null : node.left;
    }

    @Override
    public BitNode<E> rightSibling(BitNode<E> node) {
        BitNode<E> parent = parent(node);
        return null == parent ? null : parent.right == node ? null : parent.right;
    }

    @Override
    public void insertChild(BitNode<E> node, int i, Tree<BitNode<E>> insertedTree) {
        if (null == node || isEmpty())
            return;
        if (i <= 0 || i > degree(node) + 1)
            throw new IllegalArgumentException("0 < i <= " + degree(node) + 1);
        if (i == 1) {
            if (null != node.left) {
                throw new UnsupportedOperationException("结点左子树不为空，不允许在此位置插入子树");
            }
            node.left = insertedTree.root();
        } else {
            if (null != node.right) {
                throw new UnsupportedOperationException("结点右子树不为空，不允许在此位置插入子树");
            }
            node.right = insertedTree.root();
        }
    }

    @Override
    public Tree<BitNode<E>> deleteChild(BitNode<E> node, int i) {
        if (null == node || isEmpty())
            return null;
        if (i <= 0 || i > degree(node))
            throw new IllegalArgumentException("0 < i <= " + degree(node));
        BitNode<E> deletedTree;
        boolean deleteLeft = i == 1 && null != node.left;
        if (deleteLeft) {
            deletedTree = node.left;
            node.left = null;
        } else {
            deletedTree = node.right;
            node.right = null;
        }
        return new BinaryTree<>(deletedTree);
    }

    @Override
    public int nodeCount() {
        return 0;
    }

    @Override
    public int degree(BitNode<E> node) {
        int degree = 0;
        if (null != node) {
            if (null != node.left)
                degree++;
            if (null != node.right)
                degree++;
        }
        return degree;
    }

    private void clear(BitNode<E> node) {
        if (null == node) return;
        clear(node.left);
        clear(node.right);
        node.left = node.right = null;
    }

    public BitNode<E> add(E elem, BitNode<E> node, boolean addLeft) {
        if (null == node) {
            this.root = new BitNode<>(elem);
            return this.root;
        }

        BitNode<E> newNode = new BitNode<>(elem);
        BitNode<E> temp;
        if (addLeft) {
            temp = node.left;
            node.left = newNode;
            newNode.left = temp;
        } else {
            temp = node.right;
            node.right = newNode;
            newNode.right = temp;
        }
        return newNode;
    }

    public BitNode<E> get(E elem) {
        if (null == elem) return null;
        return this.get(elem, root);
    }

    private BitNode<E> get(E elem, BitNode<E> node) {
        if (null == node) return null;
        if (elem.equals(node.data)) return node;
        BitNode<E> result = get(elem, node.left);
        if (null != result) return result;
        return get(elem, node.right);
    }


    /**
     * 按指定顺序遍历二叉树
     *
     * @param orderEnum 遍历顺序
     * @author pujian
     * @date 2021/9/7 10:51
     * @return 遍历结果集
     */
    public List<E> sequence(OrderEnum orderEnum) {
        return null;
    }

}
