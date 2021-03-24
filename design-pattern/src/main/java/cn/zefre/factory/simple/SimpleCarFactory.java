package cn.zefre.factory.simple;

import cn.zefre.factory.CarType;
import cn.zefre.factory.product.Bus;
import cn.zefre.factory.product.Car;
import cn.zefre.factory.product.Tricycle;
import cn.zefre.factory.product.Truck;

/**
 * 简单工厂模式(静态工厂方法模式)
 * @author pujian
 * @date 2021/3/24 10:05
 */
public class SimpleCarFactory {

    /**
     * 生产汽车的方法
     * @param type
     * @author pujian
     * @date 2021/3/24 10:51
     * @return Car
     */
    public Car produce(CarType type) {
        Car car;
        switch (type) {
            case BUS:
                car = new Bus();
                break;
            case TRUCK:
                car = new Truck();
                break;
            case TRICYCLE:
                car = new Tricycle();
                break;
            default:
                car = null;
        }
        return car;
    }
}
