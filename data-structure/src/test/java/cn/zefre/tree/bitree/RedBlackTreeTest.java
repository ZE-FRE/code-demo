package cn.zefre.tree.bitree;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

/**
 * @author pujian
 * @date 2021/9/8 16:42
 */
public class RedBlackTreeTest {

    @Test
    public void testAdd() {
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.add(40);
        redBlackTree.add(70);
        /*
         *      40(黑)
         *          \
         *          70(红)
         */

        redBlackTree.add(90);
        /*
         *      40(黑)                       70(黑)
         *          \        左旋，变色     /     \
         *          70(红)    ---->      40(红)  90(红)
         *            \
         *          90(红)
         */

        redBlackTree.add(20);
        /*
         *        70(黑)                        70(红)                        70(黑)
         *      /     \         变色          /     \       根节点变色        /     \
         *    40(红)  90(红)    ---->       40(黑)  90(黑)    ---->        40(黑)  90(黑)
         *    /                             /                             /
         *  20(红)                        20(红)                        20(红)
         */

        redBlackTree.add(60);
        redBlackTree.add(80);
        redBlackTree.add(120);
        /*
         *           70(黑)
         *        /         \
         *    40(黑)         90(黑)
         *   /    \         /    \
         * 20(红)  60(红)  80(红)  120(红)
         */

        redBlackTree.add(50);
        /*
         *           70(黑)
         *        /         \
         *    40(红)         90(黑)
         *   /    \         /    \
         * 20(黑)  60(黑)  80(红)  120(红)
         *         /
         *       50(红)
         */

        redBlackTree.add(45);
        /*
         *           70(黑)                                             70(黑)
         *        /         \                                       /           \
         *    40(红)         90(黑)                               40(红)         90(黑)
         *   /    \         /    \           右旋，变色           /    \         /    \
         * 20(黑)  60(黑)  80(红)  120(红)      ---->         20(黑)  50(黑)   80(红)  120(红)
         *         /                                                /   \
         *       50(红)                                           45(红) 60(红)
         *        /
         *      45(红)
         */

        redBlackTree.add(48);
        Assert.assertEquals(Arrays.asList(50,40,70,20,45,60,90,48,80,120), redBlackTree.sequence(OrderEnum.SEQUENCE));
        /*
         *           70(黑)                                             70(黑)
         *        /         \                                       /           \
         *    40(红)         90(黑)                               40(红)         90(黑)
         *   /    \         /    \             变色             /    \         /    \                左旋
         * 20(黑)  50(黑)  80(红)  120(红)      ---->         20(黑)  50(红)   80(红)  120(红)        ---->
         *        /   \                                             /   \
         *      45(红) 60(红)                                      45(黑) 60(黑)
         *        \                                                 \
         *        48(红)                                            48(红)
         *
         *                   70(黑)                                               50(黑)
         *              /             \                                     /               \
         *           50(红)          90(黑)                               40(红)            70(红)
         *         /      \         /     \         右旋，变色           /     \          /      \
         *      40(红)    60(黑)  80(红)  120(红)      ---->          20(黑)  45(黑)    60(黑)  90(黑)
         *     /      \                                                        \             /      \
         * 20(黑)    45(黑)                                                     48(红)     80(红)  120(红)
         *            \
         *            48(红)
         *
         */

    }
}
