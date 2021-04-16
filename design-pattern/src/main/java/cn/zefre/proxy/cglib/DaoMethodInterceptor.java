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
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("starting execute " + method.getName() + " method...");
        // method.invoke(target, args);
        methodProxy.invokeSuper(target, args);
        System.out.println(method.getName() + " has been executed");
        return null;
    }
}
