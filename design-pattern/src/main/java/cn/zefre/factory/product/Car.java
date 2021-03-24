package cn.zefre.factory.product;

import lombok.Data;

/**
 * @author pujian
 * @date 2021/3/24 9:48
 */
@Data
public abstract class Car {

    private String name;

    public Car() {};

    public Car(String name) {
        this.name = name;
    }

    /**
     * 组装
     * @author pujian
     * @date 2021/3/24 9:56
     * @return
     */
    public abstract void assemble();

    /**
     * 质量检测
     * @author pujian
     * @date 2021/3/24 9:56
     * @return
     */
    public abstract void qualityTest();
}
