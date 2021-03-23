package cn.zefre.decorator;

/**
 * @author pujian
 * @date 2021/3/22 11:22
 */
public class Decaf extends Beverage {

    public Decaf(String description) {
        super(description);
    }

    @Override
    public double cost() {
        return 15;
    }
}

