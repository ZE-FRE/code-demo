package cn.zefre.tree.bitree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static cn.zefre.tree.bitree.HuffmanTree.HuffmanNode;

/**
 * @author pujian
 * @date 2023/3/30 10:27
 */
public class HuffmanTreeTest {

    @Test
    public void testConstructor() {
        Map<Character, Integer> characterMap = new HashMap<>();
        characterMap.put('a', 5);
        characterMap.put('b', 15);
        characterMap.put('c', 10);
        characterMap.put('d', 12);
        characterMap.put('e', 4);

        /*
         * 构造如下一棵哈夫曼树
         *               (46)[9]
         *          /               \
         *       (19)[7]           (27)[8]
         *       /      \          /      \
         *    (9)[6]   c(10)[3]  d(12)[4] b(15)[2]
         *    /     \
         * e(4)[5] a(5)[1]
         *
         * ()里面的数字表示权值，[]里面的数字表示在数组中的下标
         * wpl=4*3+5*3+10*2+12*2+15*2=101
         *
         * 用表格展示数组的情况：
         * 初始的时候：
         * index  data  weight  parent  left    right
         *   1      a     5       0       0       0
         *   2      b     15      0       0       0
         *   3      c     10      0       0       0
         *   4      d     12      0       0       0
         *   5      e     4       0       0       0
         *   6      -     0       0       0       0
         *   7      -     0       0       0       0
         *   8      -     0       0       0       0
         *   9      -     0       0       0       0
         * 构造完成后：
         * index  data  weight  parent  left    right
         *   1      a     5       6       0       0
         *   2      b     15      8       0       0
         *   3      c     10      7       0       0
         *   4      d     12      8       0       0
         *   5      e     4       6       0       0
         *   6      -     9       7       5       1
         *   7      -     19      9       6       3
         *   8      -     27      9       4       2
         *   9      -     46      0       7       8
         */
        HuffmanTree<Character> huffmanTree = new HuffmanTree<>(characterMap);
        Object[] expected =
                {
                        null,
                        new HuffmanNode<>('a', 5, 6, 0, 0),
                        new HuffmanNode<>('b', 15, 8, 0, 0),
                        new HuffmanNode<>('c', 10, 7, 0, 0),
                        new HuffmanNode<>('d', 12, 8, 0, 0),
                        new HuffmanNode<>('e', 4, 6, 0, 0),
                        new HuffmanNode<>(null, 9, 7, 5, 1),
                        new HuffmanNode<>(null, 19, 9, 6, 3),
                        new HuffmanNode<>(null, 27, 9, 4, 2),
                        new HuffmanNode<>(null, 46, 0, 7, 8)
                };
        Assertions.assertArrayEquals(expected, huffmanTree.getTree());
    }

}
