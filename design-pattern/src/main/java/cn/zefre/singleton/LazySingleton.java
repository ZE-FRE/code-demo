package cn.zefre.singleton;

/**
 * @author pujian
 * @date 2021/3/23 16:22
 */
public class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {}

//    public static LazySingleton getInstance() {
//        if(instance == null) {
//            instance = new LazySingleton();
//        }
//        return instance;
//    }

    public static synchronized LazySingleton getInstance() {
        if(instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
