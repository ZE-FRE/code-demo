package cn.zefre.jdk8.myinterface;

/**
 * @author pujian
 * @date 2020/10/21 14:50
 */
public interface AnotherDefaultInterface {
    default String defaultMethod() {
        return "我是另外一个接口的default关键字修饰的方法";
    }
}
