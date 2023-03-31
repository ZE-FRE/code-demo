package cn.zefre.tree.bitree;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 哈夫曼树(最优二叉树)
 * 概念：
 * 路径：一个结点到另一个结点所经过的路径(即分支)
 * 路径长度(用l表示)：两结点间所经过分支的数目
 * 树的路径长度：根结点到每一个结点的路径长度之和。
 * 权(用w表示)
 * 结点的带权路径长度：等于结点的权*路径长度，即w*l
 * 树的带权路径长度：所有叶子结点带权路径长度之和，记为wpl，即：wpl=Σwi*li
 *
 * @author pujian
 * @date 2023/3/30 10:04
 */
public class HuffmanTree<E> {

    static class HuffmanNode<T> {
        /**
         * 结点数据
         */
        T data;
        /**
         * 结点权值
         */
        Integer weight;
        /**
         * 双亲、左孩子、右孩子
         */
        int parent, left, right;

        public HuffmanNode() {
        }

        public HuffmanNode(T data, Integer weight) {
            this.data = data;
            this.weight = weight;
        }
        public HuffmanNode(T data, Integer weight, int parent, int left, int right) {
            this.data = data;
            this.weight = weight;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof HuffmanNode)) return false;
            HuffmanNode<?> that = (HuffmanNode<?>) o;
            return parent == that.parent &&
                    left == that.left &&
                    right == that.right &&
                    Objects.equals(data, that.data) &&
                    Objects.equals(weight, that.weight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, weight, parent, left, right);
        }
    }

    /**
     * 若有n个叶子结点，由于哈夫曼树没有度为1的结点，所以树结点总数为2n-1
     * 用容量为2n的数组来存储哈夫曼树，下标0不使用，用于表示无双亲或无孩子
     */
    private HuffmanNode<E>[] tree;

    /**
     * 构造哈夫曼树
     *
     * @param map key为数据，value为权值
     * @author pujian
     * @date 2023/3/30 10:44
     */
    public HuffmanTree(Map<E, Integer> map) {
        if (map == null || map.isEmpty()) return;
        // 叶子结点个数
        int size = map.size();
        // 数组长度
        int length = size << 1;
        // noinspection unchecked
        tree = (HuffmanNode<E>[]) Array.newInstance(HuffmanNode.class, length);

        // 存放集合U中所有子树的根结点的下标
        Set<Integer> available = new LinkedHashSet<>();
        // 存放两棵子树根结点的下标(它俩的权值最小)
        List<Integer> minimums = new ArrayList<>(2);
        /*
         * 构造哈夫曼树
         * 1、构造有n个带权的子树集合U{w1,w2,w3...wn}
         * 2、从U中找到根结点权值最小的两棵子树，以它俩的权值之和新建一个结点作为它们的父结点，构成一棵新的树
         * 3、从U中删除这两棵子树，并将这棵新的树放入集合U
         * 4、重复步骤2和3，直到U中只含有一棵树为止
         */
        Iterator<Map.Entry<E, Integer>> iterator = map.entrySet().iterator();
        // 将叶子结点从数组头部开始存入
        for (int i = 1; i <= size && iterator.hasNext(); i++) {
            Map.Entry<E, Integer> entry = iterator.next();
            tree[i] = new HuffmanNode<>(entry.getKey(), entry.getValue());
            // 将下标存入子树根结点下标集合U中
            available.add(i);
        }
        for (int parent = size + 1; parent < length; parent++) {
            // 找到权值最小的两棵子树
            select(available, minimums);
            int leftIndex = minimums.get(0);
            int rightIndex = minimums.get(1);
            HuffmanNode<E> left = tree[leftIndex];
            HuffmanNode<E> right = tree[rightIndex];
            // 将父结点和两棵子树关联起来
            left.parent = right.parent = parent;
            tree[parent] = new HuffmanNode<>();
            tree[parent].left = leftIndex;
            tree[parent].right = rightIndex;
            tree[parent].weight = left.weight + right.weight;

            // 将新的树加入集合U
            available.add(parent);
            minimums.clear();
        }
    }

    /**
     * 找到权值最小的两个结点的下标
     *
     * @param available 子树根结点下标的集合
     * @param minimums  返回结果
     * @author pujian
     * @date 2023/3/30 14:41
     */
    private void select(Set<Integer> available, List<Integer> minimums) {
        assert available.size() >= 2;
        for (int i = 0; i < 2; i++) {
            Iterator<Integer> iterator = available.iterator();
            iterator.hasNext();
            int min = iterator.next();
            while (iterator.hasNext()) {
                Integer index = iterator.next();
                if (tree[min].weight > tree[index].weight)
                    min = index;
            }
            minimums.add(min);
            // 将子树移出
            available.remove(min);
        }
    }


    public HuffmanNode<E>[] getTree() {
        return tree;
    }

}
