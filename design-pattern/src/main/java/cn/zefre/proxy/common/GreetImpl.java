package cn.zefre.proxy.common;

import cn.zefre.proxy.Greet;

/**
 * 被代理类
 *
 * @author pujian
 * @date 2021/4/14 10:40
 */
public class GreetImpl implements Greet {

    @Override
    public void sayHello() {
        System.out.println("hello world!");
    }

    @Override
    public String saySo(String name) {
        String result = name + ",why don't you say so?";
        System.out.println(result);
        return result;
    }
}
