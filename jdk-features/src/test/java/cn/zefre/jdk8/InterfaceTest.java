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
import java.util.function.Supplier;
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


    /**
     * 用集合来测试泛型的上下界问题
     * @author pujian
     * @date 2020/11/4 20:16
     */
    @Test
    public void testListCommon() {

        // 下界
        List<? super Rabbit> baseObjs = new ArrayList<>();
        baseObjs.add(new Rabbit()); // 可以add，即下界可以add
        Object object = baseObjs.get(0); // 可以看到get操作得到的是Object对象，即下界不可以get


        // ================================================= //


        // 上界
        List<? extends Animal> extendObjs = new ArrayList<>();
        //extendObjs.add(new Monkey()); // 编译不通过，可以看到上界不可以add
        //Animal animal = extendObjs.get(0); // 可以看到get操作得到的是Animal对象，即上界可以get


//        Animal a = new Rabbit();
//        a.privatePrint();
    }

    /**
     * 测试泛型
     * @author pujian
     * @date 2020/11/4 19:57
     */
    @Test
    public void testCommon() {

        // 1、测试上界、下界对add操作的支持
        // 1.1测试下界
        Run<? super Rabbit> run1 = new Run<>();
        run1.run(new BigRabbit()); // 编译运行都通过，因为BigRabbit是Rabbit的子类。说明下界能add
        // 1.2测试上界
        Run<? extends Rabbit> run2 = new Run<>();
        //run2.run(new Animal()); // 编译错误，Animal类型不能赋值给Rabbit及其子类。说明上界不能add



        // ====================================================== //


        // 2、测试上界、下界对get操作的支持
        // 2.1测试下界
        Supplier<? super BigRabbit> supplier1 = new Supplier<Animal>() {
            @Override
            public Animal get() {
                return new Animal();
            }
        };
        Object object = supplier1.get(); // 可以看到，get方法获取到的是Object，即下界不能get

        // 2.2测试上界
        Supplier<? extends Rabbit> supplier2 = new Supplier<BigRabbit>() {
            @Override
            public BigRabbit get() {
                return new BigRabbit();
            }
        };
        Rabbit rabbit = supplier2.get(); // 编译运行通过，get操作的到的是Rabbit，说明上界可以get
        rabbit.sayHelo();

    }

}

class Run<T> {
    public void run(T t) {
        System.out.println(t.getClass().getName());
    }
}

class Animal {
    public void privatePrint() {
        System.out.println("private print");
    }

    public void sayHelo() {
        System.out.println("hello, i am animal!");
    }
}

class Monkey extends Animal {
    @Override
    public void sayHelo() {
        System.out.println("hello, i am a monkey");
    }
}

class Rabbit extends Animal {
    @Override
    public void sayHelo() {
        System.out.println("hello, i am a rabbit");
    }
}

class BigRabbit extends Rabbit {
    @Override
    public void sayHelo() {
        System.out.println("hello, i am a big rabbit");
    }
}