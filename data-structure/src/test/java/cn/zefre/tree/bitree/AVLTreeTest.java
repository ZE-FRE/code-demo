package cn.zefre.tree.bitree;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

/**
 * @author pujian
 * @date 2021/8/26 9:55
 */
public class AVLTreeTest {

    @Test
    public void testAdd() {
        AVLTree<Integer> avlTree = new AVLTree<>();
        avlTree.add(30);
        avlTree.add(22);
        avlTree.add(18);
        Assert.assertEquals(Arrays.asList(22,18,30), avlTree.sequenceOrder());
        /*
         * 第一次旋转(LL型，右单旋)
         *       30                 22
         *    22      ----->    18      30
         *  18
         */


        avlTree.add(16);
        avlTree.add(20);
        avlTree.add(8);
        Assert.assertEquals(Arrays.asList(18,16,22,8,20,30), avlTree.sequenceOrder());
        /*
         * 第二次旋转(LL型，右单旋)
         *           22                      18
         *       18      30              16      22
         *    16    20        ---->   8        20   30
         *  8
         */


        avlTree.add(12);
        Assert.assertEquals(Arrays.asList(18,12,22,8,16,20,30), avlTree.sequenceOrder());
        /*
         * 第三次旋转(LR型，先左旋，再右旋)
         *         18                         18                         18
         *     16      22                 16      22                12        22
         *  8       20    30   ---->   12      20    30   ---->   8    16   20   30
         *    12                     8
         */


        avlTree.add(3);
        avlTree.add(10);
        avlTree.add(9);
        Assert.assertEquals(Arrays.asList(18,10,22,8,12,20,30,3,9,16), avlTree.sequenceOrder());
        /*
         * 第四次旋转(LR型，先左旋，再右旋)
         *               18                                 18                                  18
         *         12          22                     12           22                    10             22
         *      8     16    20    30    ---->      10    16     20     30  ---->      8      12      20     30
         *   3    10                             8                                  3   9      16
         *       9                             3   9
         *
         */


        avlTree.add(45);
        avlTree.add(60);
        Assert.assertEquals(Arrays.asList(18,10,22,8,12,20,45,3,9,16,30,60), avlTree.sequenceOrder());
        /*
         * 第五次旋转(RR型，左单旋)
         *                  18                                             18
         *         10                22                           10                22
         *     8       12        20       30         ---->     8      12         20     45
         *   3   9       16                  45              3   9       16           30  60
         *                                      60
         */


        avlTree.add(50);
        Assert.assertEquals(Arrays.asList(18,10,45,8,12,22,60,3,9,16,20,30,50), avlTree.sequenceOrder());
        /*
         * 第六次旋转(RR型，左单旋)
         *                  18                                              18
         *          10               22                             10               45
         *      8       12       20      45          ---->      8       12       22       60
         *    3   9        16         30    60                3   9        16  20   30  50
         *                                50
         */


        avlTree.add(25);
        avlTree.add(35);
        avlTree.add(28);
        Assert.assertEquals(Arrays.asList(18,10,45,8,12,25,60,3,9,16,22,30,50,20,28,35), avlTree.sequenceOrder());
        /*
         * 第七次旋转(RL型，先右旋，再左旋)
         *                     18                                               18                                                 18
         *          10                     45                       10                       45                       10                         45
         *      8      12           22           60  ---->      8       12            22             60  ---->    8       12              25            60
         *    3   9      16     20      30    50              3   9       16      20      25       50           3   9        16       22      30      50
         *                           25   35                                                 30                                     20      28  35
         *                             28                                                  28  35
         */


        avlTree.add(14);
        Assert.assertEquals(Arrays.asList(18,10,45,8,14,25,60,3,9,12,16,22,30,50,20,28,35), avlTree.sequenceOrder());
        /*
         * 第八次旋转(RL型，先右旋，再左旋)
         *                      18                                              18                                                 18
         *           10                    45                       10                       45                       10                        45
         *      8        12          25          60  ---->     8       12             25          60   ---->    8           14            25            60
         *    3   9         16    22    30     50            3   9        14       22    30     50           3   9       12    16     22      30     50
         *                14     20   28 35                                 16   20    28  35                                       20      28  35
         */


        avlTree.add(27);
        /*
         * 第九次旋转(LR型，先左旋，再右旋)
         *                       18                                                      18                                                       18
         *          10                        45                           10                           45                          10                            30
         *     8        14            25             60    ---->     8           14               30            60     ---->    8        14                 25          45
         *   3   9   12   16     22        30      50              3   9      12    16        25      35     50               3   9    12  16           22      28   35    60
         *                     20       28    35                                           22    28                                                   20      27         50
         *                            27                                                 20    27
         *
         */

        List<Integer> inOrderList = Arrays.asList(3,8,9,10,12,14,16,18,20,22,25,27,28,30,35,45,50,60);
        Assert.assertEquals(inOrderList, avlTree.inOrder());
        List<Integer> sequenceOrderList = Arrays.asList(18,10,30,8,14,25,45,3,9,12,16,22,28,35,60,20,27,50);
        Assert.assertEquals(sequenceOrderList, avlTree.sequenceOrder());
    }


    @Test
    public void testGet() {
        AVLTree<Integer> avlTree = new AVLTree<>();
        for (Integer num : Arrays.asList(30,22,18,16,20,8,12,3,10,9)) {
            avlTree.add(num);
        }

        AVLTree.Node<Integer> node = avlTree.get(8);
        Assert.assertEquals(Integer.valueOf(8), node.data);
        Assert.assertEquals(Integer.valueOf(3), node.left.data);
        Assert.assertEquals(Integer.valueOf(9), node.right.data);
    }
}
