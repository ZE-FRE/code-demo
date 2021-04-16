package cn.zefre.proxy.jdk;

import cn.zefre.proxy.Greet;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author pujian
 * @date 2021/4/14 10:52
 */
public class DynamicGreetProxy {

    public static <T extends Greet> Greet getGreetProxy(T target, InvocationHandler handler) {
        return (Greet) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);
    }

}
