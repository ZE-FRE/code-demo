package cn.zefre.factory.method;

import cn.zefre.factory.product.Bus;
import cn.zefre.factory.product.Car;

/**
 * @author pujian
 * @date 2021/3/24 10:23
 */
public class BusFactory extends CarFactory {
    @Override
    public Car produce() {
        return new Bus("city bus");
    }
}
