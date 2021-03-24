package cn.zefre.factory.method;

import cn.zefre.factory.product.Car;
import cn.zefre.factory.product.Tricycle;

/**
 * @author pujian
 * @date 2021/3/24 10:30
 */
public class TricycleFactory extends CarFactory {
    @Override
    public Car produce() {
        return new Tricycle("old tricycle");
    }
}
