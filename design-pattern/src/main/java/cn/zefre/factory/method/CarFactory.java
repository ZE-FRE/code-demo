package cn.zefre.factory.method;

import cn.zefre.factory.product.Car;

/**
 * 工厂方法模式
 * @author pujian
 * @date 2021/3/24 10:22
 */
public abstract class CarFactory {

    /**
     * 出厂
     * @author pujian
     * @date 2021/3/24 10:24
     * @return
     */
    public void delivery() {
        // 生产汽车
        Car car = produce();

        // 组装
        car.assemble();
        // 质量检测
        car.qualityTest();
        System.out.println("complete delivery");
    }

    public abstract Car produce();
}
