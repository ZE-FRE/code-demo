package cn.zefre.decorator;

/**
 * @author pujian
 * @date 2021/3/22 11:24
 */
public class Milk extends CondimentDecorator {

    private Beverage beverage;

    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",Milk";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.2;
    }
}
