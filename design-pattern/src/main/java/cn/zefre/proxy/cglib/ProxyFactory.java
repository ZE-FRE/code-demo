package cn.zefre.proxy.cglib;

import cn.zefre.proxy.Greet;
import cn.zefre.proxy.common.GreetImpl;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author pujian
 * @date 2021/4/16 15:08
 */
public class ProxyFactory {

    public static UserDao getDaoProxy() {
        DaoMethodInterceptor interceptor = new DaoMethodInterceptor();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserDao.class);
        enhancer.setCallback(interceptor);
        return (UserDao) enhancer.create();
    }
}
