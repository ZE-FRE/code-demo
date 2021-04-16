package cn.zefre.proxy.common;

import cn.zefre.proxy.Greet;

/**
 * 代理类
 *
 * @author pujian
 * @date 2021/4/14 10:41
 */
public class GreetProxy implements Greet {

    private GreetImpl greet;

    public GreetProxy(GreetImpl greet) {
        this.greet = greet;
    }

    @Override
    public void sayHello() {
        System.out.println("before sayHello method is invoked print");
        greet.sayHello();
        System.out.println("after sayHello method is invoked print");
    }

    @Override
    public String saySo(String name) {
        String result;
        System.out.println("before saySo method is invoked print");
        result = greet.saySo(name);
        System.out.println("after saySo method is invoked print");
        return result;
    }
}
