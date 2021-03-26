package cn.zefre.strategy;

/**
 * 策略模式的Context
 * @author pujian
 * @date 2021/3/26 9:50
 */
public class ShoppingCart {

    private Payment payment;

    public ShoppingCart(Payment payment) {
        this.payment = payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * 清空购物车
     * @author pujian
     * @date 2021/3/26 9:52
     * @return
     */
    public void clear() {
        // 支付
        payment.pay();
        // 打印发票
        printInvoice();
    }

    /**
     * 打印发票
     * @author pujian
     * @date 2021/3/26 9:52
     * @return
     */
    public void printInvoice() {
        System.out.println("打印了发票");
    }
}
