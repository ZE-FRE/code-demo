package cn.zefre.tree.bitree;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static cn.zefre.tree.bitree.HuffmanTree.HuffmanNode;

/**
 * 哈夫曼编码
 *
 * @author pujian
 * @date 2023/3/30 16:41
 */
public class HuffmanCode {

    /**
     * key为字符，value为字符的哈夫曼编码
     */
    private Map<Character, Character[]> charset;
    /**
     * 哈夫曼树
     */
    private HuffmanNode<Character>[] tree;

    public Map<Character, Character[]> getCharset() {
        return charset;
    }

    /**
     * 利用哈夫曼树构造哈夫曼编码
     *
     * @param charMap 字符集，key为字符，value为字符的权(出现的频率)
     * @author pujian
     * @date 2023/3/30 16:58
     */
    public HuffmanCode(Map<Character, Integer> charMap) {
        if (charMap == null || charMap.isEmpty()) throw new IllegalArgumentException("字符集不能为空");
        HuffmanTree<Character> huffmanTree = new HuffmanTree<>(charMap);
        this.tree = huffmanTree.getTree();
        this.charset = new HashMap<>(charMap.size());
        // 从根结点开始构建哈夫曼编码
        constructFromRoot();
        // 从叶子结点开始构建哈夫曼编码
        //constructFromLeaf();
    }


    /**
     * 从根结点非递归遍历哈夫曼树，构建哈夫曼编码
     *
     * @author pujian
     * @date 2023/3/31 16:04
     */
    private void constructFromRoot() {
        /*
         * 重置所有结点的weight，用于遍历
         * 0：表示此结点还未被遍历
         * 1：表示此结点已被遍历一次
         * 2：表示此结点已被遍历两次
         */
        for (int i = 1; i < tree.length; i++) tree[i].weight = 0;

        HuffmanNode<Character> root = tree[tree.length - 1];
        Deque<Character> stack = new LinkedList<>();
        HuffmanNode<Character> node = root;
        while (node != null) {
            if (node.weight == 0) {
                node.weight = 1;
                if (node.left != 0) {
                    // 记录编码0
                    stack.addLast('0');
                    node = tree[node.left];
                }
                // 此结点是叶子结点
                else if (node.right == 0) charset.put(node.data, stack.toArray(new Character[0]));
            } else if (node.weight == 1) { // 表示结点左子树已遍历完毕
                node.weight = 2;
                // 遍历右子树
                if (node.right != 0) {
                    // 不是叶子结点，记录编码1
                    stack.addLast('1');
                    node = tree[node.right];
                }
            } else { // 左右子树遍历完毕
                // 重置结点的weight
                node.weight = 0;
                // 退回父结点
                node = tree[node.parent];
                stack.pollLast();
            }
        }
    }

    /**
     * 从根结点递归遍历哈夫曼树，构建哈夫曼编码
     *
     * @author pujian
     * @date 2023/3/31 14:24
     */
    private void constructFromRootRecursive() {
        LinkedList<Character> list = new LinkedList<>();
        constructFromRootRecursive(tree[tree.length - 1], list);
    }

    /**
     * 递归遍历哈夫曼树，构建哈夫曼编码
     *
     * @param node  node
     * @param stack stack
     * @author pujian
     * @date 2023/3/31 14:14
     */
    private void constructFromRootRecursive(HuffmanNode<Character> node, Deque<Character> stack) {
        if (node.left != 0) {
            stack.addLast('0');
            constructFromRootRecursive(tree[node.left], stack);
            stack.removeLast();
        } else {
            this.charset.put(node.data, stack.toArray(new Character[0]));
        }
        if (node.right != 0) {
            stack.addLast('1');
            constructFromRootRecursive(tree[node.right], stack);
            stack.removeLast();
        } else {
            this.charset.put(node.data, stack.toArray(new Character[0]));
        }
    }

    /**
     * 从叶子结点开始构建哈夫曼编码
     *
     * @author pujian
     * @date 2023/3/31 11:42
     */
    private void constructFromLeaf() {
        int leafIndex = 1;
        HuffmanNode<Character> leaf = tree[leafIndex];
        for (; leaf.data != null; leafIndex++, leaf = tree[leafIndex]) {
            // 字符
            Character character = leaf.data;
            // 字符的编码
            Deque<Character> stack = new LinkedList<>();
            int index = leafIndex;
            HuffmanNode<Character> node = tree[index];
            while (node.parent != 0) {
                HuffmanNode<Character> parent = tree[node.parent];
                if (index == parent.left)
                    stack.push('0');
                else
                    stack.push('1');
                index = node.parent;
                node = tree[index];
            }
            this.charset.put(character, stack.toArray(new Character[0]));
        }
    }

    /**
     * 对字符串进行编码
     *
     * @param msg 消息
     * @return java.lang.String
     * @author pujian
     * @date 2023/3/31 11:08
     */
    public String encode(String msg) {
        StringBuilder sb = new StringBuilder();
        for (char c : msg.toCharArray()) {
            Character[] codes = charset.get(c);
            if (codes == null) throw new IllegalArgumentException("unknown character '" + c + "'");
            for (Character code : codes) sb.append((char) code);
        }
        return sb.toString();
    }

    /**
     * 解码
     *
     * @param encodedMsg 被编码的消息
     * @return java.lang.String
     * @author pujian
     * @date 2023/3/31 11:29
     */
    public String decode(String encodedMsg) {
        HuffmanNode<Character> root = tree[tree.length - 1];
        StringBuilder sb = new StringBuilder();
        HuffmanNode<Character> node = root;
        for (char code : encodedMsg.toCharArray()) {
            node = code == '0' ? tree[node.left] : tree[node.right];
            if (node.left == 0 || node.right == 0) {
                // 遍历到叶子结点，记录叶子结点的字符
                sb.append((char) node.data);
                // 回到根结点，解码下一个字符
                node = root;
            }
        }
        return sb.toString();
    }

}
