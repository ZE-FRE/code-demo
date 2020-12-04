package cn.zefre.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyUtil {

    public static Object getProxy(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        long start = System.nanoTime();
                        System.out.println("开始执行时间：" + start);
                        Object result = method.invoke(obj, args);
                        long end = System.nanoTime();
                        System.out.println("结束执行时间：" + end);
                        System.out.println(method.getName() + "执行花费时间：" + (end-start));
                        return result;
                    }
                });
    }
}
