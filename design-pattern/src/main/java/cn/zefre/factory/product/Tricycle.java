package cn.zefre.factory.product;

/**
 * 三轮车
 * @author pujian
 * @date 2021/3/24 10:04
 */
public class Tricycle extends Car {

    public Tricycle() {}

    public Tricycle(String name) {
        super(name);
    }

    @Override
    public void assemble() {
        System.out.println("assemble the tricycle");
    }

    @Override
    public void qualityTest() {
        System.out.println("testing the tricycle");
    }

}
