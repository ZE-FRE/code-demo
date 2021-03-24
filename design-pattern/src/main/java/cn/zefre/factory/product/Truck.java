package cn.zefre.factory.product;

/**
 * 卡车
 * @author pujian
 * @date 2021/3/24 9:59
 */
public class Truck extends Car {

    public Truck() {}

    public Truck(String name) {
        super(name);
    }

    @Override
    public void assemble() {
        System.out.println("assemble the truck");
    }

    @Override
    public void qualityTest() {
        System.out.println("testing the truck");
    }

}
