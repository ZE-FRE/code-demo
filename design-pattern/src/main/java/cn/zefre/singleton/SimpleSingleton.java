package cn.zefre.singleton;

/**
 * @author pujian
 * @date 2021/3/23 16:21
 */
public class SimpleSingleton {

    private static SimpleSingleton instance = new SimpleSingleton();

    private SimpleSingleton() {}

    public static SimpleSingleton getInstance() {
        return instance;
    }

}
