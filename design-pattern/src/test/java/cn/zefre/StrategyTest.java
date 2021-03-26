package cn.zefre;

import cn.zefre.strategy.AliPay;
import cn.zefre.strategy.PayPal;
import cn.zefre.strategy.ShoppingCart;
import org.junit.Test;

/**
 * @author pujian
 * @date 2021/3/26 9:55
 */
public class StrategyTest {

    @Test
    public void testStrategy() {
        ShoppingCart shoppingCart = new ShoppingCart(new AliPay());
        shoppingCart.clear();
        // 购物车又增加了商品
        // ....省略具体代码

        // 更换支付方式
        shoppingCart.setPayment(new PayPal());
        shoppingCart.clear();
    }
}
