package cn.zefre.jdk8.myinterface.function;

/**
 * 四则运算
 * @author pujian
 * @date 2020/10/21 14:58
 */
public class Operation {

    private int memberVar = 1;
    public void op() {
        int localVar = 1;
        MyFunctionInterface myFunctionInterface = (a, b) -> {
            memberVar = 10; // 可以看到lambada表达式可以修改成员变量的值
            //localVar = 2; // 取消这行注释，可以看到不能在lambda表达式中修改外部局部变量的值
            return 0;
        };
    }

    /**
     *
     * @author pujian
     * @date 2020/10/21 15:29
     * @param num1
     * @param num2
     * @description
     * lambda表达式声明的变量不能与外部局部变量同名
     * 例：
     * (num1, num2) -> num1 + num2 // 报错，因为num1,num2是add方法的形参，已经声明过了
     * (a, b) -> a + b // 正常
     */
    public static int add(int num1, int num2) {
        MyFunctionInterface myFunctionInterface = (a, b) -> a + b;
        return myFunctionInterface.op(num1, num2);
    }
    public static int sub(int num1, int num2) {
        MyFunctionInterface myFunctionInterface = (a, b) -> a - b;
        return myFunctionInterface.op(num1, num2);
    }
    public static int multiply(int num1, int num2) {
        MyFunctionInterface myFunctionInterface = (a, b) -> a * b;
        return myFunctionInterface.op(num1, num2);
    }
    public static int divide(int num1, int num2) {
        MyFunctionInterface myFunctionInterface = (a, b) -> a / b;
        return myFunctionInterface.op(num1, num2);
    }
    
    /** 
    *  这个方法是为了说明lambda表达式可以直接返回
    * @author pujian 
    * @date 2020/10/21 15:24
    * 
    */
    public static MyFunctionInterface getDefaultMyFunctionInterface() {
        return (a, b) -> 10;
    }
}
