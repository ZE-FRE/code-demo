package cn.zefre.factory.method;

import cn.zefre.factory.product.Car;
import cn.zefre.factory.product.Truck;

/**
 * @author pujian
 * @date 2021/3/24 10:29
 */
public class TruckFactory extends CarFactory {
    @Override
    public Car produce() {
        return new Truck("express truck");
    }
}
