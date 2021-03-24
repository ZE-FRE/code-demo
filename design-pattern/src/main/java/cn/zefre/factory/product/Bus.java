package cn.zefre.factory.product;

/**
 * 汽车
 * @author pujian
 * @date 2021/3/24 9:53
 */
public class Bus extends Car {

    public Bus() {}

    public Bus(String name) {
        super(name);
    }

    @Override
    public void assemble() {
        System.out.println("assemble the bus");
    }

    @Override
    public void qualityTest() {
        System.out.println("testing the bus");
    }

}
