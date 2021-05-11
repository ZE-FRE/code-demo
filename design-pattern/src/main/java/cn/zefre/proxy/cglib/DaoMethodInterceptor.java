package cn.zefre.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

/**
 * @author pujian
 * @date 2021/4/16 15:43
 */
public class DaoMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("starting execute " + method.getName() + " method...");
        // proxy是代理对象，这一行可能会造成死循环
        // method.invoke(proxy, args);
        methodProxy.invokeSuper(proxy, args);
        System.out.println(method.getName() + " has been executed");
        return null;
    }
}
