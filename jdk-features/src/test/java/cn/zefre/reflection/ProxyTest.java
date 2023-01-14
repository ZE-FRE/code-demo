package cn.zefre.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author pujian
 * @date 2022/10/12 15:00
 */
public class ProxyTest {

    interface Parent {
        void sayHello();
    }

    static class Child implements Parent {
        @Override
        public void sayHello() {
            System.out.println("hello");
        }
    }

    @Test
    public void testMethod() throws NoSuchMethodException {
        Child target = new Child();
        Method sayHelloMethod = Parent.class.getDeclaredMethod("sayHello");
        System.out.println(sayHelloMethod.getDeclaringClass());
        Object proxy = Proxy.newProxyInstance(ProxyTest.class.getClassLoader(), new Class[]{Parent.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("sayHello")) {
                    Class<?> declaringClass = method.getDeclaringClass();
                    System.out.println("定义类是：" + declaringClass + " 是否相等：" + (method.equals(sayHelloMethod)));
                    method.invoke(target);
                }
                return null;
            }
        });
        Parent parent = (Parent) proxy;
        parent.sayHello();
    }

}
