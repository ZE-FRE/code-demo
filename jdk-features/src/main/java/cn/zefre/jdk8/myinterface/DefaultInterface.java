package cn.zefre.jdk8.myinterface;

/**
 * @author pujian
 * @date 2020/10/21 14:46
 */
public interface DefaultInterface {

    default String defaultMethod() {
        return "我是接口的default关键字修饰的方法";
    }
}
