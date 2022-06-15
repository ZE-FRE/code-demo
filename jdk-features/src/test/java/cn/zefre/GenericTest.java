package cn.zefre;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 泛型测试类
 * 总结：传参用? super T，返回值：用? extends U
 *
 * @author pujian
 * @date 2022/5/27 13:50
 */
public class GenericTest {

    static class Animal {
        public void privatePrint() {
            System.out.println("private print");
        }
        public void sayHello() {
            System.out.println("hello, i am animal!");
        }
    }
    static class Rabbit extends Animal {
        @Override
        public void sayHello() {
            System.out.println("hello, i am a rabbit");
        }
    }
    static class BigRabbit extends Rabbit {
        @Override
        public void sayHello() {
            System.out.println("hello, i am a big rabbit");
        }
    }


    /**
     * 用集合来测试泛型的上下界
     *
     * @author pujian
     * @date 2020/11/4 20:16
     */
    @Test
    public void testListBound() {
        // 下界
        List<? super Rabbit> baseObjs = new ArrayList<>();
        baseObjs = new ArrayList<Object>();
        baseObjs = new ArrayList<Animal>();
        baseObjs = new ArrayList<Rabbit>();
        // baseObjs = new ArrayList<BigRabbit>(); // 编译不通过，Required type: List<? super Rabbit>  Provided: ArrayList<BigRabbit>

        baseObjs.add(new Rabbit()); // 可以add，即下界可以add
        Object object = baseObjs.get(0); // 可以看到get操作得到的是Object对象，即下界不可以get

        // ================================================= //

        // 上界
        List<? extends Rabbit> extendedObjs = new ArrayList<>();
        // extendedObjs = new ArrayList<Object>(); // 编译不通过，Required type: List<? extends Animal>  Provided: ArrayList<Object>
        // extendedObjs = new ArrayList<Animal>(); // 编译不通过，Required type: List<? extends Animal>  Provided: ArrayList<Animal>
        extendedObjs = new ArrayList<Rabbit>();
        extendedObjs = new ArrayList<Rabbit>(Collections.singletonList(new BigRabbit()));
        // extendedObjs = new ArrayList<BigRabbit>(Collections.singletonList(new Rabbit())); // 编译不通过

        // extendedObjs.add(new Rabbit()); // 编译不通过，可以看到上界不可以add，因为里面存的是Rabbit的一个具体子类型，是哪个具体子类型不知道，所以就没法add
        Animal animal = extendedObjs.get(0); // 可以看到get操作可以得到Animal对象，即上界可以get
        animal.privatePrint();
        animal.sayHello();
    }



    final static class Run<A> {
        public void run(A a) {
            System.out.println(a.getClass().getName());
        }
    }
    /**
     * 测试泛型上下界
     *
     * @author pujian
     * @date 2020/11/4 19:57
     */
    @Test
    public void testBound() {
        // 1、测试上界、下界对add操作的支持
        // 1.1测试下界
        // run1里的A是Rabbit或Rabbit的父类
        Run<? super Rabbit> run1 = new Run<>();
        run1.run(new BigRabbit()); // BigRabbit是Rabbit的子类，根据里氏替换原则，当然能传参成功
        // 1.2测试上界
        // run2里的A是Rabbit或Rabbit的子类
        Run<? extends Rabbit> run2 = new Run<>();
        //run2.run(new Animal()); // 编译不通过，父类型Animal不能赋值给子类型Rabbit及其子类。

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
        rabbit.sayHello();
    }


    final static class OverallCase<A, U> {
        /**
         * 此时{@link Function#apply(A or its superclass Object)}入参泛型是A或A的父类，所以可以传入A类型的参数，因为子类型A可以赋值给父类型
         * 此时{@link Function#apply(A or its superclass Object)}返回值泛型是U或U的子类，所以可以返回U类型，因为U的子类型可以赋值给U
         *
         * @param a a
         * @param mapper mapper
         * @author pujian
         * @date 2022/5/27 15:25
         * @return U
         */
        public U map(A a, Function<? super A, ? extends U> mapper) {
            return mapper.apply(a);
        }
    }
    @Test
    public void testOverall() {
        // 此时OverallCase的泛型A已确定为Rabbit，泛型U已确定为BigRabbit
        OverallCase<Rabbit, BigRabbit> overallCase = new OverallCase<>();
        // 实例化一个Function
        Function<Animal, BigRabbit> mapper = animal -> { animal.sayHello(); return new BigRabbit(); };

        // 此时的OverallCase#map方法签名已确定为：
        // public BigRabbit map(Rabbit a, Function<? super Rabbit, ? extends BigRabbit> mapper);
        // 第一个实参BigRabbit是Rabbit的子类型，所以可以传入
        // 第二个实参mapper，Animal是Rabbit的父类，符合? super Rabbit；BigRabbit是BigRabbit的本身，符合? extends BigRabbit
        overallCase.map(new BigRabbit(), mapper).sayHello(); // 编译通过，子类型BigRabbit可以赋值给Rabbit

        // overallCase.map(new Animal(), mapper); // 编译不通过，父类型Animal不能赋值给子类型Rabbit
    }



    @Test
    public void testGenericArray() throws InstantiationException, IllegalAccessException {

        // 数组的协变
        Rabbit[] rabbitArr = new BigRabbit[2];
        rabbitArr[0] = new BigRabbit();
        // rabbitArr[1] = new Rabbit(); // 运行时抛java.lang.ArrayStoreException异常

        // 泛型的不变
        // List<Rabbit> rabbitList = new ArrayList<BigRabbit>(); // 编译期类型异常

        Rabbit[] rabbits = instantiateGenericArray(Rabbit.class, 2);
        Assert.assertEquals(2, rabbits.length);
    }

    /**
     * 常用的实例化泛型数组的方式
     *
     * @param clazz clazz
     * @param size 数组大小
     * @author pujian
     * @date 2022/6/2 14:35
     * @return T[]
     */
    public static <T> T[] instantiateGenericArray(Class<T> clazz, int size) throws IllegalAccessException, InstantiationException {
        // T t = new T(); // 报错，泛型类型不能直接实例化，只能通过反射实例化
        T t = clazz.newInstance(); // OK
        // T[] tArr = new T[size]; // 报错，不能这样实例化泛型数组
        return (T[]) Array.newInstance(clazz, size);
    }

}
