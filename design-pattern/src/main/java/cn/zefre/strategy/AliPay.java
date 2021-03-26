package cn.zefre.strategy;

/**
 * 策略模式的实际策略类
 * @author pujian
 * @date 2021/3/26 9:54
 */
public class AliPay implements Payment {
    @Override
    public void pay() {
        System.out.println("使用支付宝支付");
    }
}
