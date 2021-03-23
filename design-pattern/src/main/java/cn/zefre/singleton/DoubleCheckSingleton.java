package cn.zefre.singleton;

/**
 * @author pujian
 * @date 2021/3/23 16:25
 */
public class DoubleCheckSingleton {

    private static volatile DoubleCheckSingleton instance;

    private DoubleCheckSingleton() {}

    public static DoubleCheckSingleton getInstance() {
        if (null == instance) {
            synchronized (DoubleCheckSingleton.class) {
                // double-check
                if(null == instance) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
