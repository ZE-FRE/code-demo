package cn.zefre.jdk8.myinterface.impl;


import cn.zefre.jdk8.myinterface.AnotherDefaultInterface;
import cn.zefre.jdk8.myinterface.DefaultInterface;

/**
 * @author pujian
 * @date 2020/10/21 14:51
 */
public class DoubleDefaultInterfaceImpl implements DefaultInterface, AnotherDefaultInterface {
    @Override
    public String defaultMethod() {
        return "default方法同名，实现类必须重写default方法";
    }
}
