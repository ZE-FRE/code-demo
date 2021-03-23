package cn.zefre.decorator;

/**
 * @author pujian
 * @date 2021/3/22 11:17
 */
public class DarkRoast extends Beverage {

    public DarkRoast(String description) {
        super(description);
    }

    @Override
    public double cost() {
        return 10;
    }
}
