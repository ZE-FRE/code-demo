package cn.zefre.jdk8;


import cn.zefre.jdk8.methodpointer.User;
import cn.zefre.jdk8.methodpointer.UserConstructor;
import cn.zefre.jdk8.myinterface.IStaticMethod;
import cn.zefre.jdk8.myinterface.function.MessageHandler;
import cn.zefre.jdk8.myinterface.function.Operation;
import cn.zefre.jdk8.myinterface.impl.DefaultInterfaceImpl;
import cn.zefre.jdk8.myinterface.impl.DoubleDefaultInterfaceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 测试接口特性
 * @author pujian
 * @date 2020/10/21 14:28
 */
public class InterfaceTest {

    // 测试接口的静态方法
    @Test
    public void testStaticMethod() {
        String value = IStaticMethod.interfaceStaticMethod();
        System.out.println(value);
    }

    @Test
    public void testDefaultMethod() {
        // 测试单个
        System.out.println(new DefaultInterfaceImpl().defaultMethod());
        // 测试多个
        System.out.println(new DoubleDefaultInterfaceImpl().defaultMethod());
    }

    // 测试函数接口
    @Test
    public void testFunctionInterface() {
        // 四则运算
        int num1 = 30;
        int num2 = 10;
        System.out.println(Operation.add(num1, num2));
        System.out.println(Operation.sub(num1, num2));
        System.out.println(Operation.multiply(num1, num2));
        System.out.println(Operation.divide(num1, num2));

        // 字符串处理
        MessageHandler messageHandler = msg -> {
            Assert.assertNotNull("msg不能为null", msg);
            return new StringBuilder(msg).reverse().toString();
        };
        System.out.println(messageHandler.handle("hello lambda"));
    }


    /**
     * 测试新特性-方法引用
     * 方法引用：
     * 使用::操作符通过方法名对方法进行引用
     * @author pujian
     * @date 2020/10/21 16:26
     *
     */
    @Test
    public void testMethodPointer() {

        List<Integer> nums = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        nums.forEach(System.out::print);
        System.out.println();
        System.out.println("=============================");
        // 引用静态方法
        nums.forEach(InterfaceTest::printEvenNum);

        System.out.println();
        System.out.println("=============================");
        // 引用实例方法
        nums.forEach(this::printOddNum);

        System.out.println();
        System.out.println("=============================");
        // 引用实例方法
        Stream.of(new InterfaceTest(), new InterfaceTest()).forEach(InterfaceTest::printHello);
    }

    public static void printEvenNum(Integer num) {
        if(num % 2 == 0)
            System.out.print(num + " ");
    }
    public void printOddNum(Integer num) {
        if(num % 2 != 0)
            System.out.print(num + " ");
    }
    public void printHello() {
        System.out.println("hello");
    }

    /**
     * 测试构造方法引用
     * @author pujian
     * @date 2020/10/21 16:45
     * 构造方法的引用语法：对象::new
     */
    @Test
    public void testConstructorPointer() {
        // 通过User::new即可将User类的构造方法引用给UserConstructor接口的construct方法
        UserConstructor userConstructor = User::new;
        User user = userConstructor.construct(1L, "zhangsan", 20);
        System.out.println(user);
    }

}
