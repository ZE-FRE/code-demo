package cn.zefre;

import cn.zefre.singleton.DoubleCheckSingleton;
import cn.zefre.singleton.LazySingleton;
import cn.zefre.singleton.SimpleSingleton;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author pujian
 * @date 2021/3/24 9:14
 */
public class SingletonTest {

    @Test
    public void testSingleton() {
        SimpleSingleton simpleSingleton1 = SimpleSingleton.getInstance();
        SimpleSingleton simpleSingleton2 = SimpleSingleton.getInstance();

        LazySingleton lazySingleton1 = LazySingleton.getInstance();
        LazySingleton lazySingleton2 = LazySingleton.getInstance();

        DoubleCheckSingleton doubleCheckSingleton1 = DoubleCheckSingleton.getInstance();
        DoubleCheckSingleton doubleCheckSingleton2 = DoubleCheckSingleton.getInstance();

        Assert.assertEquals(simpleSingleton1, simpleSingleton2);
        Assert.assertEquals(lazySingleton1, lazySingleton2);
        Assert.assertEquals(doubleCheckSingleton1, doubleCheckSingleton2);
    }
}
