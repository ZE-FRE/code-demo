package cn.zefre.proxy.jdk;

import cn.zefre.proxy.Greet;
import cn.zefre.proxy.common.GreetImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author pujian
 * @date 2021/4/14 11:01
 */
public class GreetInvocationHandler implements InvocationHandler {

    private Greet target;

    public GreetInvocationHandler(Greet target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("sayHello")) {
            System.out.println("starting sayHello method");
            target.sayHello();
            System.out.println("finishing sayHello method");
        } else if(method.getName().equals("saySo")) {
            System.out.println("starting saySo method");
            Object result = method.invoke(target, args);
            System.out.println("finishing saySo method");
            return result;
        }
        return null;
    }
}
