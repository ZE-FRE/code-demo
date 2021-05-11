package cn.zefre;

import cn.zefre.proxy.Greet;
import cn.zefre.proxy.cglib.ProxyFactory;
import cn.zefre.proxy.cglib.UserDao;
import cn.zefre.proxy.common.GreetImpl;
import cn.zefre.proxy.common.GreetProxy;
import cn.zefre.proxy.jdk.DynamicGreetProxy;
import cn.zefre.proxy.jdk.GreetInvocationHandler;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;

/**
 * @author pujian
 * @date 2021/4/16 15:55
 */
public class ProxyTest {

    @Test
    public void testCommon() {
        Greet greetProxy = new GreetProxy();
        greetProxy.sayHello();
        greetProxy.saySo("张三");
    }

    @Test
    public void testJdkProxy() {
        GreetImpl greet = new GreetImpl();
        InvocationHandler invocationHandler = new GreetInvocationHandler(greet);
        Greet greetProxy = DynamicGreetProxy.getGreetProxy(greet, invocationHandler);
        greetProxy.sayHello();
        greetProxy.saySo("张三");
    }

    @Test
    public void testCglibProxy() {
        UserDao userDaoProxy = ProxyFactory.getDaoProxy();
        userDaoProxy.selectOne("张三");
        userDaoProxy.update("张三");
    }
}
