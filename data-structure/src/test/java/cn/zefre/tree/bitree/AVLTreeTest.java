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
        /*
         * 第一次旋转(最小不平衡子树根结点是30，LL型，右旋)
         *       30                  22
         *      /    绕结点30右旋    /   \
         *    22      ----->      18    30
         *   /
         *  18
         */

        avlTree.add(16);
        avlTree.add(20);
        avlTree.add(8);
        /*
         * 第二次旋转(最小不平衡子树根结点是22，LL型，右旋)
         *           22                           18
         *         /    \                       /    \
         *       18      30                   16      22
         *      /  \          绕结点22右旋     /       /  \
         *    16   20           ---->        8      20   30
         *    /
         *   8
         */

        avlTree.add(12);
        /*
         * 第三次旋转(最小不平衡子树根结点是16，LR型，先左旋，再右旋)
         *         18                              18                                18
         *       /    \                          /    \                            /    \
         *     16      22                      16      22                        12      22
         *     /      /  \     绕结点8左旋     /       /   \      绕结点16右旋     /  \    /  \
         *    8      20   30     ---->      12       20    30      ---->       8    16 20   30
         *     \                           /
         *     12                         8
         */


        avlTree.add(3);
        avlTree.add(10);
        avlTree.add(9);
        /*
         * 第四次旋转(最小不平衡子树根结点是12，LR型，先左旋，再右旋)
         *               18                                 18                                 18
         *           /        \                         /        \                         /        \
         *         12          22                     12          22                      10         22
         *       /    \       /   \    绕结点8左旋    /   \       /   \   绕结点12右旋     /   \       /   \
         *      8     16     20    30    ---->     10    16    20    30    ---->       8     12    20    30
         *     / \                                 /                                  / \      \
         *    3   10                              8                                  3   9      16
         *        /                              / \
         *       9                              3   9
         *
         */

        avlTree.add(45);
        avlTree.add(60);
        /*
         * 第五次旋转(最小不平衡子树根结点是30，RR型，左旋)
         *               18                                         18
         *           /        \                                /          \
         *         10          22                             10           22
         *       /   \        /   \        绕结点30左旋       /   \        /   \
         *      8     12     20   30         ---->         8      12    20     45
         *     / \      \           \                     / \       \          / \
         *    3   9     16           45                  3   9       16      30   60
         *                            \
         *                             60
         */

        avlTree.add(50);
        /*
         * 第六次旋转(最小不平衡子树根结点是22，RR型，左旋)
         *                  18                                              18
         *            /           \                                   /            \
         *          10             22                               10              45
         *        /    \         /    \        绕结点22左旋         /    \         /     \
         *       8     12       20     45         ---->           8     12      22      60
         *      / \      \            /   \                      / \      \    /  \     /
         *     3   9      16         30   60                    3   9     16  20  30  50
         *                                /
         *                               50
         */

        avlTree.add(25);
        avlTree.add(35);
        avlTree.add(28);
        /*
         * 第七次旋转(最小不平衡子树根结点是22，RL型，先右旋，再左旋)
         *             18                                       18                                         18
         *       /            \                          /              \                           /              \
         *     10             45                       10                45                       10                45
         *    /   \          /    \    绕结点30右旋    /   \            /     \    绕结点22左旋    /   \            /      \
         *   8    12       22      60     ---->      8    12         22       60    ---->      8     12         25       60
         *  / \     \     /  \     /                / \     \      /    \     /               / \      \       /   \     /
         * 3   9    16   20   30  50               3   9    16    20    25   50              3   9     16     22   30   50
         *                   /  \                                         \                                  /     / \
         *                  25  35                                        30                                20    28 35
         *                   \                                            / \
         *                   28                                          28 35
         */

        avlTree.add(14);
        /*
         * 第八次旋转(最小不平衡子树根结点是12，RL型，先右旋，再左旋)
         *                 18                                                          18
         *         /               \                                           /               \
         *       10                 45                                       10                 45
         *    /     \             /     \   绕结点16右旋，再绕结点12右旋     /      \            /     \
         *   8       12         25      60          ---->                8       14         25       60
         *  / \        \       /   \    /                               / \     /   \      /   \     /
         * 3   9       16     22   30  50                              3   9  12    16    22   30   50
         *             /     /    /  \                                                   /     / \
         *           14     20   28  35                                                 20    28  35
         */

        avlTree.add(27);
        /*
         * 第九次旋转(最小不平衡子树根结点是45，LR型，先左旋，再右旋)
         *                   18                                           18                                              18
         *          /                 \                          /                 \                             /                 \
         *        10                  45                       10                   45                        10                   30
         *     /      \            /      \    绕结点25左旋   /    \              /      \     绕结点45右旋    /    \              /      \
         *    8       14          25      60     ---->      8      14          30       60      ---->      8      14          25        45
         *   / \     /   \       /  \     /                / \    /  \       /    \     /                 / \    /  \        /  \      /  \
         *  3   9   12   16    22   30   50               3   9  12  16     25    35   50                3   9  12  16      22  28    35  60
         *                    /    /  \                                    /  \                                            /    /         /
         *                   20   28  35                                  22  28                                          20   27        50
         *                       /                                       /    /
         *                      27                                      20   27
         *
         */

        List<Integer> sequenceOrderList = Arrays.asList(18,10,30,8,14,25,45,3,9,12,16,22,28,35,60,20,27,50);
        Assert.assertEquals(sequenceOrderList, avlTree.sequence());

        // 插入已存在元素
        Assert.assertFalse(avlTree.add(16));
    }


    @Test
    public void testRemove() {
        AVLTree<Integer> avlTree = new AVLTree<>();
        // 删除avl树只有一个结点的情况
        avlTree.add(100);
        avlTree.remove(100);
        Assert.assertTrue(avlTree.sequence().isEmpty());

        /*
         * 构造一棵avl树
         *                   18
         *          /                 \
         *        10                   30
         *      /    \              /      \
         *     8      14          25        45
         *    / \    /  \        /  \      /  \
         *   3   9  12  16      22  28    35  60
         *                     /    /         /
         *                    20   27        50
         */
        for (Integer num : Arrays.asList(30,22,18,16,20,8,12,3,10,9,45,60,50,25,35,28,14,27)) {
            avlTree.add(num);
        }

        // 删除结点不存在
        Assert.assertFalse(avlTree.remove(55));

        // 删除叶子结点
        avlTree.remove(35);
        /*
         * 最小不平衡子树根结点是45
         *
         *                   18                                                  18
         *          /                 \                                 /                 \
         *        10                   30                             10                   30
         *      /    \              /      \       右旋60，左旋45    /    \              /      \
         *     8      14          25        45        ---->        8      14          25       50
         *    / \    /  \        /  \         \                   / \    /  \        /  \     /  \
         *   3   9  12  16      22  28        60                 3   9  12  16      22  28   45  60
         *                     /    /         /                                    /    /
         *                    20   27        50                                   20   27
         */

        // 删除只有左子树结点
        avlTree.remove(28);
        /*
         *                   18
         *          /                 \
         *        10                   30
         *      /    \              /      \
         *     8      14          25       50
         *    / \    /  \        /  \     /  \
         *   3   9  12  16      22  27   45  60
         *                     /
         *                    20
         */

        // 删除结点左右子树都存在，且直接后继是它的右结点
        avlTree.remove(25);
        /*
         * 最小不平衡子树根结点是27
         *
         *                 18                                      18
         *          /             \                          /             \
         *        10               30                      10               30
         *      /    \           /    \         右旋27    /    \           /    \
         *     8      14        27    50        ---->   8      14        22     50
         *    / \    /  \      /     /  \              / \    /  \      /  \   /  \
         *   3   9  12  16    22    45  60            3   9  12  16    20  27 45  60
         *                   /
         *                  20
         */

        // 删除结点左右子树都存在，且直接后继不是它的右结点
        avlTree.remove(18);
        /*
         *                 20
         *          /             \
         *        10               30
         *      /    \           /    \
         *     8      14        22    50
         *    / \    /  \         \   /  \
         *   3   9  12  16        27 45  60
         */

        // 删除结点只有右子树
        avlTree.remove(22);
        /*
         *                 20
         *          /             \
         *        10               30
         *      /    \           /    \
         *     8      14        27    50
         *    / \    /  \            /  \
         *   3   9  12  16          45  60
         */

        avlTree.remove(27);
        /*
         * 最小不平衡子树根结点是30
         *
         *                 20                                    20
         *          /             \                          /         \
         *        10               30                      10          50
         *      /    \               \        右旋30      /    \      /   \
         *     8      14              50      ---->     8      14   30    60
         *    / \    /  \            /  \              / \    /  \    \
         *   3   9  12  16          45  60            3   9  12  16    45
         */

        Assert.assertEquals(Arrays.asList(20,10,50,8,14,30,60,3,9,12,16,45), avlTree.sequence());
    }
}
