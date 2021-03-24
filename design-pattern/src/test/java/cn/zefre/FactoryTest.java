package cn.zefre;

import cn.zefre.factory.method.BusFactory;
import cn.zefre.factory.method.CarFactory;
import cn.zefre.factory.method.TricycleFactory;
import cn.zefre.factory.method.TruckFactory;
import cn.zefre.factory.product.Car;
import cn.zefre.factory.virtual.BenzFactory;
import cn.zefre.factory.virtual.Factory;
import cn.zefre.factory.virtual.component.Engine;
import cn.zefre.factory.virtual.component.Tyre;
import org.junit.Test;

/**
 * @author pujian
 * @date 2021/3/24 14:55
 */
public class FactoryTest {

    @Test
    public void testFactoryMethodPattern() {
        CarFactory factory = new BusFactory();
        factory.delivery();
        factory = new TruckFactory();
        factory.delivery();
        factory = new TricycleFactory();
        factory.delivery();
    }

    @Test
    public void testAbstractFactoryPattern() {
        Factory factory = new BenzFactory();
        Engine engine = factory.produceEngine();
        Tyre tyre = factory.produceTyre();
        System.out.println(engine.getName());
        System.out.println(tyre.getName());
    }
}
