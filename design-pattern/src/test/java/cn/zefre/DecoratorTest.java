package cn.zefre;

import cn.zefre.decorator.Beverage;
import cn.zefre.decorator.Decaf;
import cn.zefre.decorator.Milk;
import cn.zefre.decorator.Mocha;
import org.junit.Test;

/**
 * @author pujian
 * @date 2021/3/22 11:28
 */
public class DecoratorTest {

    @Test
    public void testMilkDoubleMochaDecaf() {

        Beverage beverage = new Decaf("Decaf");
        beverage = new Mocha(beverage);
        beverage = new Mocha(beverage);
        beverage = new Milk(beverage);
        System.out.println(beverage.getDescription() + " cost $" + beverage.cost());
    }
}
